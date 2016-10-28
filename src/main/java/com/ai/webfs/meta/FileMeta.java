package com.ai.webfs.meta;

import java.io.Serializable;

/**
 * �ļ��ϴ��󷵻صĶ���
 * @author wu
 *
 */
public class FileMeta implements Serializable{
	private static final long serialVersionUID = -1424066145490090054L;
	private long id;
	/**
	 * jquery-fileupload��Ҫ���ֶ�
	 */
	private String name;
	private long size;
	private String url;
	//@Transparent
	private String thumbnailUrl;
	private String deleteUrl="";
	private String deleteType="DELETE";
	/** end */
	//������������ļ�����
	private String saveName;
	//�����Ŀ�������
	private String catalog;
	
	//�ϴ�����ʱ����������Ϣ
	//@Transparent
	private String error;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "FileMeta [id=" + id + ", name=" + name + ", size=" + size + ", url=" + url + ", thumbnailUrl="
				+ thumbnailUrl + ", deleteUrl=" + deleteUrl + ", deleteType=" + deleteType + ", saveName=" + saveName
				+ ", catalog=" + catalog + ", error=" + error + "]";
	}
}
