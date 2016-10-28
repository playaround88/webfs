package com.ai.webfs.commons;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class JsonWriter {
	public static String NULL_LIST="{\"total\":0,\"rows\":[]}";
	public static void writeText(HttpServletResponse response,String message){
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 以json格式写出字符�?
	 * @param response
	 * @param message
	 */
	public static void writeJson(HttpServletResponse response,String message){
		//转化json数据
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 标准响应json格式
	 * {"success":true,"data":[]|{}}
	 * {"success":false,"message":""}
	 */
	/**
	 * 返回标准格式封装的一个对�?
	 */
	public static void writeObj(HttpServletResponse response,Object obj){
		Gson gson=new Gson();
		writeJson(response,"{\"success\":true,\"data\":"+gson.toJson(obj)+"}");
	}
	/**
	 * 返回标准格式封装的list
	 * @param response
	 * @param ls
	 */
	public static void writeList(HttpServletResponse response,List<?> ls){
		if(ls==null){
			writeJson(response,"{\"success\":true,\"data\":[]}");
			return ;
		}
		Gson gson=new Gson();
		writeJson(response,"{\"success\":true,\"data\":"+gson.toJson(ls)+"}");
	}
	/**
	 * 返回标准的错误信�?
	 */
	public static void writeErrorMsg(HttpServletResponse response,String msg){
		writeJson(response,"{\"success\":false,\"message\":\""+msg+"\"}");
	}
	/**
	 * 返回标准的信�?
	 */
	public static void writeSuccessMsg(HttpServletResponse response,String msg){
		writeJson(response,"{\"success\":true,\"message\":\""+msg+"\"}");
	}
	
}
