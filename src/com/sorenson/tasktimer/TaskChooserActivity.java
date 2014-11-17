package com.sorenson.tasktimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sorenson.tasktimer.model.Task;
import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TaskChooserActivity extends ListActivity {

	String TAG = "com.sorenson.tasktimer";
	List<String> taskNames;
	List<Task> tasks;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_task_chooser);
		
		lv = getListView();
		taskNames = collectTaskNames();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, taskNames);
		
		lv.setAdapter(adapter);
	}
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.putExtra("taskName", tasks.get(position).getName());
		intent.putExtra("taskId", tasks.get(position).getId());
		setResult(2, intent);
		finish();
    }

	private List<String> collectTaskNames() {
		SQLiteHelper db = new SQLiteHelper(this);
		tasks = db.getAllTaskEntries();
		List<String> taskNames = new ArrayList<String>();
		for (Task task : tasks) {
			taskNames.add(task.getName());
		}
		return taskNames;
	}
}
