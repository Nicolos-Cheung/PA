package com.pingan.service.impl;

import com.pingan.dao.MongoDBDao;
import com.pingan.dao.PCMMongoDbDao;
import com.pingan.dao.impl.MongoDbDaoImpl2;
import com.pingan.dao.impl.PCMDaoImpl;
import com.pingan.domain.IvectorV;
import com.pingan.domain.PCMObject;
import com.pingan.service.MongoDBService;
import com.pingan.service.PCMMongoDBService;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class PCMMongoDBServiceImpl implements PCMMongoDBService {

	private PCMMongoDbDao dao = new PCMDaoImpl();

	@Override
	public void register(PCMObject po) {

		dao.register(po);

	}

	@Override
	public String QueryIvector(String userid) {

		PCMObject po = dao.find(userid);
		return po.getIvector();
	}

	@Override
	public String QueryIvectorVersion(String userid) {

		PCMObject po = dao.find(userid);
		return po.getVersion();
	}

	@Override
	public String QueryVoicePath(String userid) {

		PCMObject po = dao.find(userid);
		return po.getVoicepath();
	}

	@Override
	public void update(PCMObject po) {

		dao.update(po);

	}
}
