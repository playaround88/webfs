package com.ai.webfs.meta;

import java.util.List;
/**
 * �ļ�Ԫ��Ϣ�������ӿ�
 * @author wutb
 */
public interface FileMetaService {
	/**
	 * ��Ӷ���ļ�Ԫ��Ϣ
	 * @param files
	 */
	public void addMult(List<FileMeta> files);
	/**
	 * �����ļ�Ԫ��Ϣ
	 * @param meta
	 */
	public void add(FileMeta meta);
	/**
	 * ɾ���ļ�Ԫ��Ϣ
	 * @param id
	 */
	public void del(long id);
	/**
	 * ��ѯ�ļ�Ԫ��Ϣ
	 * @param id
	 * @return
	 */
	public FileMeta selectById(long id);

}
