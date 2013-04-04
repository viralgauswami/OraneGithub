package com.example.torahapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;

public class RouteQuestionActivity extends Activity {

	Context mContext;
	Button btnNext,btnBack,btnReview;
	WebserviceReader wr;
	GridView gridview;
	TextView tvLessonTitle,tvQuestionDesc,tvScore,tvScoreTitle;
	JSONArray questionOptionArray;
	JSONObject[] questionOptionObj;
	JSONObject questionDetailObj,questionSolutionObj;
	String[] questionOption;
	String solutionRoute;
	String[] route;
	int[] routePos;
	ImageView[] ivMouse,ivHome;
	LinearLayout llImageTop,llImageBottom;
	ProgressBar pbContentLoad;
	int score=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_question);
		
		mContext = this;
		wr = new WebserviceReader();
		gridview = (GridView)findViewById(R.id.gridview);
		tvLessonTitle = (TextView)findViewById(R.id.tvLessonTitle);
		tvQuestionDesc = (TextView)findViewById(R.id.tvQuestionDesc);
		
		ivMouse = new ImageView[5];
		ivHome = new ImageView[5];
		
		ivMouse[0] = (ImageView)findViewById(R.id.ivMouse1);
		ivMouse[1] = (ImageView)findViewById(R.id.ivMouse2);
		ivMouse[2] = (ImageView)findViewById(R.id.ivMouse3);
		ivMouse[3] = (ImageView)findViewById(R.id.ivMouse4);
		ivMouse[4] = (ImageView)findViewById(R.id.ivMouse5);
		
		ivHome[0] = (ImageView)findViewById(R.id.ivHome1);
		ivHome[1] = (ImageView)findViewById(R.id.ivHome2);
		ivHome[2] = (ImageView)findViewById(R.id.ivHome3);
		ivHome[3] = (ImageView)findViewById(R.id.ivHome4);
		ivHome[4] = (ImageView)findViewById(R.id.ivHome5);
			
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnReview = (Button)findViewById(R.id.btnReview);
		
		llImageTop = (LinearLayout)findViewById(R.id.llImageTop);
		llImageBottom = (LinearLayout)findViewById(R.id.llImageBottom);
		
		pbContentLoad = (ProgressBar)findViewById(R.id.pbContentLoad);
		
		tvScore = (TextView)findViewById(R.id.tvScore);
		tvScoreTitle = (TextView)findViewById(R.id.tvScoreTitle);
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
			return obj;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			pbContentLoad.setVisibility(View.GONE);
			tvLessonTitle.setVisibility(View.VISIBLE);
			tvQuestionDesc.setVisibility(View.VISIBLE);
			llImageTop.setVisibility(View.VISIBLE);
			gridview.setVisibility(View.VISIBLE);
			llImageBottom.setVisibility(View.VISIBLE);
			tvScoreTitle.setVisibility(View.VISIBLE);
			tvScore.setVisibility(View.VISIBLE);
			btnBack.setVisibility(View.VISIBLE);
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
				questionDetailObj = new JSONObject(result.getString("question"));
				tvLessonTitle.setText(questionDetailObj.getString("lesson_name"));
				tvQuestionDesc.setText(questionDetailObj.getString("question_text"));
				
				Log.d("OPTION DATA", result.getString("option"));
				questionOptionArray = new JSONArray(result.getString("option"));
				questionOptionObj = new JSONObject[questionOptionArray.length()];
				questionOption = new String[questionOptionArray.length()];
				for(int i=0;i<questionOptionArray.length();i++)
				{
					questionOptionObj[i] = questionOptionArray.getJSONObject(i);
					questionOption[i] = questionOptionObj[i].getString("route_text");
				}
				gridview.setAdapter(new TextViewAdapter(mContext, android.R.layout.simple_list_item_1,questionOption));
				
				Log.d("SOLUTION DATA", result.getString("solution"));
				questionSolutionObj = new JSONObject(result.getString("solution"));
				solutionRoute = questionSolutionObj.getString("route_text");
				route = solutionRoute.split("(?<=\\S)\\s+(?=\\S)");
				routePos = new int[route.length];
				for(int i=0;i<route.length;i++)
				{
					routePos[i] = StringToNumber(route[i]);
				}
				
				ivMouse[routePos[0]].setImageResource(R.drawable.mouse);
				if(routePos[route.length-1]==20)
				{
					ivHome[0].setImageResource(R.drawable.home_icon);
				}
				else if(routePos[route.length-1]==21)
				{
					ivHome[1].setImageResource(R.drawable.home_icon);
				}
				else if(routePos[route.length-1]==22)
				{
					ivHome[2].setImageResource(R.drawable.home_icon);
				}
				else if(routePos[route.length-1]==23)
				{
					ivHome[3].setImageResource(R.drawable.home_icon);
				}
				else if(routePos[route.length-1]==24)
				{
					ivHome[4].setImageResource(R.drawable.home_icon);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    private class TextViewAdapter extends ArrayAdapter {
	    Context ctxt;
	    String word = new String();
	    int cnt=0;
	    TextView[] tvCorrect = new TextView[5];
	    TextViewAdapter(Context ctxt, int resource,
	                        String[] items) {
	      super(ctxt, resource, items);
	      this.ctxt=ctxt;
	    }
	    
	    public View getView(final int position, View convertView,
	                          ViewGroup parent) {
	      TextView label=(TextView)convertView;
	      
	      if (convertView==null) {
	        convertView=new TextView(ctxt);
	        label=(TextView)convertView;
	      }
	      label.setText(questionOption[position]);
	      label.setTextColor(Color.BLACK);
	      label.setBackgroundResource(R.drawable.backwhite);
	      label.setWidth(33);
	      label.setHeight(33);
	      label.setGravity(17);
	      label.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				TextView tmpTv = (TextView)v;
				final int tmpScore = Integer.parseInt(tvScore.getText().toString());
				if(cnt<routePos.length)
				{
					if(routePos[cnt]==position)
					{
						tmpTv.setBackgroundResource(R.drawable.backgreen);
						tmpTv.setEnabled(false);
						try {
							tvScore.setText((tmpScore+Integer.parseInt(questionDetailObj.getString("question_points")))+"");
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cnt++;
					}
					if(cnt==routePos.length)
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
						builder.setMessage("You Found the correct root").setPositiveButton("Ok", dialogClickListener).show();
					}
				}
				else
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
					builder.setMessage("You Found the correct root").setPositiveButton("Ok", dialogClickListener).show();
				}
				
			}
		});
	      return(convertView);
	    }
    }
    public int StringToNumber(String pos)
    {
    	if(pos.equals("00"))
    		return 0;
    	else if(pos.equals("01"))
    		return 1;
    	else if(pos.equals("02"))
    		return 2;
    	else if(pos.equals("03"))
    		return 3;
    	else if(pos.equals("04"))
    		return 4;
    	else if(pos.equals("10"))
    		return 5;
    	else if(pos.equals("11"))
    		return 6;
    	else if(pos.equals("12"))
    		return 7;
    	else if(pos.equals("13"))
    		return 8;
    	else if(pos.equals("14"))
    		return 9;
    	else if(pos.equals("20"))
    		return 10;
    	else if(pos.equals("21"))
    		return 11;
    	else if(pos.equals("22"))
    		return 12;
    	else if(pos.equals("23"))
    		return 13;
    	else if(pos.equals("24"))
    		return 14;
    	else if(pos.equals("30"))
    		return 15;
    	else if(pos.equals("31"))
    		return 16;
    	else if(pos.equals("32"))
    		return 17;
    	else if(pos.equals("33"))
    		return 18;
    	else if(pos.equals("34"))
    		return 19;
    	else if(pos.equals("40"))
    		return 20;
    	else if(pos.equals("41"))
    		return 21;
    	else if(pos.equals("42"))
    		return 22;
    	else if(pos.equals("43"))
    		return 23;
    	else if(pos.equals("44"))
    		return 24;
    	return 0;
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
