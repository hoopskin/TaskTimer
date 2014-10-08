package com.sorenson.tasktimer;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	public final String sharedPrefFileName = "com.sorenson.taskTimer.PREFERENCE_FILE_KEY";
	public Button saveButton;
	public Button loadButton;
	public TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		saveButton = (Button)findViewById(R.id.saveButton);
		loadButton = (Button)findViewById(R.id.loadButton);
		textView = (TextView)findViewById(R.id.textView1);
		final SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences(sharedPrefFileName, 0);
		final SharedPreferences.Editor editor = sharedPrefs.edit();
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor.putString("myString", "Yay! I can save!");
				editor.commit();
			}
		});
		
		loadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String defValue = sharedPrefs.getString("myString", "N/A");
				textView.setText(defValue);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
