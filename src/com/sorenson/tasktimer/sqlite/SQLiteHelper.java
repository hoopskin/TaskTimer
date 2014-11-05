package com.sorenson.tasktimer.sqlite;

import java.util.List;

import com.sorenson.tasktimer.model.Task;
import com.sorenson.tasktimer.model.Time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static final String TAG = "com.sorenson.tasktimer";

	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TaskTimerDB";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create tasks table
		String CREATE_TASKS_TABLE = "CREATE TABLE tasks ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT, "+
				"goalTimeSeconds INTEGER, "+
				"reduce INTEGER)";

		// create tasks table
		db.execSQL(CREATE_TASKS_TABLE);

		// SQL statement to create timeEntries table
		String CREATE_TIME_TABLE = "CREATE TABLE timeEntries ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"taskId INTEGER, " +
				"date TEXT" +
				"startTime TEXT" +
				"endTime TEXT," +
				"FOREIGN KEY (taskId) REFERENCES tasks(id))";

		// create timeEntries table
		db.execSQL(CREATE_TIME_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older timeEntries and tasks tables if existed
        db.execSQL("DROP TABLE IF EXISTS timeEntries");
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // create fresh task and timeEntries tables
        this.onCreate(db);
	}

	//---------------------------------------------------------------------

	/**
     * CRUD operations (create "add", read "get", update, delete) task
     */

	// Tasks table name
    private static final String TABLE_TASKS = "tasks";

    // Tasks Table Columns names
    private static final String TASKS_KEY_ID = "id";
    private static final String TASKS_KEY_NAME = "name";
    private static final String TASKS_KEY_GOAL_TIME_SECONDS = "goalTimeSeconds";
    private static final String TASKS_KEY_REDUCE = "reduce";

    private static final String[] TASKS_COLUMNS = {TASKS_KEY_ID, TASKS_KEY_NAME, TASKS_KEY_GOAL_TIME_SECONDS, TASKS_KEY_REDUCE};

    public void addTask(Task task) {
    	Log.d(TAG, "Adding task: " + task.toString());

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
		values.put(TASKS_KEY_ID, task.getId());
		values.put(TASKS_KEY_NAME, task.getName());
		values.put(TASKS_KEY_GOAL_TIME_SECONDS, task.getGoalTimeSeconds());

		if(task.isReduce()) {
			values.put(TASKS_KEY_REDUCE, 1);
		} else {
			values.put(TASKS_KEY_REDUCE, 0);
		}

        // 3. insert
        db.insert(TABLE_TASKS, // table
        		null, //nullColumnHack
        		values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

	public Task getTask(String name) {
		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
        Cursor cursor =
        		db.query(TABLE_TASKS, // a. table
        		TASKS_COLUMNS, // b. column names
        		" name = ?", // c. selections
                new String[] { name }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
			cursor.moveToFirst();
		}

		Task task = new Task();
		task.setId(Integer.parseInt(cursor.getString(0)));
		task.setName(cursor.getString(1));
		task.setGoalTimeSeconds(Integer.parseInt(cursor.getString(2)));
		if (Integer.parseInt(cursor.getString(3)) == 1) {
			task.setReduce(true);
		} else {
			task.setReduce(false);
		}

		Log.d(TAG, "getTask("+name+") = " + task.toString());

		return task;
	}

	public List<Time> getAllTimeEntries(String name) {
		return null;
	}
}
