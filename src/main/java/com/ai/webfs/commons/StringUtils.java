package com.ai.webfs.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	private static Integer seq = 0;
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat dt_sdf=new SimpleDateFormat("yyyyMMdd");

	public static int getSeq() {
		synchronized (seq) {
			if (seq < 999) {
				return ++seq;
			} else {
				seq = 0;
				return seq;
			}
		}
	}

	/**
	 * 生成日期时间字符串，在加上一个全局序列，分布式情况下不可用
	 * yyyyMMddHHmmss+seq(3)
	 * @return
	 */
	public static String getDateTimeSeq() {
		return getDatetime() + getSeq();
	}
	
	/**
	 * 获取yyyyMMddHHmmss格式的时间字符串
	 * @return
	 */
	public static String getDatetime(){
		Date date=new Date();
		return sdf.format(date); 
	}
	/**
	 * 生成yyyyMMdd的日期字符串
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		return dt_sdf.format(date);
	}
}
