package com.rmgtug.scrumpoker.service;

import java.io.IOException;
import java.util.HashMap;

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

public class RestServiceDispatcher extends AbstractHandler{
	 
	protected final static String LOG_CAT = "com.rmgtug.scrumpoker";
	
	protected HashMap<String, IHandler> routing; 
	
	public RestServiceDispatcher() {
		routing = new HashMap<String, IHandler>();
	}
	
	public void addRoute(String route, IHandler handler) {
		routing.put(route, handler);
	}
	
	@Override
	public void handle(String target, Request request, HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) throws IOException, ServletException {
	
		Log.i(LOG_CAT, "req received: " +request.getRequestURI());
		String requestUri = request.getRequestURI();
		String[] comp = requestUri.split("/");
		if (comp == null || comp.length == 0)
			throw new ServletException("can't handle "+ requestUri);
		
		String handlerName = comp[1];
		IHandler handler = routing.get(handlerName);
		handler.handle(servletRequest, servletResponse);
		
		((Request) request).setHandled(true);
        
	}

	
}
