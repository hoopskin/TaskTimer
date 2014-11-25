package com.sorenson.tasktimer;

import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button addTaskButton = (Button)findViewById(R.id.goToAddTaskButton);
		Button indivTaskStatsButton = (Button)findViewById(R.id.indivTaskStatsButton);
		Button addTimeTimer = (Button)findViewById(R.id.addTimeTimerButton);
		Button addTimeManual = (Button)findViewById(R.id.addTimeManualButton);
		
		/* //Uncomment this section to clear out the database
		SQLiteHelper db = new SQLiteHelper(this);
		db.onUpgrade(db.getWritableDatabase(), 1, 2);
		*/
		
		addTimeTimer.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AddTimeTimerActivity.class));
			}
		});
		
		addTimeManual.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AddTimeManualActivity.class));
			}
		});
		
		addTaskButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
			}
		});
		
		indivTaskStatsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), IndivTaskDetailActivity.class));
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
