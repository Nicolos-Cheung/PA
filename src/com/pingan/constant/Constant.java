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
	 * 上传注册语音文件的目录
	 */
	public static final String VOICEPATH = FILEPATH + "/voice";

	/**
	 * PCM上传注册语音文件的目录
	 */
	public static final String PCMVOICEPATH = FILEPATH + "/pcm2wav";

	/**
	 * 是否保存PCM数据
	 */
	public static final boolean IS_RETAIN_PCM_DATA = true;

	/**
	 * 上传验证语音文件的目录
	 */
	public static final String VALIDATION_VOICEPATH = FILEPATH + "/voice";

	/**
	 * 是否保留验证音频
	 */
	public static final boolean IS_RETAIN_VALIDATION_VOICE = false;

	/**
	 * UpLoad临时文件目录
	 */
	public static final String TEMPPATH = FILEPATH + "/temp";

	/**
	 * 注册特征文件的目录
	 */
	public static final String IVECTOR_PATH = FILEPATH + "/ivector";

	/**
	 * 使用的数据库类型：
	 */
	public static final DB WHICH_DATABASE = DB.MYSQL;

	public enum DB {
		MYSQL, MONGODB
	}

	/**
	 * MongoDB IP
	 */
	public static final String MONGODB_IP = "localhost";
	/**
	 * MongoDB 端口号
	 */
	public static final int MONGODB_PORT = 27017;

	/**
	 * MongoDB 数据库名
	 */
	public static final String MONGODB_DB_NAME = "pingan101";

	/**
	 * 点积阈值
	 */
	public static final int THRESHOLD = 100;

	/**
	 * PLDA阈值
	 */
	public static final int PLDA_THRESHOLD = 0;

	/**
	 * 评分方式
	 */
	public static final SCORE SCORE_MODE = SCORE.DOT;

	public enum SCORE {
		DOT, PLDA
	}

	/**
	 * 当前使用的特征提取的版本号
	 */
	public static final String IVECTOR_VERSION = "1.0";

}
