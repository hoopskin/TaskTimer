package com.sorenson.tasktimer.model;

import java.util.Date;

public class Time {
	private int id;
	private int taskId;
	private Date timeDate;
	private Date startTime;
	private Date endTime;

	public Time() {}

	public Time(int taskId, Date timeDate, Date startTime, Date endTime) {
		this.taskId = taskId;
		this.timeDate = timeDate;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public Date getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Date timeDate) {
		this.timeDate = timeDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "Time [id="+String.valueOf(id)+", taskId = "+String.valueOf(taskId)+
				", date="+String.valueOf(timeDate)+
				", startTime = " + String.valueOf(startTime)+
				", endTime="+String.valueOf(endTime)+"]";
	}
}
