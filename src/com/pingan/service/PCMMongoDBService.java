package com.pingan.service;

import com.pingan.domain.IvectorV;
import com.pingan.domain.PCMObject;

public interface PCMMongoDBService {

	/**
	 * 用户声纹注册
	 */
	void register(PCMObject po);
	
	/**
	 * 查询特征文件路径
	 */
	String QueryIvector(String userid);
	
	/**
	 * 查询特征文件版本
	 */
	String QueryIvectorVersion(String userid);
	
	/**
	 * 查找wav地址
	 */
	String QueryVoicePath(String userid);
	
	/**
	 * 更新
	 * @param iv
	 */
    void update(PCMObject po);
    
    
	
	
}
