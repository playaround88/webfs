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
	 * ��������ʱ���ַ������ڼ���һ��ȫ�����У��ֲ�ʽ����²�����
	 * yyyyMMddHHmmss+seq(3)
	 * @return
	 */
	public static String getDateTimeSeq() {
		return getDatetime() + getSeq();
	}
	
	/**
	 * ��ȡyyyyMMddHHmmss��ʽ��ʱ���ַ���
	 * @return
	 */
	public static String getDatetime(){
		Date date=new Date();
		return sdf.format(date); 
	}
	/**
	 * ����yyyyMMdd�������ַ���
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		return dt_sdf.format(date);
	}
}
