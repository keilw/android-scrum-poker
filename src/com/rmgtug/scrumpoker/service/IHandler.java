package com.rmgtug.scrumpoker.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHandler {

	public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException;
	
}
