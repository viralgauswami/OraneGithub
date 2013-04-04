package com.example.torahapp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.torah.webservice.WebserviceReader;

public class ReviewActivity extends Activity {

	Context mContext;
	WebserviceReader wr;
	TextView tvLessonTitle,tvReviewContent;
	ProgressBar pbContentLoad;
	JSONObject questionReviewObj;
	Button btnPlay,btnPause,btnSend,btnRec,btnStop;
	
	MediaRecorder recorder;
	File audiofile = null;
	private static final String TAG = "SoundRecordingActivity";
	
	MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		mContext = this;
		
		wr = new WebserviceReader();
		tvLessonTitle = (TextView)findViewById(R.id.tvLessonTitle);
		tvReviewContent = (TextView)findViewById(R.id.tvReviewContent);
		
		pbContentLoad = (ProgressBar)findViewById(R.id.pbContentLoad);
		
		btnPlay = (Button)findViewById(R.id.btnPlay);
		btnPause = (Button)findViewById(R.id.btnPause);
		btnStop = (Button)findViewById(R.id.btnStop);
		btnRec = (Button)findViewById(R.id.btnRec);
		btnSend = (Button)findViewById(R.id.btnSend);
		
		mediaPlayer = new MediaPlayer();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(isNetworkAvailable())
		{
			new QuestionReviewAsyncTask().execute(CommonObject.baseUrl+"review/lesson_id/"+CommonObject.lessonId[CommonObject.lessonPos]);
		}
		else
		{
			finish();
			Toast.makeText(mContext, "Connection is not available",Toast.LENGTH_SHORT).show();
		}
		btnPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnPlay.setVisibility(View.GONE);
    			btnPause.setVisibility(View.VISIBLE);
    			playAudio(audiofile);
			}
		});
		btnPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnPlay.setVisibility(View.VISIBLE);
				btnPause.setVisibility(View.GONE);
				if(mediaPlayer.isPlaying())
				{
					mediaPlayer.pause();
				}
			}
		});
		btnRec.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                btnPlay.setVisibility(View.GONE);
    			btnPause.setVisibility(View.GONE);
    			btnRec.setVisibility(View.GONE);
    			btnStop.setVisibility(View.VISIBLE);
    			try {
					startRecording();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnPlay.setVisibility(View.VISIBLE);
				btnRec.setVisibility(View.VISIBLE);
				btnStop.setVisibility(View.GONE);
				stopRecording();
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent lessonList = new Intent(getApplicationContext(), LessonActivity.class);
				startActivity(lessonList);
				finish();
			}
		});
	}
	
	class QuestionReviewAsyncTask extends AsyncTask<String, Void, JSONObject>
	{
		String lessonName;
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
			
			tvLessonTitle.setVisibility(View.VISIBLE);
			tvReviewContent.setVisibility(View.VISIBLE);
			
			pbContentLoad.setVisibility(View.GONE);
			btnRec.setVisibility(View.VISIBLE);
			btnPlay.setVisibility(View.VISIBLE);
			btnSend.setVisibility(View.VISIBLE);
			try {
				Log.d("REVIEW DATA", result.toString());
				questionReviewObj = new JSONObject(result.getString("lesson_review"));
				lessonName = questionReviewObj.getString("lesson_name");
				tvLessonTitle.setText(questionReviewObj.getString("lesson_name"));
				tvReviewContent.setText(Html.fromHtml(questionReviewObj.getString("lesson_review")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startRecording() throws IOException {

	    File sampleDir = Environment.getExternalStorageDirectory();
	    try {
	      audiofile = File.createTempFile("sound", ".3gp", sampleDir);
	    } catch (IOException e) {
	      Log.e(TAG, "sdcard access error");
	      return;
	    }
	    recorder = new MediaRecorder();
	    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(audiofile.getAbsolutePath());
	    recorder.prepare();
	    recorder.start();
	  }

	  public void stopRecording() {
	    recorder.stop();
	    recorder.release();
	    addRecordingToMediaLibrary();
	  }

	  protected void addRecordingToMediaLibrary() {
	    ContentValues values = new ContentValues(4);
	    long current = System.currentTimeMillis();
	    values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
	    values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
	    values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
	    values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
	    ContentResolver contentResolver = getContentResolver();

	    Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	    Uri newUri = contentResolver.insert(base, values);

	    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
	    Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
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
