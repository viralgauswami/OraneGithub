package com.example.torahapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;

public class LessonActivity extends Activity{

	ProgressBar cntLoadBar;
	ListView lstLesson;
	Context mContext;
	JSONArray lessonArray;
	JSONObject[] lessonObj;
	String[] lessonTitle;
	MySimpleArrayAdapter lstLessonAdptr;
	WebserviceReader wr;
	Bundle lessonDetail;
	Button btnNext,btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson);
		cntLoadBar = (ProgressBar)findViewById(R.id.cntLoadBar);
		lstLesson = (ListView)findViewById(R.id.lstLesson);
		mContext = this;
		wr = new WebserviceReader();
		lessonDetail = new Bundle();
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(isNetworkAvailable())
		{
			new LessonAsyncTask().execute(CommonObject.baseUrl+"lessonlist");
		}
		else
		{
			Toast.makeText(mContext, "Connection is not available",Toast.LENGTH_SHORT).show();
		}
		btnNext.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lessonDetail.putString("LessonDetails", lessonObj[0].toString());
				Intent lessonDetailIntent = new Intent(getApplicationContext(), LessonDetailsActivity.class);
				lessonDetailIntent.putExtras(lessonDetail);
				startActivity(lessonDetailIntent);
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	class LessonAsyncTask extends AsyncTask<String, Void, JSONObject>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject obj = wr.sendRequest(params[0]);
			return obj;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			cntLoadBar.setVisibility(View.GONE);
			lstLesson.setVisibility(View.VISIBLE);
			btnNext.setVisibility(View.VISIBLE);
			btnBack.setVisibility(View.VISIBLE);
			
			try {
				Log.d("Lesson DATA", result.getString("lesson_list"));
				lessonArray = new JSONArray(result.getString("lesson_list"));
				lessonObj = new JSONObject[lessonArray.length()];
				lessonTitle = new String[lessonArray.length()];
				CommonObject.lessonId = new String[lessonArray.length()];
				for(int i=0;i<lessonArray.length();i++)
				{
					lessonObj[i] = lessonArray.getJSONObject(i); 
					CommonObject.lessonId[i] = lessonObj[i].getString("lesson_id");
					lessonTitle[i] = lessonObj[i].getString("lesson_name");
				}
				lstLessonAdptr = new MySimpleArrayAdapter(LessonActivity.this, lessonTitle);
				lstLesson.setAdapter(lstLessonAdptr);
				lstLesson.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						CommonObject.lessonPos = arg2;
						lessonDetail.putString("LessonDetails", lessonObj[arg2].toString());
						Intent lessonDetailIntent = new Intent(getApplicationContext(), LessonDetailsActivity.class);
						lessonDetailIntent.putExtras(lessonDetail);
						startActivity(lessonDetailIntent);
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class MySimpleArrayAdapter extends ArrayAdapter<String> 
	{
		private final Activity context;
		private final String[] names;
		public MySimpleArrayAdapter(Activity context, String[] names) 
		{
			super(context, R.layout.lessoninfl, names);
			this.context = context;
			this.names = names;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) 
		{
			
			LayoutInflater inflater = (LayoutInflater) getSystemService(context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.lessoninfl, null, true);
			
			final TextView lessonTitle = (TextView) rowView.findViewById(R.id.tvLessonTitleInfl);
			lessonTitle.setText(names[position]);
			
			return rowView;
		}
	}
	//Check network is available or not 
    private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}
