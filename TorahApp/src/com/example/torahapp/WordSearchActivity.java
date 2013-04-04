package com.example.torahapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class WordSearchActivity extends Activity {

	Context mContext;
	Button btnNext,btnBack,btnReview;
	GridView gridview;
	String[] puzzChar =
			{"ח","וֹ","מ","בָּ","שָׁ","שׁ","לָ","וֹ","שׁ"
			,"שׁ","וֹ","מ","חָּ","ל","ל","ה","מָ","בַּ"
			,"ח","בָּ","שַׁ","א","וֹ","בּ","ל","חּ","חָּ"
			,"שׁ","לָ","מָ","שָׁ","ם","בָּ","לָ","וֹ","ם"
			,"בָּ","ם","שָׁ","ה","מ","א","מָ","לָ","בַּ"
			,"ח","וֹ","ל","מַ","וֹ","בּ","ה","הָ","ח"
			,"ח","וֹ","מ","בָּ","ם","שׁ","וֹ","ל","שָׁ"};
	TextView[] tvWord = new TextView[18];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_search);
		
		mContext = this;
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnReview = (Button)findViewById(R.id.btnReview);
		
		gridview = (GridView)findViewById(R.id.gridview);
        tvWordInitilize();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		gridview.setAdapter(new TextViewAdapter(this, android.R.layout.simple_list_item_1,puzzChar));
		
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
	
	private void tvWordInitilize() {
		// TODO Auto-generated method stub
    	tvWord[0] = (TextView)findViewById(R.id.tvWord1);
    	tvWord[1] = (TextView)findViewById(R.id.tvWord2);
    	tvWord[2] = (TextView)findViewById(R.id.tvWord3);
    	tvWord[3] = (TextView)findViewById(R.id.tvWord4);
    	tvWord[4] = (TextView)findViewById(R.id.tvWord5);
    	tvWord[5] = (TextView)findViewById(R.id.tvWord6);
    	tvWord[6] = (TextView)findViewById(R.id.tvWord7);
    	tvWord[7] = (TextView)findViewById(R.id.tvWord8);
    	tvWord[8] = (TextView)findViewById(R.id.tvWord9);
    	tvWord[9] = (TextView)findViewById(R.id.tvWord10);
    	tvWord[10] = (TextView)findViewById(R.id.tvWord11);
    	tvWord[11] = (TextView)findViewById(R.id.tvWord12);
    	tvWord[12] = (TextView)findViewById(R.id.tvWord13);
    	tvWord[13] = (TextView)findViewById(R.id.tvWord14);
    	tvWord[14] = (TextView)findViewById(R.id.tvWord15);
    	tvWord[15] = (TextView)findViewById(R.id.tvWord16);
    	tvWord[16] = (TextView)findViewById(R.id.tvWord17);
    	tvWord[17] = (TextView)findViewById(R.id.tvWord18);
	}
    
    private class TextViewAdapter extends ArrayAdapter {
	    Context ctxt;
	    String word = new String();
	    int pos[],cnt=0;
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
	      label.setText(puzzChar[position]);
	      label.setTextColor(Color.BLACK);
	      label.setBackgroundResource(R.drawable.backwhite);
	      label.setGravity(17);
	      label.setWidth(33);
	      label.setHeight(33);
	      label.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tmpTv = (TextView)v;
				tmpTv.setBackgroundResource(R.drawable.backyellow);
				word = word + tmpTv.getText();
				Log.d("WORD",word);
				tvCorrect[cnt++]=tmpTv;
				//pos[cnt++]=position;
				if(word.equals("בָּמוֹח")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[0].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("לָמָה")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[1].setBackgroundColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if (word.equals("שָׁה")) {
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[2].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("לָשׁ")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[3].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("מוֹם")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[4].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("בָּם")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[5].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("בָּא")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[6].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("בּוֹא")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[7].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("חָּם")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[8].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("בָּלָם")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[9].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("שָׁלוֹשׁ")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[10].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("בַּח")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[11].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("מָה")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[12].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("שַׁבָּח")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[13].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("שָׁלוֹם")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[14].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("מָשָׁל")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[15].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("שָׁם")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[16].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.equals("מוֹח")){
					Toast.makeText(ctxt, "Word Done", Toast.LENGTH_SHORT).show();
					word="";
					tvWord[17].setTextColor(Color.GREEN);
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backgreen);
					}
					cnt=0;
				}
				else if(word.length()>5)
				{
					word="";
					for(int i=0;i<cnt;i++)
					{
						tvCorrect[i].setBackgroundResource(R.drawable.backwhite);
					}
					cnt=0;
				}
			}
	      });
	      return(convertView);
	    }
	  }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		CommonObject.cnt--;
		finish();
	}
}
