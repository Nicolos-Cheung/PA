package com.pingan.service;

import com.pingan.domain.IvectorV;

public interface MongoDBService {

	/**
	 * 用户声纹注册
	 */
	void register(IvectorV iv);
	
	/**
	 * 查询特征文件路径
	 */
	String QueryIvectorPath(String telnum);
	
	/**
	 * 查询特征文件版本
	 */
	String QueryIvectorVersion(String telnum);
	
	/**
	 * 查找wav地址
	 */
	String QueryWavPath(String telnum);
	
	/**
	 * 更新
	 * @param iv
	 */
    void update(IvectorV iv);
	
	
}
