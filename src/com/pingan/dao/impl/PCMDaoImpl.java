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
import com.pingan.dao.PCMMongoDbDao;
import com.pingan.domain.Ivector;
import com.pingan.domain.IvectorV;
import com.pingan.domain.PCMObject;

/**
 * 
 * PCM——MongoDB的dao实现
 * @author Ning
 * 
 * 
 */
public class PCMDaoImpl implements PCMMongoDbDao {

	public String mongodb_ip = Constant.MONGODB_IP;
	public int mongodb_port = Constant.MONGODB_PORT;
	public String DB_name = Constant.MONGODB_DB_NAME;
	public String Collection_name = "pcm_user";

	
	@Override
	public void register(PCMObject po) {

		Mongo mongo = null;
		try {
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBCollection collection = db.getCollection(Collection_name);
			DBObject dbObject = new BasicDBObject();
			dbObject.put("userid", po.getUserid());
			dbObject.put("ivector", po.getIvector());
			dbObject.put("version", po.getVersion());
			dbObject.put("voicepath", po.getVoicepath());
			dbObject.put("desc", po.getDesc());
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
	public PCMObject find(String userid) {

		Mongo mongo = null;
		PCMObject po = null;
		try {
			po = new PCMObject();
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBCollection collection = db.getCollection(Collection_name);
			DBObject searchQuery = new BasicDBObject();
			searchQuery.put("userid", userid);
			DBCursor dbCursor = collection.find(searchQuery); // dbCursor
																// 结果集(ResultSet)
			while (dbCursor.hasNext()) {
				DBObject ob = dbCursor.next();
				po.setUserid(ob.get("userid").toString().trim());
				po.setIvector(ob.get("ivector").toString().trim());
				po.setVersion(ob.get("version").toString().trim());
				po.setVoicepath(ob.get("voicepath").toString().trim());
				po.setDesc(ob.get("desc").toString().trim());
			}
			return po;

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
	public void update(PCMObject po) {

		/**
		 * 更新的条件 更新的内容的对象 如果没有符合条件的记录，是否新增一条记录 如果有条记录符合，是否全部被更新...
		 */
		Mongo mongo = null;
		try {
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBObject queryObject = new BasicDBObject();
			queryObject.put("userid", po.getUserid());
			DBCollection collection = db.getCollection(Collection_name);
			DBObject dbObject = new BasicDBObject();
			dbObject.put("userid", po.getUserid());
			dbObject.put("ivector", po.getIvector());
			dbObject.put("version", po.getVersion());
			dbObject.put("voicepath", po.getVoicepath());
			dbObject.put("desc", po.getDesc());
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
	public void remove(String userid) {
		Mongo mongo = null;
		try {
			mongo = new Mongo(mongodb_ip, mongodb_port);
			DB db = mongo.getDB(DB_name);
			DBCollection collection = db.getCollection(Collection_name);
			DBObject dbObject = new BasicDBObject();
			dbObject.put("userid", userid);
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