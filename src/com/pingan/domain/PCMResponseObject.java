package com.pingan.domain;

public class PCMResponseObject {

	private String user_id;
	private String ivector;

	public PCMResponseObject(String user_id, String ivector) {
		super();
		this.user_id = user_id;
		this.ivector = ivector;
	}

	public String getUserId() {
		return user_id;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public String getIvector() {
		return ivector;
	}

	public void setIvector(String ivector) {
		this.ivector = ivector;
	}

}
