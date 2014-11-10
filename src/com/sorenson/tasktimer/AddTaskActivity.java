package com.sorenson.tasktimer;

import com.sorenson.tasktimer.model.Task;
import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddTaskActivity extends ActionBarActivity {
	EditText taskNameEditText;
	NumberPicker minuteNumberPicker;
	NumberPicker secondsNumberPicker;
	Button addTaskButton;
	Button cancelButton;
	ToggleButton goalToReduceToggleButton;
	SQLiteHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		taskNameEditText = (EditText)findViewById(R.id.TaskNameEditText);
		minuteNumberPicker = (NumberPicker)findViewById(R.id.minuteNumberPicker);
		secondsNumberPicker = (NumberPicker)findViewById(R.id.secondsNumberPicker);
		addTaskButton = (Button)findViewById(R.id.addTaskButton);
		cancelButton = (Button)findViewById(R.id.cancelButton);
		goalToReduceToggleButton = (ToggleButton)findViewById(R.id.goalToReduceToggleButton);
		db = new SQLiteHelper(this);
		
		addTaskButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (taskNameEditText.getText().toString().length()==0) {
					Toast.makeText(getApplicationContext(), "Task Name must be filled in", Toast.LENGTH_LONG).show();
				} else {
					Task newTask = new Task();
					String taskName = taskNameEditText.getText().toString();
					
					newTask.setName(taskName);
					newTask.setReduce(goalToReduceToggleButton.isChecked());
					newTask.setGoalTimeSeconds((minuteNumberPicker.getValue()*60) +
											   (secondsNumberPicker.getValue()));
					db.addTask(newTask);
					Toast.makeText(getApplicationContext(), taskName + " Created.", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
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
