package com.example.glossaries;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;


public class SuggestionProvider extends ContentProvider {
	

	
	public static String TAG = "SuggestionProvider";

	    public static String AUTHORITY = "com.example.glossaries.SuggestionProvider";
	    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Java");

	   
	    private DatabaseHelper mDataBase=new DatabaseHelper(getContext());

	    // UriMatcher stuff
	    private static final int SEARCH_WORDS = 0;
	    private static final int GET_WORD = 1;
	    private static final int SEARCH_SUGGEST = 2;
	    private static final int REFRESH_SHORTCUT = 3;
	    private static final UriMatcher sURIMatcher = buildUriMatcher();

	    private static UriMatcher buildUriMatcher() {
	        UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
	        // to get definitions...
	        matcher.addURI(AUTHORITY, "Java", SEARCH_WORDS);
	        matcher.addURI(AUTHORITY, "Java/#", GET_WORD);
	        
	        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
	        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);

	        
	        Log.w(TAG,matcher.toString());
	        return matcher;
	    }

	    @Override
	    public boolean onCreate() {
	    	mDataBase = new DatabaseHelper(getContext());
	    	
	        return true;
	    }
    
	    

	    /**
	     * Handles all the dictionary searches and suggestion queries from the Search Manager.
	     * When requesting a specific word, the uri alone is required.
	     * When searching all of the dictionary for matches, the selectionArgs argument must carry
	     * the search query as the first element.
	     * All other arguments are ignored.
	     */
	    @Override
	    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
	                        String sortOrder) {

	        // Use the UriMatcher to see what kind of query we have and format the db query accordingly
	    	Log.w(TAG,"uri :"+uri.toString());
	    	
	    	switch (sURIMatcher.match(uri)) {
	            case SEARCH_SUGGEST:
	                if (selectionArgs == null) {
	                  throw new IllegalArgumentException(
	                      "selectionArgs must be provided for the Uri: " + uri);
	                }
	                Log.w(TAG,"case1");
	                return getSuggestions(selectionArgs[0]);
	            case SEARCH_WORDS:
	                if (selectionArgs == null) {
	                  throw new IllegalArgumentException(
	                      "selectionArgs must be provided for the Uri: " + uri);
	                }
	                Log.w(TAG,"case2"+selectionArgs[0]);
	                return search(selectionArgs[0]);
	           		
	            default:
	                throw new IllegalArgumentException("Unknown Uri: " + uri);
	                
	        }
	    }
	  
	    private Cursor getSuggestions(String query) {
	      query = query.toLowerCase();
	      String[] columns = new String[] {
	          BaseColumns._ID,
	          DatabaseHelper.KEY_WORD,
	          DatabaseHelper.KEY_DESC};
	       

	      return mDataBase.getWordMatches(query, columns);
	    }

	    private Cursor search(String query) {
	    mDataBase.openDataBase();
	      query = query.toLowerCase();
	      Log.w(TAG, "tolowercase"+ query);
	      String[] columns = new String[] {
	          BaseColumns._ID,
	          DatabaseHelper.KEY_WORD,
	          DatabaseHelper.KEY_DESC};
	      Log.w(TAG, "after column"+ columns[0]);
	      mDataBase.close();
	      return mDataBase.getWordMatches(query, columns);
	     
	    }

	    public Cursor getWord(Uri uri, String rowId, String[] columns) {
	    	Log.w(TAG, "rowid selected"+ rowId);
	        String selection = "rowid = ?";
	        String[] selectionArgs = new String[] {rowId};
	        
	        return query(uri,columns,selection, selectionArgs,null);

	    }
	   	   
	    @Override
	    public String getType(Uri uri) {
	        switch (sURIMatcher.match(uri)) {
	            case SEARCH_SUGGEST:
	                return SearchManager.SUGGEST_MIME_TYPE;
	            case REFRESH_SHORTCUT:
	                return SearchManager.SHORTCUT_MIME_TYPE;
	            default:
	                throw new IllegalArgumentException("Unknown URL " + uri);
	        }
	    }

	    
	    @Override
	    public Uri insert(Uri uri, ContentValues values) {
	        throw new UnsupportedOperationException();
	    }

	    @Override
	    public int delete(Uri uri, String selection, String[] selectionArgs) {
	        throw new UnsupportedOperationException();
	    }

	    @Override
	    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
	        throw new UnsupportedOperationException();
	    }

	}


