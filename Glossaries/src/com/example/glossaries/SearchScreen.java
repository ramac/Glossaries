package com.example.glossaries;

import java.io.BufferedReader;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchScreen extends Activity{
	DatabaseHelper dbHelper;
	SQLiteDatabase myDB;
	 private static final String TAG = "GLOSSARY_SEARCH_SCREEN";                      
Button button;
SearchView textsearch;
TextView showtext;
String searchtext;
BufferedReader br;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchscreen);
		
		
		textsearch = (SearchView)findViewById(R.id.searchview);
		
		showtext = (TextView)findViewById(R.id.textView1);
		
		
		button =(Button )findViewById(R.id.button1);		
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SearchScreen.this, MainActivity.class);
				startActivity(intent);
			
				
			}
		});
		try{
		dbHelper=new DatabaseHelper(this);
		dbHelper.createDataBase();
		Log.w(TAG,dbHelper.getDatabaseName());
		myDB=dbHelper.getReadableDatabase();
		Log.w(TAG,myDB.getPath());
		}
		catch(Exception ex){
			Log.w(TAG,"");
		}
		
		Cursor mycursor = searchWord("abstract");
		
		CharSequence desc= mycursor.getString(0);
		showtext.append(desc);

	}
	
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


public Cursor searchWord(String inputText) throws SQLException {
    Log.w(TAG, "searchtext"+inputText);
    String query = "SELECT desc FROM java where word ='"+inputText+"'";
    Log.w(TAG, query);
    
    Cursor mCursor = myDB.rawQuery(query,null);
    


    if (mCursor != null) {
        mCursor.moveToFirst();
    }
    return mCursor;

}

	
	
}
