package com.sorenson.tasktimer;

import java.util.Calendar;
import java.util.Date;

import com.sorenson.tasktimer.model.Time;
import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AddTimeManualActivity extends ActionBarActivity {
	NumberPicker minuteNumberPicker;
	NumberPicker secondsNumberPicker;
	DatePicker datePicker;
	Button submitButton;
	TextView taskNameText;
	int taskId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_time_manual);
		
		minuteNumberPicker = (NumberPicker)findViewById(R.id.manualMinuteNumberPicker);
		secondsNumberPicker = (NumberPicker)findViewById(R.id.manualSecondsNumberPicker);
		datePicker = (DatePicker)findViewById(R.id.datePicker);
		submitButton = (Button)findViewById(R.id.addTimeEntryButton);
		final SQLiteHelper db = new SQLiteHelper(this);
		
		minuteNumberPicker.setMaxValue(999);
		minuteNumberPicker.setMinValue(0);
		
		secondsNumberPicker.setMaxValue(59);
		secondsNumberPicker.setMinValue(0);
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Time timeEntry = new Time();
				timeEntry.setEntryDate(getDateFromDatePicker());
				timeEntry.setSeconds((minuteNumberPicker.getValue()*60)+(secondsNumberPicker.getValue()));
				timeEntry.setTaskId(taskId);
				db.addTime(timeEntry);
			}
		});
		
		Intent intent = new Intent(AddTimeManualActivity.this, TaskChooserActivity.class);
		startActivityForResult(intent, 2);
	}
	
	public Date getDateFromDatePicker() {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = (datePicker.getYear() - 1900);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		
		return calendar.getTime();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 2) {
			String taskName = data.getStringExtra("taskName");
			taskId = data.getIntExtra("taskId", -1);
			taskNameText = (TextView)findViewById(R.id.addTimeManualTaskNameText);
			taskNameText.setText(taskName);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_time_manual, menu);
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
