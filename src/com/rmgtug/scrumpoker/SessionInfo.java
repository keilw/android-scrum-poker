package com.rmgtug.scrumpoker;

public class SessionInfo {

	protected String sessionName;
	protected String imgDummy;
	protected String ipAddress;
	
	public SessionInfo(String name, String image) {
		sessionName = name;
		imgDummy = image;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getImgDummy() {
		return imgDummy;
	}
	public void setImgDummy(String imgDummy) {
		this.imgDummy = imgDummy;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
