package com.ai.webfs.store.fs;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ai.webfs.commons.StringUtils;
import com.ai.webfs.meta.FileMeta;
import com.ai.webfs.store.FileStoreProvider;

public class FsProvider implements FileStoreProvider {
	private static final Logger LOG = LoggerFactory.getLogger(FsProvider.class);

	/**
	 * 支持文件上传目录按日期分目录
	 * //TODO 考虑分布式情况下的序列
	 */
	@Override
	public FileMeta store(MultipartFile file) throws Exception {
		String uploadPath=FsProviderConfig.uploadPath;
		//判断uploadPath是否存在，不存在则创建
		File path = new File(FsProviderConfig.uploadPath);
		if (!path.exists()) {
			LOG.debug("upload_path:" + FsProviderConfig.uploadPath + ", is not exit will create!");
			path.mkdirs();
		}
		//判断uploadPath/yyyyMMdd目录是否存在，不存在则创建
		String dateStr=StringUtils.getDate();
		if(FsProviderConfig.useDate){
			uploadPath=FsProviderConfig.uploadPath.endsWith(File.separator)?
								FsProviderConfig.uploadPath+dateStr:
									FsProviderConfig.uploadPath+File.separator+dateStr;
			path =new File(uploadPath);
			if (!path.exists()) {
				LOG.debug("upload_path/" + dateStr + " dir is not exit will create!");
				path.mkdirs();
			}
		}
		//获取文件后缀
		String suffix = file.getOriginalFilename().indexOf(".") == -1 ? "" : file.getOriginalFilename().substring(
				file.getOriginalFilename().indexOf("."));
		//生成不重复的文件名
		String seq = StringUtils.getDateTimeSeq();
		String genName = seq + suffix;//新生成的文件名
		String fullName = uploadPath + "/" + genName;//带完整路径的文件名

		FileMeta meta=extraFileMeta(file);
		meta.setSaveName(genName);

		file.transferTo(new File(fullName));

		return meta;
	}

	private FileMeta extraFileMeta(MultipartFile file) {
		FileMeta fm = new FileMeta();
		fm.setName(file.getOriginalFilename());
		fm.setSize(file.getSize());
		
		return fm;
	}

	@Override
	public File getFile(FileMeta meta) {
		String uploadPath=FsProviderConfig.uploadPath;
		String name=meta.getSaveName();
		
		if(FsProviderConfig.useDate){
			uploadPath+=uploadPath.endsWith(File.separator)?
									name.substring(0,8):
										File.separator+name.substring(0,8);
		}
		
		return new File(uploadPath+File.separator+name);
	}

	@Override
	public void delete(FileMeta meta) throws Exception {
		String uploadPath=FsProviderConfig.uploadPath;
		String name=meta.getSaveName();
		//文件按日期分目录情况的处理
		if(FsProviderConfig.useDate){
			uploadPath+=uploadPath.endsWith(File.separator)?
									name.substring(0,8):
										File.separator+name.substring(0,8);
		}
		
		File file=new File(uploadPath+File.separator+name);
		if(file.exists()){
			file.delete();
		}else{
			throw new Exception("文件不存在！");
		}
	}

}
