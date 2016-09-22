package com.pingan.dao;

import com.pingan.domain.PCMObject;

public interface PCMMongoDbDao {

	void register(PCMObject po);

	PCMObject find(String userid);

	void update(PCMObject po);

	void remove(String userid);
	
}
