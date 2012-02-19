package com.rmgtug.scrumpoker.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import android.util.Log;


/**
 * @author stadolf@gmail.com
 * 
 * handles incoming URIs on a VERY basic level. E.G ///user/Vgtfg/setcard/13 
 * @link http://jetty.codehaus.org/jetty/jetty-6/apidocs/org/mortbay/jetty/handler/AbstractHandler.html
 * 
 * could also use a ServletHandler to register real servlets. 
 */

public class RestServiceHandler extends AbstractHandler{
	 
	protected final static String LOG_CAT = "com.rmgtug.scrumpoker";
	
	@Override
	public void handle(String target, Request request, HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) throws IOException, ServletException {
	
		Log.i(LOG_CAT, "req received: " +request.getRequestURI());
		servletResponse.setContentType("text/html");
        servletResponse.setStatus(HttpServletResponse.SC_OK);
        String response = dispatch(request.getRequestURI());
        
        servletResponse.getWriter().println("<h2>Response:" +response + " </h2>");
        ((Request) request).setHandled(true);
	}
	
	public String dispatch(String requestUri) {
		String[] comp = requestUri.split("/");
		if ("user".equals(comp[0])) {
			String userName = comp[1];
			String action = comp[2];
			String cardid = comp[3];
			if ("setcard".equals(action)) 
				return setCard(userName, cardid);
		} 
		
		return "No response";
	}
	
	/**
	 * 
	 * @param userName
	 * @param cardId
	 * @return a readable message
	 */
	protected String setCard(String userName, String cardId) {
		Log.i("com.rmgtug", "set card "+cardId+" for "+userName+ " ");
		return "Card " + cardId + " set for "+ userName;
	}
}
