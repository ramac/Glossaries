
package com.example.glossaries;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class WordActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);
        Uri uri = getIntent().getData();
        String query=getIntent().getStringExtra("query");
        Cursor cursor = new SuggestionProvider().query(uri, null, null, new String[]{query}, null);

        if (cursor == null) {
            finish();
        } else {
            cursor.moveToFirst();

            TextView word = (TextView) findViewById(R.id.word);
            Typeface face1= Typeface.createFromAsset(getAssets(),"fonts/CalendarNova-Regular.ttf");
            word.setTypeface(face1);
            TextView definition = (TextView) findViewById(R.id.definition);

            int wIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_WORD);
            int dIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_DESC);

            word.setText(cursor.getString(wIndex));
            definition.setText(cursor.getString(dIndex));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
                return false;
       }
    }

