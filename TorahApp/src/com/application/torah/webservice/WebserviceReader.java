package com.application.torah.webservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.Environment;
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
	//File Download
	public File fileDownload(String webUrl,String lessonName,String flieName)
	{
		 try {
	            URL url = new URL(webUrl+flieName);
	            Log.d("Audio Download URL", url+"");
	            
	            URLConnection connection = url.openConnection();
	            connection.connect();

	            //Create Dir
	            File direct = new File(Environment.getExternalStorageDirectory() + "/Troah/"+lessonName+"/audio/");
	            if(!direct.exists())
	            { if(direct.mkdir()){}}
	            
	            // download the file
	            InputStream input = new BufferedInputStream(url.openStream());
	            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Troah/"+lessonName+"/"+flieName);

	            byte data[] = new byte[1024];
	            int count;
	            while ((count = input.read(data)) != -1) {
	                output.write(data, 0, count);
	            }
	            output.flush();
	            output.close();
	            input.close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		 return(new File(Environment.getExternalStorageDirectory() + "/Troah/"+lessonName+"/"+flieName));
	}
}
