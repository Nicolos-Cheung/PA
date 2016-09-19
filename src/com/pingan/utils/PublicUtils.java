package com.pingan.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicUtils {

	public static void deletefile(String filepath) {

		File registerwav = new File(filepath);
		if (registerwav.exists()) {
			registerwav.delete();
		}
	}

	public static String getDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

	/**
	 * 如果路径不存在则为其创建
	 * 
	 * @param path
	 */
	public static void mkDir(String path) {

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 判断文件类型
	 */
	public static boolean checkFileType(String type, String filename) {

		String[] split = filename.split("\\.");
		String filetype = split[split.length - 1];
		if (!filetype.equals(type)) {
			return false;
		}
		return true;
	}

}
