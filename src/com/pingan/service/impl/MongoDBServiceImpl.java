package com.pingan.service.impl;

import com.pingan.dao.MongoDBDao;
import com.pingan.dao.impl.MongoDbDaoImpl2;
import com.pingan.domain.IvectorV;
import com.pingan.service.MongoDBService;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class MongoDBServiceImpl implements MongoDBService {
	
	private MongoDBDao dao = new MongoDbDaoImpl2();

	@Override
	public void register(IvectorV iv) {
		dao.register(iv);
	}

	@Override
	public String QueryIvectorPath(String telnum) {
		
		IvectorV iv = dao.find(telnum);
		return iv.getIvectorPath();
	}

	@Override
	public String QueryIvectorVersion(String telnum) {
		
		IvectorV iv = dao.find(telnum);
		return iv.getVersion();
	}

	@Override
	public String QueryWavPath(String telnum) {
		
		IvectorV iv = dao.find(telnum);
		return iv.getWavPath();
	}

	@Override
	public void update(IvectorV iv) {
		dao.update(iv);
		
	}

}
