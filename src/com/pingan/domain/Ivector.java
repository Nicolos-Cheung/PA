package com.pingan.domain;

public class Ivector {

	private String telnum;
	private String url;

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Ivector(String telnum, String url) {
		super();
		this.telnum = telnum;
		this.url = url;
	}

	public Ivector() {
	}

}
