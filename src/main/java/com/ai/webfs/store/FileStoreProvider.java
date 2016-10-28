package com.ai.webfs.store;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ai.webfs.meta.FileMeta;
/**
 * 文件存储方式的抽象接口
 * @author wutb
 */
public interface FileStoreProvider {
	/**
	 * 保存文件
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public FileMeta store(MultipartFile file) throws Exception;
	/**
	 * 获取文件
	 * @param meta
	 * @return
	 */
	public File getFile(FileMeta meta);
	
	/**
	 * 删除文件
	 * @param meta
	 * @throws Exception 
	 */
	public void delete(FileMeta meta) throws Exception;
}
