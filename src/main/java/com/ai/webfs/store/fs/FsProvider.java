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
	 * ֧���ļ��ϴ�Ŀ¼�����ڷ�Ŀ¼
	 * //TODO ���Ƿֲ�ʽ����µ�����
	 */
	@Override
	public FileMeta store(MultipartFile file) throws Exception {
		String uploadPath=FsProviderConfig.uploadPath;
		//�ж�uploadPath�Ƿ���ڣ��������򴴽�
		File path = new File(FsProviderConfig.uploadPath);
		if (!path.exists()) {
			LOG.debug("upload_path:" + FsProviderConfig.uploadPath + ", is not exit will create!");
			path.mkdirs();
		}
		//�ж�uploadPath/yyyyMMddĿ¼�Ƿ���ڣ��������򴴽�
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
		//��ȡ�ļ���׺
		String suffix = file.getOriginalFilename().indexOf(".") == -1 ? "" : file.getOriginalFilename().substring(
				file.getOriginalFilename().indexOf("."));
		//���ɲ��ظ����ļ���
		String seq = StringUtils.getDateTimeSeq();
		String genName = seq + suffix;//�����ɵ��ļ���
		String fullName = uploadPath + "/" + genName;//������·�����ļ���

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
		//�ļ������ڷ�Ŀ¼����Ĵ���
		if(FsProviderConfig.useDate){
			uploadPath+=uploadPath.endsWith(File.separator)?
									name.substring(0,8):
										File.separator+name.substring(0,8);
		}
		
		File file=new File(uploadPath+File.separator+name);
		if(file.exists()){
			file.delete();
		}else{
			throw new Exception("�ļ������ڣ�");
		}
	}

}
