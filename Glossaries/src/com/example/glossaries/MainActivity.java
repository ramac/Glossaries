package com.example.glossaries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
public class MainActivity extends Activity {
	Intent intent;
	private int timer = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		new Thread()
	    {
	      public void run()
	      {
	        int i = 0;
	        try
	        {
	          while (true)
	          {
	            int j = MainActivity.this.timer;
	            if (i >= j)
	              return;
	            sleep(300);
	            i += 300;
	          }
	        }
	        catch (InterruptedException localInterruptedException)
	        {
	          while (true)
	        	  
	          {
	        	intent = new Intent(MainActivity.this,SearchScreen.class);
	        	  MainActivity.this.startActivity(intent );
	        	  MainActivity.this.finish();
	          }
	        }
	        finally
	        {
	        	intent = new Intent(MainActivity.this,SearchScreen.class);
	        	
	        	MainActivity.this.finish();
	        	startActivity(intent);
	        }
	      }
	    }
	    .start();
	  }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
