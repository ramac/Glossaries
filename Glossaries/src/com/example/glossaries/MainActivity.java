package com.example.glossaries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button button;
	Spinner spinner;	
	String text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button)findViewById(R.id.button1);
		final Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				text = String.valueOf(spinner.getSelectedItem());
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "You selected " + text , Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
		
		
		
	button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, SearchScreen.class);
	        startActivity(intent);
		}
	});	
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
