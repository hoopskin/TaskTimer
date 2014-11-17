package com.sorenson.tasktimer.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Time {
	private int id;
	private int taskId;
	private Date entryDate;
	private int seconds;
	SimpleDateFormat entryDateFormat = new SimpleDateFormat("MMddyyyy");

	public Time() {}

	public Time(int taskId, Date entryDate, int seconds) {
		this.taskId = taskId;
		this.entryDate = entryDate;
		this.seconds = seconds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return "Time [id="+String.valueOf(id)+", taskId = "+String.valueOf(taskId)+
				", date="+String.valueOf(entryDate)+
				", seconds = " + String.valueOf(seconds)+"]";
	}
}
