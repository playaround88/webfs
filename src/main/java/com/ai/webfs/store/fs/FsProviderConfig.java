package com.ai.webfs.store.fs;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FsProviderConfig {
	private static final Logger LOG=LoggerFactory.getLogger(FsProviderConfig.class);
	public static String uploadPath="";
	@Deprecated
	public static boolean useCatelog=false;//Ϊ���ֽṹĿǰ��֧��
	public static boolean useDate=true;
	
	static{
		Properties props=new Properties();
		try {
			props.load(FsProviderConfig.class.getResourceAsStream("/fs.propertis"));
			uploadPath=props.getProperty("upload_path");
			useCatelog=Boolean.parseBoolean(props.getProperty("use_catelog"));
			useDate=Boolean.parseBoolean(props.getProperty("use_date"));
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("�ļ��洢��ʽ�����쳣�����û��ʹ���ļ��洢������ԣ�"+e.getMessage());
			LOG.error("���������ļ��쳣��", e);
		}
	}

}
