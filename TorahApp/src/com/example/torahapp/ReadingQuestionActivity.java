package com.example.torahapp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;

public class ReadingQuestionActivity extends Activity {

	Context mContext;
	Button btnNext,btnBack,btnReview;
	
	WebserviceReader wr;
	TextView tvLessonTitle,tvQuestionDesc;
	ProgressBar pbContentLoad;

	RelativeLayout rlSelectArea;
	
	JSONObject questionDetailObj;
	JSONArray questionOptionArray;
	JSONObject[] questionOptionObj;
	String[][] questionOption;
	File[] questionOptionFile;
	TextView[] tvColData;
	TextView tvScore,tvScoreTitle;
	int r1cnt,r2cnt,r3cnt,r4cnt,r5cnt;
	Thread t;
	int score=0;
	MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_question);
		
		mContext = this;
		mediaPlayer = new MediaPlayer();
		
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnReview = (Button)findViewById(R.id.btnReview);
		
		wr = new WebserviceReader();
		
		tvLessonTitle = (TextView)findViewById(R.id.tvLessonTitle);
		tvQuestionDesc = (TextView)findViewById(R.id.tvQuestionDesc);
		
		pbContentLoad = (ProgressBar)findViewById(R.id.pbContentLoad);
		
		rlSelectArea = (RelativeLayout)findViewById(R.id.rlSelectArea);
		
		tvScore = (TextView)findViewById(R.id.tvScore);
		tvScoreTitle = (TextView)findViewById(R.id.tvScoreTitle);
		
		tvColData = new TextView[30];
		inititlze();
	}
	
	private void inititlze() {
		// TODO Auto-generated method stub
		tvColData[0]=(TextView)findViewById(R.id.tvCol1Data1);
		tvColData[1]=(TextView)findViewById(R.id.tvCol1Data2);
		tvColData[2]=(TextView)findViewById(R.id.tvCol1Data3);
		tvColData[3]=(TextView)findViewById(R.id.tvCol1Data4);
		tvColData[4]=(TextView)findViewById(R.id.tvCol1Data5);
		tvColData[5]=(TextView)findViewById(R.id.tvCol1Data6);
		
		tvColData[6]=(TextView)findViewById(R.id.tvCol2Data1);
		tvColData[7]=(TextView)findViewById(R.id.tvCol2Data2);
		tvColData[8]=(TextView)findViewById(R.id.tvCol2Data3);
		tvColData[9]=(TextView)findViewById(R.id.tvCol2Data4);
		tvColData[10]=(TextView)findViewById(R.id.tvCol2Data5);
		tvColData[11]=(TextView)findViewById(R.id.tvCol2Data6);
		
		tvColData[12]=(TextView)findViewById(R.id.tvCol3Data1);
		tvColData[13]=(TextView)findViewById(R.id.tvCol3Data2);
		tvColData[14]=(TextView)findViewById(R.id.tvCol3Data3);
		tvColData[15]=(TextView)findViewById(R.id.tvCol3Data4);
		tvColData[16]=(TextView)findViewById(R.id.tvCol3Data5);
		tvColData[17]=(TextView)findViewById(R.id.tvCol3Data6);
		
		tvColData[18]=(TextView)findViewById(R.id.tvCol4Data1);
		tvColData[19]=(TextView)findViewById(R.id.tvCol4Data2);
		tvColData[20]=(TextView)findViewById(R.id.tvCol4Data3);
		tvColData[21]=(TextView)findViewById(R.id.tvCol4Data4);
		tvColData[22]=(TextView)findViewById(R.id.tvCol4Data5);
		tvColData[23]=(TextView)findViewById(R.id.tvCol4Data6);
		
		tvColData[24]=(TextView)findViewById(R.id.tvCol5Data1);
		tvColData[25]=(TextView)findViewById(R.id.tvCol5Data2);
		tvColData[26]=(TextView)findViewById(R.id.tvCol5Data3);
		tvColData[27]=(TextView)findViewById(R.id.tvCol5Data4);
		tvColData[28]=(TextView)findViewById(R.id.tvCol5Data5);
		tvColData[29]=(TextView)findViewById(R.id.tvCol5Data6);
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(isNetworkAvailable())
		{
			new QuestionOptionAsyncTask().execute(CommonObject.baseUrl+"question/question_id/"+CommonObject.questionId[CommonObject.cnt]);
		}
		else
		{
			finish();
			Toast.makeText(mContext, "Connection is not available",Toast.LENGTH_SHORT).show();
		}
		
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonObject.cnt++;
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
		});
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonObject.cnt--;
				finish();
			}
		});
		btnReview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent questionDetailIntent = new Intent(getApplicationContext(), ReviewActivity.class);
				startActivity(questionDetailIntent);
			}
		});
	}
	
	class QuestionOptionAsyncTask extends AsyncTask<String, Void, JSONObject>
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
				questionDetailObj = new JSONObject(obj.getString("question"));;
				questionOptionArray = new JSONArray(obj.getString("option"));
				questionOptionObj = new JSONObject[questionOptionArray.length()];
				questionOptionFile = new File[questionOptionArray.length()];
				for(int i=0;i<questionOptionArray.length();i++)
				{
					questionOptionObj[i] = questionOptionArray.getJSONObject(i);
					questionOptionFile[i] = wr.fileDownload(CommonObject.audioUrl, questionDetailObj.getString("lesson_name"), questionOptionObj[i].getString("read_audio"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			pbContentLoad.setVisibility(View.GONE);
			tvLessonTitle.setVisibility(View.VISIBLE);
			tvQuestionDesc.setVisibility(View.VISIBLE);
			rlSelectArea.setVisibility(View.VISIBLE);
			
			btnBack.setVisibility(View.VISIBLE);
			btnReview.setVisibility(View.VISIBLE);
			
			tvScoreTitle.setVisibility(View.VISIBLE);
			tvScore.setVisibility(View.VISIBLE);
			
			if(CommonObject.cnt==CommonObject.questionCount-1)
			{
				btnNext.setVisibility(View.GONE);
				btnReview.setVisibility(View.VISIBLE);
			}
			else
			{
				btnNext.setVisibility(View.VISIBLE);
				btnReview.setVisibility(View.GONE);
			}
			int showRow;
			try {
				Log.d("QUESTION DATA", result.getString("question"));
				questionDetailObj = new JSONObject(result.getString("question"));;
				tvLessonTitle.setText(questionDetailObj.getString("lesson_name"));
				tvQuestionDesc.setText(questionDetailObj.getString("question_text"));
				
				Log.d("OPTION DATA", result.getString("option"));
				questionOptionArray = new JSONArray(result.getString("option"));
				questionOptionObj = new JSONObject[questionOptionArray.length()];
				r1cnt=r2cnt=r3cnt=r4cnt=r5cnt=0;
				for(int i=0;i<questionOptionArray.length();i++)
				{
					questionOptionObj[i] = questionOptionArray.getJSONObject(i);
					if(questionOptionObj[i].getString("read_line_no").equals("1") && !questionOptionObj[i].getString("read_character").equals(""))
					{
						r1cnt++;
					}
					else if(questionOptionObj[i].getString("read_line_no").equals("2") && !questionOptionObj[i].getString("read_character").equals(""))
					{
						r2cnt++;
					}
					else if(questionOptionObj[i].getString("read_line_no").equals("3") && !questionOptionObj[i].getString("read_character").equals(""))
					{
						r3cnt++;
					}
					else if(questionOptionObj[i].getString("read_line_no").equals("4") && !questionOptionObj[i].getString("read_character").equals(""))
					{
						r4cnt++;
					}
					{
						r5cnt++;
					}
					tvColData[i].setText(questionOptionObj[i].getString("read_character"));
					tvColData[i].setOnClickListener(new myAudioLisatner(i));
				}
				showRow = Math.max(r1cnt, Math.max(r2cnt, Math.max(r3cnt,Math.max(r4cnt, r5cnt))));
				questionOption = new String[showRow][5];
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class myAudioLisatner implements OnClickListener
	{
		int pos;
		public myAudioLisatner(int pos) {
			// TODO Auto-generated constructor stub
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			playAudio(questionOptionFile[pos]);
		}
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
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		CommonObject.cnt--;
		finish();
	}
}