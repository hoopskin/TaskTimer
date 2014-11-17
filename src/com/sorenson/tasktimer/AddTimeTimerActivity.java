package com.sorenson.tasktimer;

import java.util.Date;

import com.sorenson.tasktimer.model.Time;
import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTimeTimerActivity extends ActionBarActivity {
	TextView taskNameText;
	TextView elapsedTime;
	boolean stopPressed = false;
	int hour = 0;
	int min = 0;
	int sec = 0;
	String mTimeFormat = "%02d:%02d:%02d";
	Handler mHandler = new Handler();
	Runnable mUpdateTime = new Runnable() {
		public void run() { updateTimeView(); }
	};
	Date date;
	Button startButton;
	Button stopButton;
	Button submitButton;
	int taskId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_time_timer);
		elapsedTime = (TextView)findViewById(R.id.elapsedTime);
		startButton = (Button)findViewById(R.id.startButton);
		stopButton = (Button)findViewById(R.id.stopButton);
		submitButton = (Button)findViewById(R.id.submitButton);
		final SQLiteHelper db = new SQLiteHelper(this);
		
		startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.postDelayed(mUpdateTime, 1000);
				stopPressed = false;
			}
		});
		
		stopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopPressed = true;
			}
		});
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Time timeEntry = new Time();
				timeEntry.setTaskId(taskId);
				timeEntry.setEntryDate(new Date());
				//Minus 1 used it claims 5.01 seconds = 6 seconds even though screen says 5 seconds
				timeEntry.setSeconds((hour*3600)+(min*60)+(sec-1));
				db.addTime(timeEntry);
				Toast.makeText(getApplicationContext(), "Submitting a time of " + String.valueOf(sec-1) + " seconds to task with id = " + String.valueOf(taskId), Toast.LENGTH_SHORT).show();
			}
		});
		
		Intent intent = new Intent(AddTimeTimerActivity.this, TaskChooserActivity.class);
		startActivityForResult(intent, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 2) {
			String taskName = data.getStringExtra("taskName");
			taskId = data.getIntExtra("taskId", -1);
			taskNameText = (TextView)findViewById(R.id.taskNameText);
			taskNameText.setText(taskName);
		}
		
	}
	
	public void updateTimeView() {
		sec += 1;
		if (sec >= 60) {
			sec = 0;
			min += 1;
			if (min >= 60) {
				min = 0;
				hour += 1;
			}
		}
		if (!stopPressed) {
			elapsedTime.setText(String.format(mTimeFormat, hour, min, sec));
			mHandler.postDelayed(mUpdateTime, 1000);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_time_timer, menu);
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
