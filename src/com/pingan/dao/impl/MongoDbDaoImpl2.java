package com.pingan.dao.impl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.pingan.constant.Constant;
import com.pingan.dao.CustomerDao;
import com.pingan.dao.MongoDBDao;
import com.pingan.domain.Ivector;
import com.pingan.domain.IvectorV;

/**
 * 
 * 使用java 代码来调用mongodb 数据库
 * 
 * @author Ning
 * 
 */
public class MongoDbDaoImpl2 implements MongoDBDao {

	public String mongodb_ip = Constant.MONGODB_IP;
	public int mongodb_port = Constant.MONGODB_PORT;
	public String DB_name = Constant.MONGODB_DB_NAME;

	@Override
	public void register(IvectorV iv) {

		Mongo mongo = null;
		try {
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBCollection collection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();
			dbObject.put("telnum", iv.getTelnum());
			dbObject.put("url", iv.getUrl());
			dbObject.put("version", iv.getVersion());
			dbObject.put("wavpath", iv.getWavPath());
			collection.insert(dbObject);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}

	}

	@Override
	public IvectorV find(String telnum) {

		Mongo mongo = null;
		IvectorV iv = null;
		try {
			iv = new IvectorV();
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBCollection collection = db.getCollection("customers");
			DBObject searchQuery = new BasicDBObject();
			searchQuery.put("telnum", telnum);
			DBCursor dbCursor = collection.find(searchQuery); // dbCursor
																// 结果集(ResultSet)
			while (dbCursor.hasNext()) {
				DBObject ob = dbCursor.next();
				iv.setTelnum((ob.get("telnum").toString().trim()));
				iv.setUrl(ob.get("url").toString().trim());
				iv.setVersion((ob.get("version").toString().trim()));
				iv.setWavPath((ob.get("wavpath").toString().trim()));
			}
			return iv;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return null;

	}

	@Override
	public void update(IvectorV iv) {

		/**
		 * 更新的条件 更新的内容的对象 如果没有符合条件的记录，是否新增一条记录 如果有条记录符合，是否全部被更新...
		 */
		Mongo mongo = null;
		try {
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBObject queryObject = new BasicDBObject();
			queryObject.put("telnum", iv.getTelnum());
			DBCollection collection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();
			// dbObject.put("telnum", "123456");
			dbObject.put("url", iv.getUrl());
			dbObject.put("version", iv.getVersion());
			dbObject.put("wavpath", iv.getWavPath());
			collection.update(queryObject, dbObject);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
			mongo = null;
		}
	}

	@Override
	public void remove(String telnum) {
		Mongo mongo = null;
		try {
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBCollection collection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();
			dbObject.put("telnum", telnum);
			collection.remove(dbObject);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
	}
}