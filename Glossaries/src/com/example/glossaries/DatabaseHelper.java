package com.example.glossaries;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//import com.example.glossaries.CustomersDbAdapter.DatabaseHelper;

import android.content.Context;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DatabaseHelper extends SQLiteOpenHelper {

private static String DB_PATH = "/data/data/com.example.glossaries/databases/";
private static String DB_NAME ="mysqlitecopy.sqlite";
private SQLiteDatabase sqLiteDatabase;
private static final String TAG = "GLOSSARY_DATABASE_HELPER";
    
private final Context myContext;

public DatabaseHelper(Context context) {
	super(context, DB_NAME, null, 1);
    this.myContext = context;
    Log.w(TAG,"in constructor");
}	

/* Creates a empty database on the system and rewrites it with your own database.
* */
public void createDataBase() throws IOException{
	Log.w(TAG,"in create DB");
	//boolean dbExist = checkDataBase();
	boolean dbExist = false;

	if(dbExist){
		//do nothing - database already exist
		Log.w(TAG,"DB already exists");
		
	}else{
		//By calling this method an empty database will be created into the default system path
        //of your application so we are gonna be able to overwrite that database with our database.
   	this.getReadableDatabase();

   	try {
			copyDataBase();
		} 
   	catch (IOException e) {
		Log.e(TAG, "Received an exception", e); 
   		throw new Error("Error copying database");
   		}
	}

}
/**
 * Check if the database already exist to avoid re-copying the file each time you open the application.
 * @return true if it exists, false if it doesn't
 */
private boolean checkDataBase(){
	Log.w(TAG,"in CheckDataBase");
	SQLiteDatabase checkDB = null;

	try{
		String myPath = DB_PATH + DB_NAME;
		Log.w(TAG,myPath);
		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
		
	}catch(SQLiteException e){

		 Log.e(TAG, "Received an exception", e); 

	}

	if(checkDB != null){

		checkDB.close();

	}

	return checkDB != null ? true : false;
}

/**
 * Copies your database from your local assets-folder to the just created empty database in the
 * system folder, from where it can be accessed and handled.
 * This is done by transfering bytestream.
 * */
private void copyDataBase() throws IOException{
	Log.w(TAG,"in copyDatabase");

	//Open your local db as the input stream
	InputStream myInput = myContext.getAssets().open(DB_NAME);

	// Path to the just created empty db
	String outFileName = DB_PATH + DB_NAME;

	//Open the empty db as the output stream
	OutputStream myOutput = new FileOutputStream(outFileName);

	//transfer bytes from the inputfile to the outputfile
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer))>0){
		myOutput.write(buffer, 0, length);
	}

	//Close the streams
	myOutput.flush();
	myOutput.close();
	myInput.close();

}


public void openDataBase() throws SQLException{
	 
	//Open the database
    String myPath = DB_PATH + DB_NAME;
	sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);

}

@Override
public synchronized void close() {

	    if(sqLiteDatabase != null)
		    sqLiteDatabase.close();

	    super.close();

}

@Override
public void onCreate(SQLiteDatabase db) {
Log.w(TAG,"on db create");
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

}


//Cursor cursor = sqLiteDatabase.query("Java", new String[]{"KEY_ID", "KEY_word", "KEY_desc"}, null, null, null, null, null);













	


}