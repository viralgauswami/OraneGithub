package com.example.torahapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnStartLesson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnStartLesson = (Button)findViewById(R.id.btnStartLesson);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		btnStartLesson.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent lessonList = new Intent(getApplicationContext(), LessonActivity.class);
				startActivity(lessonList);
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
