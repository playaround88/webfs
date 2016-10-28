package com.ai.webfs.store.fs;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FsProviderConfig {
	private static final Logger LOG=LoggerFactory.getLogger(FsProviderConfig.class);
	public static String uploadPath="";
	@Deprecated
	public static boolean useCatelog=false;//为保持结构目前不支持
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
			LOG.error("文件存储方式配置异常，如果没有使用文件存储，请忽略！"+e.getMessage());
			LOG.error("加载配置文件异常！", e);
		}
	}

}
