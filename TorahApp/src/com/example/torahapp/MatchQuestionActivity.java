package com.example.torahapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MatchQuestionActivity extends Activity {

	Context mContext;
	Button btnNext,btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_question);
		
		mContext = this;
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(CommonObject.cnt==CommonObject.questionCount-1)
		{
			btnNext.setVisibility(View.GONE);
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
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		CommonObject.cnt--;
		finish();
	}
}
