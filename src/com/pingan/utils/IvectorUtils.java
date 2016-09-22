package com.pingan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IvectorUtils {

	/**
	 * 
	 * @param filepath
	 *            wav路径
	 * @param toolpath
	 *            tool的路径
	 * @return
	 */
	public static List<String> KaldiToIvecter(String filepath, String toolpath) {
		Process process = null;
		List<String> processList = new ArrayList<String>();

		String commond = toolpath + "/wav_ivector --config=" + toolpath
				+ "/compute.conf " + filepath + " " + toolpath + "/final.ubm "
				+ toolpath + "/final.ie";
		try {
			process = Runtime.getRuntime().exec(commond);
			process.waitFor();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {
				processList.add(line);
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return processList;
	}

	/**
	 * 
	 * @param registerdir
	 *            注册特征值文件路径
	 * @param testdir
	 *            测试生成的特征值文件路径
	 * @param toolpath
	 * @return
	 */
	public static double Kaldiscore(String registerdir, String testdir,
			String toolpath) {
		Process process = null;
		List<String> processList = new ArrayList<String>();
		String commond = toolpath + "/wav_score_dot_product " + registerdir
				+ " " + testdir;
		try {
			process = Runtime.getRuntime().exec(commond);
			process.waitFor();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {
				processList.add(line);
			}
			input.close();

			if (processList != null && processList.size() > 0) {
				String str = processList.get(0).trim();
				return Double.parseDouble(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param registerdir
	 *            注册特征值文件路径
	 * @param testdir
	 *            测试生成的特征值文件路径
	 * @param toolpath
	 * @return
	 */
	public static double KaldiPLDAscore(String registerdir, String testdir,
			String toolpath) {
		Process process = null;
		List<String> processList = new ArrayList<String>();
		String commond = toolpath + "/wav_score " + registerdir + " " + testdir
				+ " " + toolpath + "/plda";
		try {
			process = Runtime.getRuntime().exec(commond);
			process.waitFor();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {
				processList.add(line);
			}
			input.close();

			String string = processList.get(0).trim();
			if (processList != null && processList.size() > 0) {
				return Double.parseDouble(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param list
	 * @param path
	 *            特征值存放的路径
	 * @param filename
	 *            特征值文件名
	 * @return
	 */
	public static String ListToFile_Return_FilePath(List<String> list, String path,
			String filename) {

		StringBuffer bf = new StringBuffer();

		if (list.size() == 0) {
			return "";
		}

		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			bf.append(iter.next());
		}
		try {
			FileOutputStream fos = new FileOutputStream(
					new File(path, filename));

			OutputStreamWriter osw = new OutputStreamWriter(fos);

			osw.write(bf.toString());

			osw.close();
			fos.close();

			return path+"/"+filename+".ivector";

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 
	 * @param list
	 * @param path
	 *            特征值存放的路径
	 * @param filename
	 *            特征值文件名
	 * @return
	 */
	public static boolean ListToFile(List<String> list, String path,
			String filename) {

		StringBuffer bf = new StringBuffer();

		if (list.size() == 0) {
			return false;
		}

		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			bf.append(iter.next());
		}
		try {
			FileOutputStream fos = new FileOutputStream(
					new File(path, filename));

			OutputStreamWriter osw = new OutputStreamWriter(fos);

			osw.write(bf.toString());

			osw.close();
			fos.close();

			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}
	

	/**
	 * 
	 * @param list
	 * @param path
	 *            特征值存放的路径
	 * @param filename
	 *            特征值文件名
	 * @return
	 */
	public static String ListToString(List<String> list) {

		StringBuffer bf = new StringBuffer();
		if (list.size() == 0) {
			return "";
		}

		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			bf.append(iter.next());
		}

		return bf.toString();
	}
}
