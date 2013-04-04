package com.application.torah.webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class WebserviceReader {
	
	StringBuilder builder;
	JSONObject resObj;
	public JSONObject sendRequest(String url)
	{
		try {
			String postUrl = url;
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(postUrl);
			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String line;
			builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			resObj = new JSONObject(builder.toString());
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("ERROR", e.toString());
		}
		return resObj;
	}
}
