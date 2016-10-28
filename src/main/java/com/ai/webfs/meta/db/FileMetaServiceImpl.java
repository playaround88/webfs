package com.ai.webfs.meta.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ai.webfs.meta.FileMeta;
import com.ai.webfs.meta.FileMetaService;

public class FileMetaServiceImpl implements FileMetaService{
	private static final Logger LOG=LoggerFactory.getLogger(FileMetaServiceImpl.class);
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	@Transactional
	public void addMult(List<FileMeta> files) {
		for(int i=0;i<files.size();i++){
			add(files.get(i));
		}
	}

	@Override
	public void add(FileMeta meta) {
		LOG.debug("add file meta:"+meta.toString());
		sqlSession.insert("FileMeta.insertFileMeta",meta);
	}

	@Override
	public void del(long id) {
		sqlSession.delete("FileMeta.delById", id);
	}

	@Override
	public FileMeta selectById(long id) {
		return sqlSession.selectOne("FileMeta.selectById", id);
	}
	
	
}
