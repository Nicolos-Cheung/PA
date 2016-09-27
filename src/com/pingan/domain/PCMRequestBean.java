package com.pingan.domain;

public class PCMRequestBean {

	private String user_id;
	private String person_id;
	private String telnum;
	private String source;
	private String policy_number;
	private String nas_dir;
	private String response_num;
	private String ivector_version;
	private String register_voice_path;
	private String register_date;
	private String ivector_path;
	private String available; // 1用户可用 0用户不可用

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getIvector_path() {
		return ivector_path;
	}

	public void setIvector_path(String ivector_path) {
		this.ivector_path = ivector_path;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getTelnum() {
		return telnum;
	}

	public PCMRequestBean() {

	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPolicy_number() {
		return policy_number;
	}

	public void setPolicy_number(String policy_number) {
		this.policy_number = policy_number;
	}

	public String getNas_dir() {
		return nas_dir;
	}

	public void setNas_dir(String nas_dir) {
		this.nas_dir = nas_dir;
	}

	public String getResponse_num() {
		return response_num;
	}

	public void setResponse_num(String response_num) {
		this.response_num = response_num;
	}

	public String getIvector_version() {
		return ivector_version;
	}

	public void setIvector_version(String ivector_version) {
		this.ivector_version = ivector_version;
	}

	public String getRegister_voice_path() {
		return register_voice_path;
	}

	public void setRegister_voice_path(String register_voice_path) {
		this.register_voice_path = register_voice_path;
	}

	public String getRegister_date() {
		return register_date;
	}

	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}

	@Override
	public String toString() {
		return "PCMRequestBean [user_id=" + user_id + ", person_id="
				+ person_id + ", telnum=" + telnum + ", source=" + source
				+ ", policy_number=" + policy_number + ", nas_dir=" + nas_dir
				+ ", response_num=" + response_num + ", ivector_version="
				+ ivector_version + ", register_voice_path="
				+ register_voice_path + ", register_date=" + register_date
				+ ", ivector_path=" + ivector_path + ", available=" + available
				+ "]";
	}

}
