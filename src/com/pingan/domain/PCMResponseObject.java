package com.pingan.domain;

public class PCMResponseObject {

	private String customer_id;
	private String ivector;

	public PCMResponseObject(String customer_id, String ivector) {
		super();
		this.customer_id = customer_id;
		this.ivector = ivector;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getIvector() {
		return ivector;
	}

	public void setIvector(String ivector) {
		this.ivector = ivector;
	}

}
