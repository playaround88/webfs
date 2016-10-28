package com.ai.webfs.store;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ai.webfs.meta.FileMeta;
/**
 * �ļ��洢��ʽ�ĳ���ӿ�
 * @author wutb
 */
public interface FileStoreProvider {
	/**
	 * �����ļ�
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public FileMeta store(MultipartFile file) throws Exception;
	/**
	 * ��ȡ�ļ�
	 * @param meta
	 * @return
	 */
	public File getFile(FileMeta meta);
	
	/**
	 * ɾ���ļ�
	 * @param meta
	 * @throws Exception 
	 */
	public void delete(FileMeta meta) throws Exception;
}
