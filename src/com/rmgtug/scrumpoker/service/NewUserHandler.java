package com.rmgtug.scrumpoker.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rmgtug.scrumpoker.SessionInfo;
import com.rmgtug.scrumpoker.adapter.SessionListAdapter;

import android.util.Log;

public class NewUserHandler implements IHandler{

	public void handle(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		
		String requestUri = req.getRequestURI();
		String[] comp = requestUri.split("/");
		
		if (comp == null || comp.length == 0)
			throw new ServletException("can't handle "+ requestUri);
		
		String userName = comp[2];
		String ip = req.getRemoteAddr();
		boolean success = addUser(userName, ip);
		
		if (success) {
			res.setContentType("text/html");
			res.setStatus(HttpServletResponse.SC_OK);
		} else {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
	}
	
	/**
	 * 
	 * @param userName
	 * @param cardId
	 * @return true if the user has been added successfully
	 */
	protected boolean addUser(String userName, String ip) {
		SessionListAdapter slaSingleton = SessionListAdapter.getInstance(null);
		SessionInfo si = new SessionInfo(userName, null);
		si.setIpAddress(ip);
		slaSingleton.addSessionItem(si);
		
		Log.i("com.rmgtug", "added user "+userName);
		
		return true;
	}
	
}
