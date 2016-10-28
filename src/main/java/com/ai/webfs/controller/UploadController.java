package com.ai.webfs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ai.webfs.commons.JsonWriter;
import com.ai.webfs.meta.FileMeta;
import com.ai.webfs.meta.FileMetaService;
import com.ai.webfs.store.FileStoreProvider;
import com.google.gson.Gson;

@RestController
public class UploadController {
	private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

	private static final String DOWNLOAD_URL = "/download/";
	private static final String DEL_URL = "/delete/";

	private FileStoreProvider fileStoreProvider;
	private FileMetaService fileMetaService;
	@Autowired
	public void setFileStoreService(FileStoreProvider fileStoreProvider) {
		this.fileStoreProvider = fileStoreProvider;
	}
	@Autowired
	public void setFileMetaService(FileMetaService fileMetaService) {
		this.fileMetaService = fileMetaService;
	}

	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public Object upload(MultipartHttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		List<FileMeta> metas = new ArrayList<FileMeta>();
		FileMeta meta=null;
		//存储文件
		try {
			Iterator<String> fileNames = request.getFileNames();
			while (fileNames.hasNext()) {
				MultipartFile file = request.getFile(fileNames.next());
				//保存文件
				try {
					meta=fileStoreProvider.store(file);
					metas.add(meta);
				} catch (Exception e) {
					if(meta!=null){
						meta.setError("文件存储异常！"+e.getMessage());
					}
					throw e;
				}
			}
		} catch (Exception e) {
			LOG.error("文件存储异常！" + e);
			result.put("error", e.getMessage());
			return result;
		}
		
		//遍历元信息，填充必要的公共数据
		for(int i=metas.size()-1; i>=0; i--){
			metas.get(i).setUrl(DOWNLOAD_URL);
			metas.get(i).setDeleteUrl(DEL_URL);
			metas.get(i).setCatalog(request.getParameter("catalog"));
		}

		//保存文件元信息
		try {
			fileMetaService.addMult(metas);
		} catch (Exception e) {
			LOG.error("保存文件元信息异常！" + e);
			result.put("error", e.getMessage());
			return result;
		}

		result.put("files", metas);
		return result;
	}

	@RequestMapping("/delete/{id}")
	public Object delete(@PathVariable long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		FileMeta meta = fileMetaService.selectById(id);
		if (meta == null) {
			LOG.error("没有查询到删除文件元信息id:" + id);
			result.put("error", "没有查询到删除文件元信息id:" + id);
			return result;
		}
		//
		try {
			fileStoreProvider.delete(meta);
		} catch (Exception e) {
			LOG.error("删除文件存储异常:", e);
			result.put("error", e.getMessage());
			return result;
		}
		//
		try {
			fileMetaService.del(meta.getId());
		} catch (Exception e) {
			LOG.error("删除文件元信息异常:", e);
			result.put(meta.getName(), false);
			result.put("error", "文件已删除，但元信息删除异常!" + e.getMessage());
			return result;
		}

		result.put(meta.getName(), true);
		return result;
	}

	@RequestMapping("/query/meta/{id}")
	public Object query(@PathVariable long id) {
		return fileMetaService.selectById(id);
	}

	@RequestMapping("/download/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable long id)
			throws IOException {
		LOG.debug("download file id:" + id);
		String message = null;
		if (id == 0) {
			return;
		}

		FileMeta meta = fileMetaService.selectById(id);
		if (meta == null) {
			message = "没有查询到删除文件元信息id:" + id;
			LOG.error(message);

			return;
		}

		String fileName = meta.getName();
		File file = fileStoreProvider.getFile(meta);

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			fileName = URLEncoder.encode(fileName, "UTF-8");//IE浏览器
		}

		response.reset();
		response.setContentType("application/x-download");
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;fileName=\"" + fileName + "\"");
		response.setHeader("Connection", "close");
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = response.getOutputStream();

			in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}
	}

	@ExceptionHandler(Exception.class)
	public void handleAllException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		LOG.error("捕获异常：", e);
		Map<String, Object> map = new HashMap<String, Object>();
		if (e instanceof MaxUploadSizeExceededException) {
			//long maxSize = ((MaxUploadSizeExceededException) e).getMaxUploadSize();
			map.put("error", "上传文件太大，" + e.getMessage());
		} else if (e instanceof RuntimeException) {
			map.put("error", "未选中文件");
		} else {
			map.put("error", "上传失败");
		}
		Gson gson = new Gson();
		JsonWriter.writeText(response, gson.toJson(map));
	}
}
