package com.pingan.dao.impl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.pingan.dao.CustomerDao;
import com.pingan.domain.Ivector;

/**
 * 
 * 使用java 代码来调用mongodb 数据库
 * 
 * @author Ning
 * 
 */
public class MongoDbDaoImpl implements CustomerDao {

	/**
	 * 注册声纹
	 */
	public void register(Ivector c) {
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("pingan");
			DBCollection collection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();
			dbObject.put("telnum", c.getTelnum());
			dbObject.put("url", c.getUrl());
			collection.insert(dbObject);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}

		}

	}

	/**
	 * 根据电话号码查询Ivector对象
	 */
	public Ivector find(String telnum) {

		Mongo mongo = null;
		Ivector c = new Ivector();
		c.setTelnum(telnum);
		try {
			mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("pingan");
			DBCollection dbCollection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();
			dbObject.put("telnum", telnum);
			DBCursor dbCursor = dbCollection.find(dbObject); // dbCursor
																// 结果集(ResultSet)
			while (dbCursor.hasNext()) {
				DBObject ob = dbCursor.next();
				Object object = ob.get("url");
				System.out.println(object.toString());
				c.setUrl(object.toString());
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}

		}

		return c;
	}

	/**
	 * 更新已注册的Ivector的地址
	 */
	public void update(Ivector c) {

		/**
		 * 更新的条件 更新的内容的对象 如果没有符合条件的记录，是否新增一条记录 如果有条记录符合，是否全部被更新...
		 */
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("pingan");
			DBObject queryObject = new BasicDBObject();
			queryObject.put("telnum", c.getTelnum());
			DBCollection collection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();
			dbObject.put("telnum", c.getTelnum());
			dbObject.put("url", c.getUrl());

			collection.update(queryObject, dbObject);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}

		}
	}

	/**
	 * 根据手机号删除
	 */
	public void remove(String telnum) {
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("pingan");
			DBCollection collection = db.getCollection("customers");
			DBObject dbObject = new BasicDBObject();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}

		}
	}

	public void remove() throws UnknownHostException {
		// 建立起一个连接...
		Mongo mongo = new Mongo("localhost", 3333);
		// 获取到指定的数据库...
		DB db = mongo.getDB("test");
		DBCollection dbCollection = db.getCollection("person1");

		DBObject dbObject = new BasicDBObject();

		dbCollection.remove(dbObject);

		mongo.close();

	}

	public void Update() throws UnknownHostException {
		// 建立起一个连接...
		Mongo mongo = new Mongo("localhost", 3333);
		// 获取到指定的数据库...
		DB db = mongo.getDB("test");
		DBCollection dbCollection = db.getCollection("person1");
		/**
		 * 更新的条件
		 * 
		 * 更新的内容的对象
		 * 
		 * 如果没有符合条件的记录，是否新增一条记录
		 * 
		 * 如果有条记录符合，是否全部被更新...
		 * 
		 */
		// BasicDBObject query = new BasicDBObject("_id",new
		// ObjectId("519e2e393296cf3baccdb10c"));
		// BasicDBObject object = (BasicDBObject) collection.findOne(query);
		// object.put("name", “wangwu");
		// int n = collection.update(query, object).getN();

		// dbCollection.update(q, o, upsert, multi)

	}

}
