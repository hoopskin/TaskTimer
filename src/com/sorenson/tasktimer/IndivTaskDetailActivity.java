package com.sorenson.tasktimer;

import java.text.ParseException;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.sorenson.tasktimer.model.GraphViewData;
import com.sorenson.tasktimer.model.Time;
import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class IndivTaskDetailActivity extends ActionBarActivity {
	int taskId;
	String taskName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indiv_task_detail);
		Intent intent = new Intent(IndivTaskDetailActivity.this, TaskChooserActivity.class);
		startActivityForResult(intent, 2);


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 2) {
			taskName = data.getStringExtra("taskName");
			taskId = data.getIntExtra("taskId", -1);

		}
		
		final SQLiteHelper db = new SQLiteHelper(this);
		try {
			List<Time> timeEntries = db.getAllTimeEntries(taskName);
			int goalTimeSeconds = db.getTask(taskName).getGoalTimeSeconds();
			GraphViewData[] graphData = new GraphViewData[timeEntries.size()];
			GraphViewData[] goalLineData = new GraphViewData[timeEntries.size()];
			for (Time time : timeEntries) {
				int idx = timeEntries.indexOf(time);
				graphData[idx] = new GraphViewData(idx, timeEntries.get(idx).getSeconds());
				goalLineData[idx] = new GraphViewData(idx, goalTimeSeconds);
			}
			GraphView graphView = new LineGraphView(this, taskName);
			graphView.addSeries(new GraphViewSeries(graphData));
			graphView.addSeries(new GraphViewSeries(goalLineData));
			LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
			layout.addView(graphView);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.indiv_task_detail, menu);
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
