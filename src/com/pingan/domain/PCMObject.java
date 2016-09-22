package com.pingan.domain;

public class PCMObject {

	private String userid;
	private String ivector;
	private String voicepath;
	private String version;
	private String desc;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIvector() {
		return ivector;
	}
	public void setIvector(String ivector) {
		this.ivector = ivector;
	}
	public String getVoicepath() {
		return voicepath;
	}
	public void setVoicepath(String voicepath) {
		this.voicepath = voicepath;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public PCMObject(String userid, String ivector, String voicepath,
			String version, String desc) {
		super();
		this.userid = userid;
		this.ivector = ivector;
		this.voicepath = voicepath;
		this.version = version;
		this.desc = desc;
	}
	public PCMObject(){
		
	}

}
