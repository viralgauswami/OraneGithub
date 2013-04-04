package com.example.torahapp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;
import com.example.torahcomponent.AngledTextView;

@SuppressLint("NewApi")
public class BowlQuestionActivity extends Activity {

	Context mContext;
	Resources r;
	Button btnNext,btnBack,btnReview;
	WebserviceReader wr;
	ImageView ivRound,ivIndicator;
	RelativeLayout rlBowl;
	AngledTextView[] atv = new AngledTextView[22];
	Thread t;
	TextView tvLessonTitle,tvQuestionDesc,tvScore,tvScoreTitle,tvScreen;
	JSONArray questionOptionArray;
	JSONObject[] questionOptionObj;
	JSONObject questionDetailObj;
	String[] questionOption,optionAns;
	ProgressBar pbpbContentLoad;
	String bowlType;
	int screenWidth,screenHeight;
	int totalCorrectCnt=0,corrCont=0;
	MediaPlayer mediaPlayer;
	File[] questionOptionFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bowl_question);
		
		mContext = this;
		mediaPlayer = new MediaPlayer();
		
		r = getResources();
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnReview = (Button)findViewById(R.id.btnReview); 
				
		WindowManager w = getWindowManager(); 
        Display d = w.getDefaultDisplay(); 
        screenWidth = d.getWidth(); 
        screenHeight = d.getHeight(); 

		wr = new WebserviceReader();
		tvLessonTitle = (TextView)findViewById(R.id.tvLessonTitle);
		tvQuestionDesc = (TextView)findViewById(R.id.tvQuestionDesc);
		tvScore = (TextView)findViewById(R.id.tvScore);
		tvScoreTitle = (TextView)findViewById(R.id.tvScoreTitle);
		tvScreen = (TextView)findViewById(R.id.textView1);
		ivRound = (ImageView)findViewById(R.id.imgRound);
		ivIndicator = (ImageView)findViewById(R.id.imgIndicator);
		rlBowl = (RelativeLayout)findViewById(R.id.rlBowl);
		
		atv[0] = (AngledTextView)findViewById(R.id.atv1);
		atv[1] = (AngledTextView)findViewById(R.id.atv2);
		atv[2] = (AngledTextView)findViewById(R.id.atv3);
		atv[3] = (AngledTextView)findViewById(R.id.atv4);
		atv[4] = (AngledTextView)findViewById(R.id.atv5);
		atv[5] = (AngledTextView)findViewById(R.id.atv6);
		atv[6] = (AngledTextView)findViewById(R.id.atv7);
		atv[7] = (AngledTextView)findViewById(R.id.atv8);
		atv[8] = (AngledTextView)findViewById(R.id.atv9);
		atv[9] = (AngledTextView)findViewById(R.id.atv10);
		atv[10] = (AngledTextView)findViewById(R.id.atv11);
		atv[11] = (AngledTextView)findViewById(R.id.atv12);
		atv[12] = (AngledTextView)findViewById(R.id.atv13);
		atv[13] = (AngledTextView)findViewById(R.id.atv14);
		atv[14] = (AngledTextView)findViewById(R.id.atv15);
		atv[15] = (AngledTextView)findViewById(R.id.atv16);
		atv[16] = (AngledTextView)findViewById(R.id.atv17);
		atv[17] = (AngledTextView)findViewById(R.id.atv18);
		atv[18] = (AngledTextView)findViewById(R.id.atv19);
		atv[19] = (AngledTextView)findViewById(R.id.atv20);
		atv[20] = (AngledTextView)findViewById(R.id.atv21);
		atv[21] = (AngledTextView)findViewById(R.id.atv22);
		
		pbpbContentLoad = (ProgressBar)findViewById(R.id.pbContentLoad);
		
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
		
		ivRound.setOnTouchListener(new View.OnTouchListener() {

			@Override
            public boolean onTouch(View v, MotionEvent event) {
				ivIndicator.setImageResource(R.drawable.yellow);
                final LayoutParams layoutParams = (LayoutParams) ivRound.getLayoutParams();
                int x_cord = 0,y_cord = 0;
                switch(event.getAction())
                {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
					x_cord = (int)event.getRawX();
					y_cord = (int)event.getRawY();
					
					if(tvScreen.getText().equals("screen-gen"))
					{
						if(x_cord>(rlBowl.getWidth()-ivRound.getWidth()))
						{
							x_cord=rlBowl.getWidth()-ivRound.getWidth();
						}
						if(y_cord>(rlBowl.getHeight()-ivRound.getHeight()+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics())))
						{
							y_cord=(int) (rlBowl.getHeight()-ivRound.getHeight()+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
						}
					}
					else if(tvScreen.getText().equals("screen-1"))
    				{
						if(x_cord>(rlBowl.getWidth()-ivRound.getWidth()))
						{
							x_cord=rlBowl.getWidth()-ivRound.getWidth();
						}
						if(y_cord>(rlBowl.getHeight()-ivRound.getHeight()+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics())))
						{
							y_cord=(int) (rlBowl.getHeight()-ivRound.getHeight()+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
						}
    				}
    				else if(tvScreen.getText().equals("screen-2"))
    				{
    					if(x_cord>rlBowl.getWidth())
						{
							x_cord=rlBowl.getWidth();
						}
						if(y_cord>(rlBowl.getHeight()-ivRound.getHeight()+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics())))
						{
							y_cord=(int) (rlBowl.getHeight()-ivRound.getHeight()+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, r.getDisplayMetrics()));
						}
    				}
    				else if(tvScreen.getText().equals("screen-4"))
    				{
    					if(x_cord>(rlBowl.getWidth()-ivRound.getWidth()))
    					{
    						x_cord=rlBowl.getWidth()-ivRound.getWidth();
    					}
    					if(y_cord>(rlBowl.getHeight()-ivRound.getHeight())+300)
    					{
    						y_cord=rlBowl.getHeight()-ivRound.getHeight()+300;
    					}
    				}
    				else if(tvScreen.getText().equals("screen-5"))
    				{
    					if(x_cord>(rlBowl.getWidth()-ivRound.getWidth()))
    					{
    						x_cord=rlBowl.getWidth()-ivRound.getWidth();
    					}
    					if(y_cord>(rlBowl.getHeight()-ivRound.getHeight())+400)
    					{
    						y_cord=rlBowl.getHeight()-ivRound.getHeight()+400;
    					}
    				}
    				else
    				{
    					if(x_cord>(rlBowl.getWidth()-ivRound.getWidth()))
    					{
    						x_cord=rlBowl.getWidth()-ivRound.getWidth();
    					}
    					if(y_cord>(rlBowl.getHeight()-ivRound.getHeight()))
    					{
    						y_cord=rlBowl.getHeight()-ivRound.getHeight();
    					}
    				}
					
					if(x_cord<0+ivRound.getWidth())
					{
						x_cord = 0+ivRound.getWidth();
					}
					if(y_cord<100+ivRound.getHeight())
					{
						y_cord = 100+ivRound.getHeight();
					}
					layoutParams.leftMargin = (int) (x_cord-rlBowl.getX()-ivRound.getWidth()/2);
					layoutParams.topMargin = (int) (y_cord-rlBowl.getY()-ivRound.getHeight());
					//Log.d("Margins", "("+layoutParams.leftMargin+" , "+layoutParams.topMargin+")");
					//Log.d("Bowl Size",rlBowl.getWidth()+" , "+rlBowl.getHeight());
					ivRound.setLayoutParams(layoutParams);
					break;
                case MotionEvent.ACTION_UP:
                	if(layoutParams.leftMargin>=atv[0].getX()-15 && layoutParams.topMargin>=atv[0].getY()-15 && layoutParams.leftMargin<=atv[0].getX()+15 && layoutParams.topMargin<=atv[0].getY()+15)
                	{
                		checkAnswer(atv[0], layoutParams, 0);
                	}
                	else if(layoutParams.leftMargin>=atv[1].getX()-15 && layoutParams.topMargin>=atv[1].getY()-15 && layoutParams.leftMargin<=atv[1].getX()+15 && layoutParams.topMargin<=atv[1].getY()+15)
                	{
                		checkAnswer(atv[1], layoutParams, 1);
                	}
                	else if(layoutParams.leftMargin>=atv[2].getX()-15 && layoutParams.topMargin>=atv[2].getY()-15 && layoutParams.leftMargin<=atv[2].getX()+15 && layoutParams.topMargin<=atv[2].getY()+15)
                	{
                		checkAnswer(atv[2], layoutParams, 2);
                	}
                	else if(layoutParams.leftMargin>=atv[3].getX()-15 && layoutParams.topMargin>=atv[3].getY()-15 && layoutParams.leftMargin<=atv[3].getX()+15 && layoutParams.topMargin<=atv[3].getY()+15)
                	{
                		checkAnswer(atv[3], layoutParams, 3);
                	}
                	else if(layoutParams.leftMargin>=atv[4].getX()-15 && layoutParams.topMargin>=atv[4].getY()-15 && layoutParams.leftMargin<=atv[4].getX()+15 && layoutParams.topMargin<=atv[4].getY()+15)
                	{
                		checkAnswer(atv[4], layoutParams, 4);
                	}
                	else if(layoutParams.leftMargin>=atv[5].getX()-15 && layoutParams.topMargin>=atv[5].getY()-15 && layoutParams.leftMargin<=atv[5].getX()+15 && layoutParams.topMargin<=atv[5].getY()+15)
                	{
                		checkAnswer(atv[5], layoutParams, 5);
                	}
                	else if(layoutParams.leftMargin>=atv[6].getX()-15 && layoutParams.topMargin>=atv[6].getY()-15 && layoutParams.leftMargin<=atv[6].getX()+15 && layoutParams.topMargin<=atv[6].getY()+15)
                	{
                		checkAnswer(atv[6], layoutParams, 6);
                	}
                	else if(layoutParams.leftMargin>=atv[7].getX()-15 && layoutParams.topMargin>=atv[7].getY()-15 && layoutParams.leftMargin<=atv[7].getX()+15 && layoutParams.topMargin<=atv[7].getY()+15)
                	{
                		checkAnswer(atv[7], layoutParams, 7);
                	}
                	else if(layoutParams.leftMargin>=atv[8].getX()-15 && layoutParams.topMargin>=atv[8].getY()-15 && layoutParams.leftMargin<=atv[8].getX()+15 && layoutParams.topMargin<=atv[8].getY()+15)
                	{
                		checkAnswer(atv[8], layoutParams, 8);
                	}
                	else if(layoutParams.leftMargin>=atv[9].getX()-15 && layoutParams.topMargin>=atv[9].getY()-15 && layoutParams.leftMargin<=atv[9].getX()+15 && layoutParams.topMargin<=atv[9].getY()+15)
                	{
                		checkAnswer(atv[9], layoutParams, 9);
                	}
                	else if(layoutParams.leftMargin>=atv[10].getX()-15 && layoutParams.topMargin>=atv[10].getY()-15 && layoutParams.leftMargin<=atv[10].getX()+15 && layoutParams.topMargin<=atv[10].getY()+15)
                	{
                		checkAnswer(atv[10], layoutParams, 10);
                	}
                	else if(layoutParams.leftMargin>=atv[11].getX()-15 && layoutParams.topMargin>=atv[11].getY()-15 && layoutParams.leftMargin<=atv[11].getX()+15 && layoutParams.topMargin<=atv[11].getY()+15)
                	{
                		checkAnswer(atv[11], layoutParams, 11);
                	}
                	else if(layoutParams.leftMargin>=atv[12].getX()-15 && layoutParams.topMargin>=atv[12].getY()-15 && layoutParams.leftMargin<=atv[12].getX()+15 && layoutParams.topMargin<=atv[12].getY()+15)
                	{
                		checkAnswer(atv[12], layoutParams, 12);
                	}
                	else if(layoutParams.leftMargin>=atv[13].getX()-15 && layoutParams.topMargin>=atv[13].getY()-15 && layoutParams.leftMargin<=atv[13].getX()+15 && layoutParams.topMargin<=atv[13].getY()+15)
                	{
                		checkAnswer(atv[13], layoutParams, 13);
                	}
                	else if(layoutParams.leftMargin>=atv[14].getX()-15 && layoutParams.topMargin>=atv[14].getY()-15 && layoutParams.leftMargin<=atv[14].getX()+15 && layoutParams.topMargin<=atv[14].getY()+15)
                	{
                		checkAnswer(atv[14], layoutParams, 14);
                	}
                	else if(layoutParams.leftMargin>=atv[15].getX()-15 && layoutParams.topMargin>=atv[15].getY()-15 && layoutParams.leftMargin<=atv[15].getX()+15 && layoutParams.topMargin<=atv[15].getY()+15)
                	{
                		checkAnswer(atv[15], layoutParams, 15);
                	}
                	else if(layoutParams.leftMargin>=atv[16].getX()-15 && layoutParams.topMargin>=atv[16].getY()-15 && layoutParams.leftMargin<=atv[16].getX()+15 && layoutParams.topMargin<=atv[16].getY()+15)
                	{
                		checkAnswer(atv[16], layoutParams, 16);
                	}
                	else if(layoutParams.leftMargin>=atv[17].getX()-15 && layoutParams.topMargin>=atv[17].getY()-15 && layoutParams.leftMargin<=atv[17].getX()+15 && layoutParams.topMargin<=atv[17].getY()+15)
                	{
                		checkAnswer(atv[17], layoutParams, 17);
                	}
                	else if(layoutParams.leftMargin>=atv[18].getX()-15 && layoutParams.topMargin>=atv[18].getY()-15 && layoutParams.leftMargin<=atv[18].getX()+15 && layoutParams.topMargin<=atv[18].getY()+15)
                	{
                		checkAnswer(atv[18], layoutParams, 18);
                	}
                	else if(layoutParams.leftMargin>=atv[19].getX()-15 && layoutParams.topMargin>=atv[19].getY()-15 && layoutParams.leftMargin<=atv[19].getX()+15 && layoutParams.topMargin<=atv[19].getY()+15)
                	{
                		checkAnswer(atv[19], layoutParams, 19);
                	}
                	else if(layoutParams.leftMargin>=atv[20].getX()-15 && layoutParams.topMargin>=atv[20].getY()-15 && layoutParams.leftMargin<=atv[20].getX()+15 && layoutParams.topMargin<=atv[20].getY()+15)
                	{
                		checkAnswer(atv[20], layoutParams, 20);
                	}
                	else if(layoutParams.leftMargin>=atv[21].getX()-15 && layoutParams.topMargin>=atv[21].getY()-15 && layoutParams.leftMargin<=atv[21].getX()+15 && layoutParams.topMargin<=atv[21].getY()+15)
                	{
                		checkAnswer(atv[21], layoutParams, 21);
                	}
                	break;
                }
                return true;
            }
		});
		
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(totalCorrectCnt!=corrCont)
				{
					DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int which) {
					        switch (which){
					        case DialogInterface.BUTTON_POSITIVE:
					            break;
					        }
					    }
					};
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setTitle("Information");
					builder.setMessage("Exercise is Not Over please complete it").setPositiveButton("Ok", dialogClickListener).show();
				}
				else
				{*/
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
				//}
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		CommonObject.cnt--;
		finish();
	}
	
	@SuppressLint("NewApi")
	void checkAnswer(final AngledTextView atv,final LayoutParams layoutParams,final int pos)
	{
		//ivRound.setVisibility(View.GONE);
		final int tmpScore = Integer.parseInt(tvScore.getText().toString());
		atv.setBackgroundResource(R.drawable.round_purple);
		ivRound.setVisibility(View.GONE);
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
								atv.setBackgroundResource(R.drawable.round_blue);
								ivIndicator.setImageResource(R.drawable.green);
								try {
									tvScore.setText((tmpScore+Integer.parseInt(questionDetailObj.getString("question_points")))+"");
									corrCont++;
									if(totalCorrectCnt==corrCont)
									{
										DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
										    public void onClick(DialogInterface dialog, int which) {
										        switch (which){
										        case DialogInterface.BUTTON_POSITIVE:
										            break;
										        }
										    }
										};
										AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
										builder.setTitle("Information");
										builder.setMessage("Exercise is Over").setPositiveButton("Ok", dialogClickListener).show();
									}
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
	                			atv.setBackgroundResource(R.drawable.trans);
	                			ivIndicator.setImageResource(R.drawable.red);
	                		}
							ivRound.setVisibility(View.VISIBLE);
	                		layoutParams.leftMargin = 0;
	                		layoutParams.topMargin = 0;
	                		ivRound.setLayoutParams(layoutParams);
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
			pbpbContentLoad.setVisibility(View.GONE);
			rlBowl.setVisibility(View.VISIBLE);
			btnBack.setVisibility(View.VISIBLE);
			tvScore.setVisibility(View.VISIBLE);
			tvScoreTitle.setVisibility(View.VISIBLE);
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
			try {
				Log.d("QUESTION DATA", result.getString("question"));
				questionDetailObj = new JSONObject(result.getString("question"));;
				tvLessonTitle.setText(questionDetailObj.getString("lesson_name"));
				tvQuestionDesc.setText(questionDetailObj.getString("question_text"));
				bowlType = questionDetailObj.getString("question_bwl_type");
				
				Log.d("OPTION DATA", result.getString("option"));
				questionOptionArray = new JSONArray(result.getString("option"));
				questionOptionObj = new JSONObject[questionOptionArray.length()];
				questionOption = new String[questionOptionArray.length()];
				optionAns = new String[questionOptionArray.length()];
				for(int i=0;i<questionOptionArray.length();i++)
				{
					questionOptionObj[i] = questionOptionArray.getJSONObject(i);
					questionOption[i] = questionOptionObj[i].getString("bwl_option_text");
					optionAns[i] = questionOptionObj[i].getString("bwl_is_ans");
					atv[i].setText(questionOption[i]);
					atv[i].setOnClickListener(new myAudioLisatner(i));
					if(optionAns[i].equals("1"))
					{
						totalCorrectCnt++;
					}
				}
				
				//Check bowl type
				if(bowlType.equals("bowl-1"))
				{
					rlBowl.setBackgroundResource(R.drawable.bowl1);
				}
				else if(bowlType.equals("bowl-2"))
				{
					rlBowl.setBackgroundResource(R.drawable.bowl2);
				}
				else if(bowlType.equals("bowl-3"))
				{
					rlBowl.setBackgroundResource(R.drawable.bowl3);
				}
				else if(bowlType.equals("bowl-4"))
				{
					rlBowl.setBackgroundResource(R.drawable.bowl4);
				}
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
			if(mediaPlayer.isPlaying())
			{
				mediaPlayer.stop();
			}
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
}
