package com.sorenson.tasktimer.sqlite;

import com.sorenson.tasktimer.model.Task;

import android.content.ContentValues;
import android.content.Context;
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

		// SQL statement to create times table
		String CREATE_TIME_TABLE = "CREATE TABLE times ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"taskId INTEGER, " +
				"date TEXT" +
				"startTime TEXT" +
				"endTime TEXT," +
				"FOREIGN KEY (taskId) REFERENCES tasks(id))";

		// create times table
		db.execSQL(CREATE_TIME_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older times and tasks tables if existed
        db.execSQL("DROP TABLE IF EXISTS times");
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // create fresh task and times tables
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
}
