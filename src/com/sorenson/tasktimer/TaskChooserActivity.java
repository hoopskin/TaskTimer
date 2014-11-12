package com.sorenson.tasktimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sorenson.tasktimer.model.Task;
import com.sorenson.tasktimer.sqlite.SQLiteHelper;

import android.support.v7.app.ActionBarActivity;
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

public class TaskChooserActivity extends ListActivity {

	String TAG = "com.sorenson.tasktimer";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "1");
		setContentView(R.layout.activity_task_chooser);
		Log.d(TAG, "2");
		//ListView taskListView = (ListView)findViewById(R.id.taskListView);
		ListView taskListView = getListView();
		Log.d(TAG, "3");
		SQLiteHelper db = new SQLiteHelper(this);
		Log.d(TAG, "4");
		final Intent intent = new Intent();
		
		final List<Task> tasks = db.getAllTaskEntries();
		Log.d(TAG, "5");
		final ArrayList<String> taskNames = new ArrayList<String>();
		Log.d(TAG, "6");
		for(Task task : tasks) {
			taskNames.add(task.getName());
			Log.d(TAG, task.getName());
		}
		Log.d(TAG, "7");
		StableArrayAdapter adapter = new StableArrayAdapter(this, R.id.list, taskNames);
		Log.d(TAG, "8");
		taskListView.setAdapter(adapter);
		Log.d(TAG, "9");
		taskListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "A");
				intent.putExtra("taskName", tasks.get(position).getName());
				Log.d(TAG, "B");
				intent.putExtra("taskId", tasks.get(position).getId());
				Log.d(TAG, "C");
				setResult(2, intent);
				Log.d(TAG, "D");
				finish();
				
			}
		});
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_chooser, menu);
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
	}*/
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
}
