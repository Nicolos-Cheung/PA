package com.pingan.constant;

public interface Constant {

	/**
	 * 工具路径
	 */
	public static final String TOOLPATH = "/home/pingandl/javaserver/tool";

	/**
	 * 数据文件根目录
	 */
	public static final String FILEPATH = "/home/pingandl/javaserver/server_data";

	/**
	 * 上传语音文件的目录
	 */
	public static final String VOICEPATH = FILEPATH + "/voice";

	/**
	 * UpLoad临时文件目录
	 */
	public static final String TEMPPATH = FILEPATH + "/temp";

	/**
	 * 注册特征文件的目录
	 */
	public static final String IVECTOR_PATH = FILEPATH + "/ivector";

	/*
	 * 使用的数据库类型：
	 */
	public static final DB WHICH_DATABASE = DB.MYSQL;

	public enum DB {
		MYSQL, MONGODB
	}

	/*
	 * 点积阈值
	 */
	public static final int THRESHOLD = 100;

	/*
	 * PLDA阈值
	 */
	public static final int PLDA_THRESHOLD = 0;

	/**
	 * 评分方式
	 */
	public static final SCORE SCORE_MODE = SCORE.PLDA;

	public enum SCORE {
		DOT, PLDA
	}

}
