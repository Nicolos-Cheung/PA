package com.pingan.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
	public static void deletefile(File file) {

		if (file.exists()) {
			file.delete();
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

	/**
	 * @param os
	 * @param pcmdata 
	 * @param srate
	 * @param channel
	 * @param format
	 * @throws IOException
	 */
	public static void PCMtoFile(OutputStream os, short[] pcmdata, int srate,
			int channel, int format) throws IOException {
		byte[] header = new byte[44];
		byte[] data = get16BitPcm(pcmdata);

		long totalDataLen = data.length + 36;
		long bitrate = srate * channel * format;

		header[0] = 'R';
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f';
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = (byte) format;
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1;
		header[21] = 0;
		header[22] = (byte) channel;
		header[23] = 0;
		header[24] = (byte) (srate & 0xff);
		header[25] = (byte) ((srate >> 8) & 0xff);
		header[26] = (byte) ((srate >> 16) & 0xff);
		header[27] = (byte) ((srate >> 24) & 0xff);
		header[28] = (byte) ((bitrate / 8) & 0xff);
		header[29] = (byte) (((bitrate / 8) >> 8) & 0xff);
		header[30] = (byte) (((bitrate / 8) >> 16) & 0xff);
		header[31] = (byte) (((bitrate / 8) >> 24) & 0xff);
		header[32] = (byte) ((channel * format) / 8);
		header[33] = 0;
		header[34] = 16;
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (data.length & 0xff);
		header[41] = (byte) ((data.length >> 8) & 0xff);
		header[42] = (byte) ((data.length >> 16) & 0xff);
		header[43] = (byte) ((data.length >> 24) & 0xff);

		os.write(header, 0, 44);
		os.write(data);
		os.close();
	}

	private static byte[] get16BitPcm(short[] data) {
		byte[] resultData = new byte[2 * data.length];
		int iter = 0;
		for (double sample : data) {
			short maxSample = (short) ((sample * Short.MAX_VALUE));
			resultData[iter++] = (byte) (maxSample & 0x00ff);
			resultData[iter++] = (byte) ((maxSample & 0xff00) >>> 8);
		}
		return resultData;
	}
	
public static void rawToWave(final File rawFile, final File waveFile) throws IOException {
		
		int RECORDER_SAMPLERATE = 16;  //***

		byte[] rawData = new byte[(int) rawFile.length()];
		DataInputStream input = null;
		try {
		    input = new DataInputStream(new FileInputStream(rawFile));
		    input.read(rawData);
		} finally {
		    if (input != null) {
		        input.close();
		    }
		}

		DataOutputStream output = null;
		try {
		    output = new DataOutputStream(new FileOutputStream(waveFile));
		    // WAVE header
		    // see http://ccrma.stanford.edu/courses/422/projects/WaveFormat/
		    writeString(output, "RIFF"); // chunk id
		    writeInt(output, 36 + rawData.length); // chunk size
		    writeString(output, "WAVE"); // format
		    writeString(output, "fmt "); // subchunk 1 id
		    writeInt(output, 16); // subchunk 1 size
		    writeShort(output, (short) 1); // audio format (1 = PCM)
		    writeShort(output, (short) 1); // number of channels
		    writeInt(output, 8000); // sample rate  //****
		    writeInt(output, RECORDER_SAMPLERATE * 2); // byte rate
		    writeShort(output, (short) 2); // block align
		    writeShort(output, (short) 16); // bits per sample
		    writeString(output, "data"); // subchunk 2 id
		    writeInt(output, rawData.length); // subchunk 2 size
		    // Audio data (conversion big endian -> little endian)
		    short[] shorts = new short[rawData.length / 2];
		    ByteBuffer.wrap(rawData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
		    ByteBuffer bytes = ByteBuffer.allocate(shorts.length * 2);
		    for (short s : shorts) {
		        bytes.putShort(s);
		    }

		    output.write(fullyReadFileToBytes(rawFile));
		} finally {
		    if (output != null) {
		        output.close();
		    }
		}
		}
		static byte[] fullyReadFileToBytes(File f) throws IOException {
		int size = (int) f.length();
		byte bytes[] = new byte[size];
		byte tmpBuff[] = new byte[size];
		FileInputStream fis= new FileInputStream(f);
		try { 

		    int read = fis.read(bytes, 0, size);
		    if (read < size) {
		        int remain = size - read;
		        while (remain > 0) {
		            read = fis.read(tmpBuff, 0, remain);
		            System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
		            remain -= read;
		        } 
		    } 
		}  catch (IOException e){
		    throw e;
		} finally { 
		    fis.close();
		} 

		return bytes;
		} 
		private static void writeInt(final DataOutputStream output, final int value) throws IOException {
		output.write(value >> 0);
		output.write(value >> 8);
		output.write(value >> 16);
		output.write(value >> 24);
		}

		private static void writeShort(final DataOutputStream output, final short value) throws IOException {
		output.write(value >> 0);
		output.write(value >> 8);
		}

		private static void writeString(final DataOutputStream output, final String value) throws IOException {
		for (int i = 0; i < value.length(); i++) {
		    output.write(value.charAt(i));
		    }
		}
	
}
