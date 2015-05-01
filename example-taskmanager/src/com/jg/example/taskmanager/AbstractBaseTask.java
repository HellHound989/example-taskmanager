package com.jg.example.taskmanager;

import java.util.Calendar;

public abstract class AbstractBaseTask {
	
	/* state of this task */
	static final int VIRGIN = 0;		// No datetime has been set for this task
	static final int SCHEDULED = 1;		// This task now has been scheduled
	static final int READY_EXECUTE = 2;	// This task is ready to begin executing
	static final int EXECUTING = 3;		// This task is executing at the moment
	static final int EXECUTED = 4;		// This task has been executed
	
	/* type of child inherited classes */
	static final int TYPE_SECOND = 10;
	static final int TYPE_MINUTE = 11;	
	static final int TYPE_HOURLY = 12;
	static final int TYPE_DAILY = 13;
	
	private int state;
	private int taskType;
	
	private Calendar startTime;
	
	AbstractBaseTask(int taskType) {
		this.taskType = taskType;
		this.state = VIRGIN;
	}
	
	public abstract void runTask();
	
	abstract boolean checkTask();
	
	int getType() {
		return this.taskType;
	}
	
	void setType(int type) {
		this.taskType = type;
	}
	
	int getState() {
		return this.state;
	}
	
	void setState(int state) {
		this.state = state;
	}
	
	Calendar getStartTime() {
		return this.startTime;
	}
	
	void resetTime() {
		this.startTime = Calendar.getInstance();
	}
	
	void beginTask() {
		if (getState() == READY_EXECUTE) {
			resetTime();
			setState(EXECUTING);
			new Thread(this.getClass().getName()) {
				public void run() {
					runTask();
					setState(EXECUTED);
				}
			}.start();
		}
	}
}
