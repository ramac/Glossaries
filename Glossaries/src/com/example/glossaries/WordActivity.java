
package com.example.glossaries;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class WordActivity extends Activity implements OnInitListener{
	private static final int QUEUE_FLUSH = 0;
	private TextToSpeech tts;
	ImageButton speaker;
	TextView definition;
	String TAG = "TEXT TO SPEECH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);
        Uri uri = getIntent().getData();
        String query=getIntent().getStringExtra("query");
        Log.w(TAG,"1");
        tts = new TextToSpeech(this,this);
        Log.w(TAG,"2");
        Cursor cursor = new SuggestionProvider().query(uri, null, null, new String[]{query}, null);

        if (cursor == null) {
            finish();
        } else {
            cursor.moveToFirst();

            TextView word = (TextView) findViewById(R.id.word);
            Typeface face1= Typeface.createFromAsset(getAssets(),"fonts/CalendarNova-Regular.ttf");
            word.setTypeface(face1);
            definition = (TextView) findViewById(R.id.definition);

            int wIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_WORD);
            int dIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_DESC);

            word.setText(cursor.getString(wIndex));
            definition.setText(cursor.getString(dIndex));
        
   speaker = (ImageButton)findViewById(R.id.speaker);
   OnClickListener myOnClickListener=new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.d(TAG,"in onCLick method");
			
			try{
				
				int status=getIntent().getIntExtra("TTS_INIT_STATUS",0 );
				
			//Log.w(TAG,new String(status));
			
			if (status== TextToSpeech.SUCCESS){
				int result = tts.setLanguage(Locale.US);
				
				if(result==(TextToSpeech.LANG_MISSING_DATA)|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.e("TTS", "This Language is not supported");
	            } else {
	            	Log.w(TAG,"Good to go..... Start speaking now...");
	                speaker.setEnabled(true);
	                speakOut();
	            }
	 
	        } else {
	            Log.e("TTS", "Initilization Failed!");
	        }
			}
			catch(Exception e){
				Log.e(TAG,e.getMessage());	
				e.printStackTrace();
				
			}
			
		}
	};
    speaker.setOnClickListener(myOnClickListener);
    
        }
    
    }
	@Override
	protected void onDestroy() {
		if(tts!=null){
			tts.stop();
			tts.shutdown();
		}

		super.onDestroy();
	}
	@Override
	public void onInit(int status) {
		Log.w(TAG,Integer.toString(status));
		getIntent().putExtra("TTS_INIT_STATUS", status);
		}
	private void speakOut() {
		Log.w(TAG,"hmm...let me try and get the text that I need to read");		
		String textToSpeak = definition.getText().toString();
		try{
		tts.speak(textToSpeak, QUEUE_FLUSH, null);
		}catch(Exception e){
		e.printStackTrace();
		}
		}
	@Override
	protected void onPause() {
		Log.d(TAG,"screen rotated");
		super.onPause();
	}
		
	
	}
 

   

