package com.sorenson.tasktimer.model;

public class Task {
	private int id;
    private String name;
    private int goalTimeSeconds;
    private boolean reduce;

    public Task() {}

    public Task(String name, int goalTimeSeconds, boolean reduce) {
        super();
        this.name = name;
        this.goalTimeSeconds = goalTimeSeconds;
        this.reduce = reduce;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGoalTimeSeconds() {
		return goalTimeSeconds;
	}

	public void setGoalTimeSeconds(int goalTimeSeconds) {
		this.goalTimeSeconds = goalTimeSeconds;
	}

	public boolean isReduce() {
		return reduce;
	}

	public void setReduce(boolean reduce) {
		this.reduce = reduce;
	}
	
	@Override
	public String toString() {
		return "Task [id = "+String.valueOf(id)+", name = "+name+
				", goalTimeSeconds = "+String.valueOf(goalTimeSeconds)+
				", reduce = "+String.valueOf(reduce)+"]";
	}
}
