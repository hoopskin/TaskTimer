package com.sorenson.tasktimer.sqlite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
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

    SimpleDateFormat entryDateFormat = new SimpleDateFormat("MMddyyyy");
	SimpleDateFormat entryTimeFormat = new SimpleDateFormat("hhmmss");

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
				"date TEXT," +				//MMDDYYYY
				"seconds INTEGER," +
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

    private static final String[] TASKS_COLUMNS = {TASKS_KEY_NAME, TASKS_KEY_GOAL_TIME_SECONDS, TASKS_KEY_REDUCE};
    private static final String[] TASKS_COLUMNS_WITH_ID = {TASKS_KEY_ID, TASKS_KEY_NAME, TASKS_KEY_GOAL_TIME_SECONDS, TASKS_KEY_REDUCE};
    
	//Time Entries table name
	private static final String TABLE_TIME_ENTRIES = "timeEntries";

	//Time Entries Table Columns names
	private static final String TIME_KEY_ID = "id";
	private static final String TIME_KEY_TASK_ID = "taskId";
	private static final String TIME_KEY_DATE = "date";
	private static final String TIME_KEY_SECONDS = "seconds";

	private static final String[] TIME_COLUMNS = {TIME_KEY_TASK_ID, TIME_KEY_DATE, TIME_KEY_SECONDS};
	private static final String[] TIME_COLUMNS_WITH_ID = {TIME_KEY_ID, TIME_KEY_TASK_ID, TIME_KEY_DATE, TIME_KEY_SECONDS};
	
	public void addTime(Time time) {
		Log.d(TAG, "Adding time: " + time.toString());

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TIME_KEY_TASK_ID, time.getTaskId());
		values.put(TIME_KEY_SECONDS, time.getSeconds());
		values.put(TIME_KEY_DATE, entryDateFormat.format(time.getEntryDate()));

		db.insert(TABLE_TIME_ENTRIES,
				null,
				values);

		db.close();
	}

    public void addTask(Task task) {
    	Log.d(TAG, "Adding task: " + task.toString());

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
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
		Log.d(TAG, "Getting task with name " + name);
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
        Cursor cursor =
        		db.query(TABLE_TASKS, // a. table
        		TASKS_COLUMNS_WITH_ID, // b. column names
        		" name = ?", // c. selections
                new String[] { name }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
        	Log.d(TAG, "not null");
			cursor.moveToFirst();
		} else {Log.d(TAG, "null");}
        Log.d(TAG, "Count="+String.valueOf(cursor.getColumnCount()));
        Log.d(TAG, cursor.getString(0));
        Log.d(TAG, cursor.getString(1));
        Log.d(TAG, cursor.getString(2));
        Log.d(TAG, cursor.getString(3));

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

	public List<Task> searchTasks(String query) {
		List<Task> tasks = new LinkedList<Task>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Task task = null;

        if (cursor.moveToFirst()) {
        	do {
        		task = new Task();
        		task.setId(Integer.parseInt(cursor.getString(0)));
        		task.setName(cursor.getString(1));
        		task.setGoalTimeSeconds(Integer.parseInt(cursor.getString(2)));
        		if (Integer.parseInt(cursor.getString(3)) == 1) {
        			task.setReduce(true);
        		} else {
        			task.setReduce(false);
        		}

        		tasks.add(task);
        	} while (cursor.moveToNext()) ;
        }

        Log.d(TAG, "Query \"" + query + "\" return the following results: " + tasks.toString());

		return tasks;
	}

	public List<Task> getAllTaskEntries() {
		String query = "SELECT * FROM " + TABLE_TASKS;
		return searchTasks(query);
	}

	public List<Time> getAllTimeEntries(String name) throws ParseException {
		Log.d(TAG, "Getting all time entries for " + name);
		
		List<Time> timeEntries = new LinkedList<Time>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		int taskId = getTask(name).getId();
		Log.d(TAG, "About to get time entries for task with id = " + String.valueOf(taskId));
		
		Cursor cursor =
				db.query(TABLE_TIME_ENTRIES,
				TIME_COLUMNS,
				" taskId = ?",
				new String[] {String.valueOf(taskId)},
				null,
				null,
				null,
				null);


		Time time = null;
		if (cursor.moveToFirst()) {
			do {
				time = new Time();
				//time.setId(Integer.parseInt(cursor.getString(0)));
				//time.setTaskId(Integer.parseInt(cursor.getString(1)));
				//time.setEntryDate(entryDateFormat.parse(cursor.getString(2)));
				//time.setSeconds(Integer.parseInt(cursor.getString(3)));
				time.setTaskId(Integer.parseInt(cursor.getString(0)));
				time.setEntryDate(entryDateFormat.parse(cursor.getString(1)));
				time.setSeconds(Integer.parseInt(cursor.getString(2)));

				timeEntries.add(time);
			} while (cursor.moveToNext());
		}

		Log.d(TAG, "Time entries for " + name + " are followed: " + timeEntries.toString());

		return timeEntries;
	}
}
