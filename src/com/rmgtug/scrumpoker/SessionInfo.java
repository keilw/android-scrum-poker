package com.rmgtug.scrumpoker;

public class SessionInfo {

	protected String sessionName;
	protected String imgDummy;
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
	
	
}
