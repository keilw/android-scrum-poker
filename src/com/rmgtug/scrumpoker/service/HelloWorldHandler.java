package com.rmgtug.scrumpoker.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import android.util.Log;

public class HelloWorldHandler extends AbstractHandler{
	 
	@Override
	public void handle(String target, Request request, HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) throws IOException, ServletException {
	
		Log.i("stefan",request.getRequestURI());
		servletResponse.setContentType("text/html");
        servletResponse.setStatus(HttpServletResponse.SC_OK);
        servletResponse.getWriter().println("<h1>Hello World</h1>");
        ((Request) request).setHandled(true);
	}
}
