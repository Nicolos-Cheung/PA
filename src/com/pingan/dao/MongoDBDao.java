package com.pingan.dao;

import com.pingan.domain.IvectorV;

public interface MongoDBDao {

	void register(IvectorV iv);

	IvectorV find(String telnum);

	void update(IvectorV iv);

	void remove(String telnum);
	
}
