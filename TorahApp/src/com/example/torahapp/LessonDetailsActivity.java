package com.example.torahapp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;

public class LessonDetailsActivity extends Activity{

	Context mContext;
	WebserviceReader wr;
	ProgressBar cntLoadBar;
	Bundle lessonDetail,questionList;
	JSONObject lessonDetailObj;
	TextView tvLessonLetter,tvLessonDetails,tvLessonNo;
	Button btnNext,btnBack;
	ScrollView scrlLessonContent;
	String lessonNo;
	JSONArray questionArray;
	JSONObject[] questionObj;
	MediaPlayer mediaPlayer;
	File letter,desc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_detail);
		mContext = this;
		mediaPlayer = new MediaPlayer();
		wr = new WebserviceReader();
		lessonDetail = getIntent().getExtras();
		questionList = new Bundle();
		tvLessonLetter = (TextView)findViewById(R.id.tvLessonLetter);
		tvLessonDetails = (TextView)findViewById(R.id.tvLessonDetails);
		tvLessonNo = (TextView)findViewById(R.id.tvLessonNo);
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		scrlLessonContent = (ScrollView)findViewById(R.id.scrlLessonContent);
		cntLoadBar = (ProgressBar)findViewById(R.id.cntLoadBar);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		CommonObject.cnt=0;
		try {
			Log.d("DATA", lessonDetail.getString("LessonDetails").toString());
			lessonDetailObj = new JSONObject(lessonDetail.getString("LessonDetails").toString());
			lessonNo = lessonDetailObj.getString("lesson_name");
			tvLessonNo.setText(lessonDetailObj.getString("lesson_name"));
			tvLessonLetter.setText(lessonDetailObj.getString("lesson_letter"));
			tvLessonDetails.setText(Html.fromHtml(lessonDetailObj.getString("lesson_desc")));
			
			if(isNetworkAvailable())
			{
				new QuestionAsyncTask().execute(CommonObject.baseUrl+"questionlist/lesson_id/"+lessonDetailObj.getString("lesson_id"));
			}
			else
			{
				Toast.makeText(mContext, "Connection is not available",Toast.LENGTH_SHORT).show();
			}
			
			btnNext.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(CommonObject.questionCount!=0)
					{
						if(CommonObject.questionType[CommonObject.cnt].equals("1"))
						{
							Intent questionDetailIntent = new Intent(getApplicationContext(), BowlQuestionActivity.class);
							startActivity(questionDetailIntent);
						}
						else if(CommonObject.questionType[CommonObject.cnt].equals("2"))
						{
							Intent questionDetailIntent = new Intent(getApplicationContext(), MatchQuestionActivity.class);
							startActivity(questionDetailIntent);
						}
						else if(CommonObject.questionType[CommonObject.cnt].equals("3"))
						{
							Intent questionDetailIntent = new Intent(getApplicationContext(), MultiSelectQuestionActivity.class);
							startActivity(questionDetailIntent);
						}
						else if(CommonObject.questionType[CommonObject.cnt].equals("4"))
						{
							Intent questionDetailIntent = new Intent(getApplicationContext(), RouteQuestionActivity.class);
							startActivity(questionDetailIntent);
						}
						else if(CommonObject.questionType[CommonObject.cnt].equals("5"))
						{
							Intent questionDetailIntent = new Intent(getApplicationContext(), WordSearchActivity.class);
							startActivity(questionDetailIntent);
						}
						else if(CommonObject.questionType[CommonObject.cnt].equals("6"))
						{
							Intent questionDetailIntent = new Intent(getApplicationContext(), ReadingQuestionActivity.class);
							startActivity(questionDetailIntent);
						}
					}
					else
					{
						Toast.makeText(mContext, "No Exercise Found", Toast.LENGTH_SHORT).show();
					}
				}
			});
			btnBack.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressLint("NewApi")
	class QuestionAsyncTask extends AsyncTask<String, Void, JSONObject>
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
			try {
				letter = wr.fileDownload(CommonObject.audioUrl, lessonDetailObj.getString("lesson_name"), lessonDetailObj.getString("lesson_letter_audio"));
				desc = wr.fileDownload(CommonObject.audioUrl, lessonDetailObj.getString("lesson_name"), lessonDetailObj.getString("lesson_audio"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			cntLoadBar.setVisibility(View.GONE);
			tvLessonNo.setVisibility(View.VISIBLE);
			tvLessonLetter.setVisibility(View.VISIBLE);
			scrlLessonContent.setVisibility(View.VISIBLE);
			btnNext.setVisibility(View.VISIBLE);
			btnBack.setVisibility(View.VISIBLE);
			
			try {
				Log.d("QUESTION DATA", result.toString());
				questionArray = new JSONArray(result.getString("question_list"));
				questionObj = new JSONObject[questionArray.length()];
				CommonObject.questionId = new String[questionArray.length()];
				CommonObject.questionType = new String[questionArray.length()];
				CommonObject.questionCount = questionArray.length();
				for(int i=0;i<questionArray.length();i++)
				{
					questionObj[i] = questionArray.getJSONObject(i); 
					CommonObject.questionId[i] = questionObj[i].getString("question_id");
					CommonObject.questionType[i] = questionObj[i].getString("question_type_id");
				}
				tvLessonLetter.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						playAudio(letter);
					}
				});
				tvLessonDetails.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						playAudio(desc);
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		letter.delete();
		desc.delete();
	}
	public void playAudio(File f)
	{
		try{
			FileDescriptor fd = null;
	        FileInputStream fis = new FileInputStream(f);
	        fd = fis.getFD();
	        
	        if (fd != null) {
	        	mediaPlayer.reset();
	            mediaPlayer.setDataSource(fd);
	            mediaPlayer.prepare();
	            mediaPlayer.start();
	        }
		}catch(Exception e){
			e.printStackTrace();
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
