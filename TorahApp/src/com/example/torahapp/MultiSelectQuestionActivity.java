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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;

@SuppressLint("NewApi")
public class MultiSelectQuestionActivity extends Activity {

	Context mContext;
	Button btnNext,btnBack,btnReview;
	
	WebserviceReader wr;
	TextView tvLessonTitle,tvQuestionDesc;
	ProgressBar pbContentLoad;

	ImageView ivRect;
	RelativeLayout rlSelectArea;
	
	JSONObject questionDetailObj;
	JSONArray questionOptionArray;
	JSONObject[] questionOptionObj;
	String[][] questionOption;
	File[] questionOptionFile;
	String[] optionAns;
	String questionType;
	TextView[] tvColData;
	TextView tvScore,tvScoreTitle;
	ImageView[] ivData;
	int r1cnt,r2cnt,r3cnt,r4cnt,r5cnt;
	Thread t;
	int score=0;
	MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_select_question);
		
		mContext = this;
		mediaPlayer = new MediaPlayer();
		
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnReview = (Button)findViewById(R.id.btnReview);
		
		wr = new WebserviceReader();
		
		tvLessonTitle = (TextView)findViewById(R.id.tvLessonTitle);
		tvQuestionDesc = (TextView)findViewById(R.id.tvQuestionDesc);
		
		pbContentLoad = (ProgressBar)findViewById(R.id.pbContentLoad);
		
		ivRect = (ImageView)findViewById(R.id.ivRect);
		rlSelectArea = (RelativeLayout)findViewById(R.id.rlSelectArea);
		
		tvScore = (TextView)findViewById(R.id.tvScore);
		tvScoreTitle = (TextView)findViewById(R.id.tvScoreTitle);
		
		tvColData = new TextView[30];
		ivData = new ImageView[30];
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
		
		ivData[0]=(ImageView)findViewById(R.id.ivData11);
		ivData[1]=(ImageView)findViewById(R.id.ivData12);
		ivData[2]=(ImageView)findViewById(R.id.ivData13);
		ivData[3]=(ImageView)findViewById(R.id.ivData14);
		ivData[4]=(ImageView)findViewById(R.id.ivData15);
		ivData[5]=(ImageView)findViewById(R.id.ivData16);
		
		ivData[6]=(ImageView)findViewById(R.id.ivData21);
		ivData[7]=(ImageView)findViewById(R.id.ivData22);
		ivData[8]=(ImageView)findViewById(R.id.ivData23);
		ivData[9]=(ImageView)findViewById(R.id.ivData24);
		ivData[10]=(ImageView)findViewById(R.id.ivData25);
		ivData[11]=(ImageView)findViewById(R.id.ivData26);
		
		ivData[12]=(ImageView)findViewById(R.id.ivData31);
		ivData[13]=(ImageView)findViewById(R.id.ivData32);
		ivData[14]=(ImageView)findViewById(R.id.ivData33);
		ivData[15]=(ImageView)findViewById(R.id.ivData34);
		ivData[16]=(ImageView)findViewById(R.id.ivData35);
		ivData[17]=(ImageView)findViewById(R.id.ivData36);
		
		ivData[18]=(ImageView)findViewById(R.id.ivData41);
		ivData[19]=(ImageView)findViewById(R.id.ivData42);
		ivData[20]=(ImageView)findViewById(R.id.ivData43);
		ivData[21]=(ImageView)findViewById(R.id.ivData44);
		ivData[22]=(ImageView)findViewById(R.id.ivData45);
		ivData[23]=(ImageView)findViewById(R.id.ivData46);
		
		ivData[24]=(ImageView)findViewById(R.id.ivData51);
		ivData[25]=(ImageView)findViewById(R.id.ivData52);
		ivData[26]=(ImageView)findViewById(R.id.ivData53);
		ivData[27]=(ImageView)findViewById(R.id.ivData54);
		ivData[28]=(ImageView)findViewById(R.id.ivData55);
		ivData[29]=(ImageView)findViewById(R.id.ivData56);
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
		
		ivRect.setOnTouchListener(new View.OnTouchListener() {

			@Override
            public boolean onTouch(View v, MotionEvent event) {
                final LayoutParams layoutParams = (LayoutParams) ivRect.getLayoutParams();
                int x_cord = 0,y_cord = 0;
                switch(event.getAction())
                {
                case MotionEvent.ACTION_DOWN:
                	break;
                case MotionEvent.ACTION_MOVE:
					x_cord = (int)event.getRawX();
					y_cord = (int)event.getRawY();
					
					if(x_cord>(rlSelectArea.getWidth()-ivRect.getWidth()+25))
					{
						x_cord=rlSelectArea.getWidth()-ivRect.getWidth()+25;
					}
					if(y_cord>rlSelectArea.getHeight())
					{
						y_cord=rlSelectArea.getHeight();
					}
					if(x_cord<30)
					{ x_cord=30; }
					if(y_cord<130)
					{ y_cord = 130; }
					
					layoutParams.leftMargin = (int) (x_cord-rlSelectArea.getX()-ivRect.getWidth()/2);
					layoutParams.topMargin = (int) (y_cord-rlSelectArea.getY()-ivRect.getHeight()-ivRect.getHeight()/2);
					//Log.d("Margins", "("+layoutParams.leftMargin+" , "+layoutParams.topMargin+")");
					ivRect.setLayoutParams(layoutParams);
					break;
                case MotionEvent.ACTION_UP:
                	Log.d("rSelect Size", rlSelectArea.getHeight()+"");
                	Log.d("Margins", "("+layoutParams.leftMargin+" , "+layoutParams.topMargin+")");
                	Log.d("tvColData Position", "("+tvColData[0].getX()+" , "+tvColData[0].getY()+")");
                	if(layoutParams.leftMargin>=tvColData[0].getX()-10 && layoutParams.topMargin>=tvColData[0].getY()-10 && layoutParams.leftMargin<=tvColData[0].getX()+10 && layoutParams.topMargin<=tvColData[0].getY()+10)
                	{
                		checkAnswer(tvColData[0], ivData[0], layoutParams, 0);
                	}
                	else if(layoutParams.leftMargin>=tvColData[1].getX()-10 && layoutParams.topMargin>=tvColData[1].getY()-10 && layoutParams.leftMargin<=tvColData[1].getX()+10 && layoutParams.topMargin<=tvColData[1].getY()+10)
                	{
                		checkAnswer(tvColData[1], ivData[1], layoutParams, 1);
                	}
                	else if(layoutParams.leftMargin>=tvColData[2].getX()-10 && layoutParams.topMargin>=tvColData[2].getY()-10 && layoutParams.leftMargin<=tvColData[2].getX()+10 && layoutParams.topMargin<=tvColData[2].getY()+10)
                	{
                		checkAnswer(tvColData[2], ivData[2], layoutParams, 2);
                	}
                	else if(layoutParams.leftMargin>=tvColData[3].getX()-10 && layoutParams.topMargin>=tvColData[3].getY()-10 && layoutParams.leftMargin<=tvColData[3].getX()+10 && layoutParams.topMargin<=tvColData[3].getY()+10)
                	{
                		checkAnswer(tvColData[3], ivData[3], layoutParams, 3);
                	}
                	else if(layoutParams.leftMargin>=tvColData[4].getX()-10 && layoutParams.topMargin>=tvColData[4].getY()-10 && layoutParams.leftMargin<=tvColData[4].getX()+10 && layoutParams.topMargin<=tvColData[4].getY()+10)
                	{
                		checkAnswer(tvColData[4], ivData[4], layoutParams, 4);
                	}
                	else if(layoutParams.leftMargin>=tvColData[5].getX()-10 && layoutParams.topMargin>=tvColData[5].getY()-10 && layoutParams.leftMargin<=tvColData[5].getX()+10 && layoutParams.topMargin<=tvColData[5].getY()+10)
                	{
                		checkAnswer(tvColData[5], ivData[5], layoutParams, 5);
                	}
                	else if(layoutParams.leftMargin>=tvColData[6].getX()-10 && layoutParams.topMargin>=tvColData[6].getY()-10 && layoutParams.leftMargin<=tvColData[6].getX()+10 && layoutParams.topMargin<=tvColData[6].getY()+10)
                	{
                		checkAnswer(tvColData[6], ivData[6], layoutParams, 6);
                	}
                	else if(layoutParams.leftMargin>=tvColData[7].getX()-10 && layoutParams.topMargin>=tvColData[7].getY()-10 && layoutParams.leftMargin<=tvColData[7].getX()+10 && layoutParams.topMargin<=tvColData[7].getY()+10)
                	{
                		checkAnswer(tvColData[7], ivData[7], layoutParams, 7);
                	}
                	else if(layoutParams.leftMargin>=tvColData[8].getX()-10 && layoutParams.topMargin>=tvColData[8].getY()-10 && layoutParams.leftMargin<=tvColData[8].getX()+10 && layoutParams.topMargin<=tvColData[8].getY()+10)
                	{
                		checkAnswer(tvColData[8], ivData[8], layoutParams, 8);
                	}
                	else if(layoutParams.leftMargin>=tvColData[9].getX()-10 && layoutParams.topMargin>=tvColData[9].getY()-10 && layoutParams.leftMargin<=tvColData[9].getX()+10 && layoutParams.topMargin<=tvColData[9].getY()+10)
                	{
                		checkAnswer(tvColData[9], ivData[9], layoutParams, 9);
                	}
                	else if(layoutParams.leftMargin>=tvColData[10].getX()-10 && layoutParams.topMargin>=tvColData[10].getY()-10 && layoutParams.leftMargin<=tvColData[10].getX()+10 && layoutParams.topMargin<=tvColData[10].getY()+10)
                	{
                		checkAnswer(tvColData[10], ivData[10], layoutParams, 10);
                	}
                	else if(layoutParams.leftMargin>=tvColData[11].getX()-10 && layoutParams.topMargin>=tvColData[11].getY()-10 && layoutParams.leftMargin<=tvColData[11].getX()+10 && layoutParams.topMargin<=tvColData[11].getY()+10)
                	{
                		checkAnswer(tvColData[11], ivData[11], layoutParams, 11);
                	}
                	else if(layoutParams.leftMargin>=tvColData[12].getX()-10 && layoutParams.topMargin>=tvColData[12].getY()-10 && layoutParams.leftMargin<=tvColData[12].getX()+10 && layoutParams.topMargin<=tvColData[12].getY()+10)
                	{
                		checkAnswer(tvColData[12], ivData[12], layoutParams, 12);
                	}
                	else if(layoutParams.leftMargin>=tvColData[13].getX()-10 && layoutParams.topMargin>=tvColData[13].getY()-10 && layoutParams.leftMargin<=tvColData[13].getX()+10 && layoutParams.topMargin<=tvColData[13].getY()+10)
                	{
                		checkAnswer(tvColData[13], ivData[13], layoutParams, 13);
                	}
                	else if(layoutParams.leftMargin>=tvColData[14].getX()-10 && layoutParams.topMargin>=tvColData[14].getY()-10 && layoutParams.leftMargin<=tvColData[14].getX()+10 && layoutParams.topMargin<=tvColData[14].getY()+10)
                	{
                		checkAnswer(tvColData[14], ivData[14], layoutParams, 14);
                	}
                	else if(layoutParams.leftMargin>=tvColData[15].getX()-10 && layoutParams.topMargin>=tvColData[15].getY()-10 && layoutParams.leftMargin<=tvColData[15].getX()+10 && layoutParams.topMargin<=tvColData[15].getY()+10)
                	{
                		checkAnswer(tvColData[15], ivData[15], layoutParams, 15);
                	}
                	else if(layoutParams.leftMargin>=tvColData[16].getX()-10 && layoutParams.topMargin>=tvColData[16].getY()-10 && layoutParams.leftMargin<=tvColData[16].getX()+10 && layoutParams.topMargin<=tvColData[16].getY()+10)
                	{
                		checkAnswer(tvColData[16], ivData[16], layoutParams, 16);
                	}
                	else if(layoutParams.leftMargin>=tvColData[17].getX()-10 && layoutParams.topMargin>=tvColData[17].getY()-10 && layoutParams.leftMargin<=tvColData[17].getX()+10 && layoutParams.topMargin<=tvColData[17].getY()+10)
                	{
                		checkAnswer(tvColData[17], ivData[17], layoutParams, 17);
                	}
                	else if(layoutParams.leftMargin>=tvColData[18].getX()-10 && layoutParams.topMargin>=tvColData[18].getY()-10 && layoutParams.leftMargin<=tvColData[18].getX()+10 && layoutParams.topMargin<=tvColData[18].getY()+10)
                	{
                		checkAnswer(tvColData[18], ivData[18], layoutParams, 18);
                	}
                	else if(layoutParams.leftMargin>=tvColData[19].getX()-10 && layoutParams.topMargin>=tvColData[19].getY()-10 && layoutParams.leftMargin<=tvColData[19].getX()+10 && layoutParams.topMargin<=tvColData[19].getY()+10)
                	{
                		checkAnswer(tvColData[19], ivData[19], layoutParams, 19);
                	}
                	else if(layoutParams.leftMargin>=tvColData[20].getX()-10 && layoutParams.topMargin>=tvColData[20].getY()-10 && layoutParams.leftMargin<=tvColData[20].getX()+10 && layoutParams.topMargin<=tvColData[20].getY()+10)
                	{
                		checkAnswer(tvColData[20], ivData[20], layoutParams, 20);
                	}
                	else if(layoutParams.leftMargin>=tvColData[21].getX()-10 && layoutParams.topMargin>=tvColData[21].getY()-10 && layoutParams.leftMargin<=tvColData[21].getX()+10 && layoutParams.topMargin<=tvColData[21].getY()+10)
                	{
                		checkAnswer(tvColData[21], ivData[21], layoutParams, 21);
                	}
                	else if(layoutParams.leftMargin>=tvColData[22].getX()-10 && layoutParams.topMargin>=tvColData[22].getY()-10 && layoutParams.leftMargin<=tvColData[22].getX()+10 && layoutParams.topMargin<=tvColData[22].getY()+10)
                	{
                		checkAnswer(tvColData[22], ivData[22], layoutParams, 22);
                	}
                	else if(layoutParams.leftMargin>=tvColData[23].getX()-10 && layoutParams.topMargin>=tvColData[23].getY()-10 && layoutParams.leftMargin<=tvColData[23].getX()+10 && layoutParams.topMargin<=tvColData[23].getY()+10)
                	{
                		checkAnswer(tvColData[23], ivData[23], layoutParams, 23);
                	}
                	else if(layoutParams.leftMargin>=tvColData[24].getX()-10 && layoutParams.topMargin>=tvColData[24].getY()-10 && layoutParams.leftMargin<=tvColData[24].getX()+10 && layoutParams.topMargin<=tvColData[24].getY()+10)
                	{
                		checkAnswer(tvColData[24], ivData[24], layoutParams, 24);
                	}
                	else if(layoutParams.leftMargin>=tvColData[25].getX()-10 && layoutParams.topMargin>=tvColData[25].getY()-10 && layoutParams.leftMargin<=tvColData[25].getX()+10 && layoutParams.topMargin<=tvColData[25].getY()+10)
                	{
                		checkAnswer(tvColData[25], ivData[25], layoutParams, 25);
                	}
                	else if(layoutParams.leftMargin>=tvColData[26].getX()-10 && layoutParams.topMargin>=tvColData[26].getY()-10 && layoutParams.leftMargin<=tvColData[26].getX()+10 && layoutParams.topMargin<=tvColData[26].getY()+10)
                	{
                		checkAnswer(tvColData[26], ivData[26], layoutParams, 26);
                	}
                	else if(layoutParams.leftMargin>=tvColData[27].getX()-10 && layoutParams.topMargin>=tvColData[27].getY()-10 && layoutParams.leftMargin<=tvColData[27].getX()+10 && layoutParams.topMargin<=tvColData[27].getY()+10)                	
                	{
                		checkAnswer(tvColData[27], ivData[27], layoutParams, 27);
                	}
                	else if(layoutParams.leftMargin>=tvColData[28].getX()-10 && layoutParams.topMargin>=tvColData[28].getY()-10 && layoutParams.leftMargin<=tvColData[28].getX()+10 && layoutParams.topMargin<=tvColData[28].getY()+10)
                	{
                		checkAnswer(tvColData[28], ivData[28], layoutParams, 28);
                	}
                	else if(layoutParams.leftMargin>=tvColData[29].getX()-10 && layoutParams.topMargin>=tvColData[29].getY()-10 && layoutParams.leftMargin<=tvColData[29].getX()+10 && layoutParams.topMargin<=tvColData[29].getY()+10)
                	{
                		checkAnswer(tvColData[29], ivData[29], layoutParams, 29);
                	}
                	break;
                }
                return true;
            }

			private void checkAnswer(TextView tvColData,final ImageView ivdata,
					final LayoutParams layoutParams, final int pos) {
				// TODO Auto-generated method stub
				final int tmpScore = Integer.parseInt(tvScore.getText().toString());
				ivdata.setImageResource(R.drawable.rect_purple);
				ivRect.setVisibility(View.GONE);
				t = new Thread(){
					public void run() {
						try {
							Thread.sleep(3000);
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									if (optionAns[pos].equals("1")) 
			                		{
										try {
											tvScore.setText((tmpScore+Integer.parseInt(questionDetailObj.getString("question_points")))+"");
											ivdata.setImageResource(R.drawable.rect_blue);
										} catch (NumberFormatException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
			                		else
			                		{
			                			ivdata.setImageResource(R.drawable.trans);
			                		}
									ivRect.setVisibility(View.VISIBLE);
			                		layoutParams.leftMargin = 0;
			                		layoutParams.topMargin = 0;
			                		ivRect.setLayoutParams(layoutParams);
								}
							});
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				};
				t.start();
			}
		});
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
					questionOptionFile[i] = wr.fileDownload(CommonObject.audioUrl, questionDetailObj.getString("lesson_name"), questionOptionObj[i].getString("bwl_audio"));
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
				questionType = questionDetailObj.getString("question_bwl_type");
				
				Log.d("OPTION DATA", result.getString("option"));
				questionOptionArray = new JSONArray(result.getString("option"));
				questionOptionObj = new JSONObject[questionOptionArray.length()];
				optionAns = new String[questionOptionArray.length()];
				r1cnt=r2cnt=r3cnt=r4cnt=r5cnt=0;
				for(int i=0;i<questionOptionArray.length();i++)
				{
					questionOptionObj[i] = questionOptionArray.getJSONObject(i);
					optionAns[i] = questionOptionObj[i].getString("ms_match");
					if(questionOptionObj[i].getString("ms_line_no").equals("1") && !questionOptionObj[i].getString("ms_character").equals(""))
					{
						r1cnt++;
					}
					else if(questionOptionObj[i].getString("ms_line_no").equals("2") && !questionOptionObj[i].getString("ms_character").equals(""))
					{
						r2cnt++;
					}
					else if(questionOptionObj[i].getString("ms_line_no").equals("3") && !questionOptionObj[i].getString("ms_character").equals(""))
					{
						r3cnt++;
					}
					else if(questionOptionObj[i].getString("ms_line_no").equals("4") && !questionOptionObj[i].getString("ms_character").equals(""))
					{
						r4cnt++;
					}
					else if(questionOptionObj[i].getString("ms_line_no").equals("5") && !questionOptionObj[i].getString("ms_character").equals(""))
					{
						r5cnt++;
					}
					tvColData[i].setText(questionOptionObj[i].getString("ms_character"));
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
