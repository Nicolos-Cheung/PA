package com.pingan.domain;

public class IvectorV {

	private String telnum;
	private String ivectorPath;
	private String version;
	private String wavpath;

	public String getWavPath() {
		return wavpath;
	}

	public void setWavPath(String voicePath) {
		this.wavpath = voicePath;
	}

	public IvectorV() {
	}

	public IvectorV(String telnum, String url, String version, String wavpath) {
		super();
		this.telnum = telnum;
		this.ivectorPath = url;
		this.version = version;
		this.wavpath = wavpath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getUrl() {
		return ivectorPath;
	}
	public void setUrl(String url) {
		this.ivectorPath = url;
	}

	public String getIvectorPath() {

		return ivectorPath;
	}
	
	public void setIvectorPath(String ivectorPath){
		this.ivectorPath = ivectorPath;
	}



}
