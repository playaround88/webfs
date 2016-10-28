package com.ai.webfs.meta;

import java.util.List;
/**
 * 文件元信息保存抽象接口
 * @author wutb
 */
public interface FileMetaService {
	/**
	 * 添加多个文件元信息
	 * @param files
	 */
	public void addMult(List<FileMeta> files);
	/**
	 * 保存文件元信息
	 * @param meta
	 */
	public void add(FileMeta meta);
	/**
	 * 删除文件元信息
	 * @param id
	 */
	public void del(long id);
	/**
	 * 查询文件元信息
	 * @param id
	 * @return
	 */
	public FileMeta selectById(long id);

}
