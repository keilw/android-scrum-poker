package com.rmgtug.scrumpoker.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class HttpConnection {

	private static String LOG_TAG = "HttpConnection";
	
	private int lastStatusCode = -1;
	
	private static HttpConnection instance = null;
	
	/**
	 * object to set parameters like timeout
	 */
	private HttpParams httpParameters = new BasicHttpParams();

	/**
	 * Create a local instance of cookie store
	 */
	private final CookieStore cookieStore = new BasicCookieStore();

	/**
	 * Create local HTTP context
	 */
	public final HttpContext localContext = new BasicHttpContext();

	
	private HttpConnection() {
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		// set up some default parameters for the http client
		HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);
	}
	
	public static HttpConnection getInstance() {
		if (instance == null) {
			instance = new HttpConnection();
		}
		return instance;
	}
	
	/**
	 * Get a new HttpClient with all necessary parameters, cookies and credentials
	 * 
	 * @return httpClient
	 */
	public DefaultHttpClient newHttpClient() {
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		return client;
	}
	
	public int getLastStatusCode() {
		return lastStatusCode;
	}
	
	/**
	 * Get the input stream from the given URL, requested with GET.
	 * 
	 * @param url
	 * @return inputStream
	 */
	public String getData(String url) {
		String content = null;
		// get a new client with correct context and credentials
		DefaultHttpClient httpClient = newHttpClient();
		// get request
		HttpGet httpget = new HttpGet();
		httpget.setURI(URI.create(url));

		HttpResponse response;
		try {

			int statusCode = 0;
			int retryCount = 0;

			// will try 3 times to get a positive answer before showing error
			while (statusCode != 200 && retryCount < 3) {
				// execute the get request (with local context used for cookies)
				response = httpClient.execute(httpget, localContext);
				statusCode = response.getStatusLine().getStatusCode();
				
				lastStatusCode = statusCode;

				// list cookies
				//List<Cookie> cookies = cookieStore.getCookies();

				if (statusCode != 200) {
					if (statusCode == 402) {
						// user is not logged in
						Log.e(LOG_TAG, "User not logged in, error code = " + statusCode);
						break;
					} else {
						Log.e(LOG_TAG, "Server response code " + statusCode + " on URL '" + url);
						// get the response entity
						HttpEntity entity = response.getEntity();

						if (entity != null) {
							// read content
							InputStream inputStream = entity.getContent();
							// get content as string
							content = convertStreamToString(inputStream);
							inputStream.close();
							Log.e(LOG_TAG, "Server response content: " + content);
						}
					}
				} else {
					// get the response entity
					HttpEntity entity = response.getEntity();

					if (entity != null) {
						// read content
						InputStream inputStream = entity.getContent();
						// get content as string
						content = convertStreamToString(inputStream);

						// closing input stream to trigger connection release
						inputStream.close();
					}
				}
				retryCount++;
			}

		} catch (ClientProtocolException e) {
			Log.e(LOG_TAG, "ClientProtocolException: " + e.getMessage());
			lastStatusCode = -1;
		} catch (SocketTimeoutException e) {
			Log.e(LOG_TAG, "SocketTimeoutException: " + e.getMessage());
			lastStatusCode = -1;
		} catch (ConnectTimeoutException e) {
			Log.e(LOG_TAG, "ConnectTimeoutException: " + e.getMessage());
			lastStatusCode = -1;
		} catch (IllegalStateException e) {
			Log.e(LOG_TAG, "IllegalStateException: " + e.getMessage());
			lastStatusCode = -1;
		} catch (IOException e) {
			Log.e(LOG_TAG, "IOException: " + e.getMessage());
			lastStatusCode = -1;
		} finally {
			// release resources
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}

		return content;
	}
	
	
	/**
	 * Get a String from the InputStream
	 * 
	 * @param is
	 * @return str
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
}
