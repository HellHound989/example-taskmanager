package com.jg.example.taskmanager;

import java.util.Calendar;

public abstract class DaysTask extends AbstractBaseTask {
	private int delayTime = 1;
	private int startHour = 12;
	
	protected DaysTask() {
		super(TYPE_DAILY);
	}
	
	protected DaysTask(int delay, int hour) {
		super(TYPE_DAILY);
		setDailyDelay(delay);
		this.startHour = hour;
	}
	
	public abstract void runTask();
	
	public void setDailyDelay(int delay) {
		this.delayTime = delay;
		if (delay < 1) this.delayTime = 1;
		if (delay > 7) this.delayTime = 7;
	}
	
	//Unlike the other task types, a task that is set to run every X days can be set to run at a specific
	// hour of the day. For example, say you need a task to run every 2 days, but want it to run at 2:00am
	// in this case, you can set the time for it to run, even if you started the task manager at 1:43pm on
	// day 0.
	public void setTimeOfDayStart(int hour) {
		this.startHour = hour;
		if (hour < 0) this.startHour = 0;
		if (hour > 23) this.startHour = 23;
	}

	@Override
	boolean checkTask() {
		int startDay = super.getStartTime().get(Calendar.DAY_OF_WEEK);
		int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		if (super.getState() == SCHEDULED) {
			if (currentDay <= startDay) currentDay += 7;
			
			if (((currentDay - startDay) >= this.delayTime) && (currentHour == this.startHour)) {
				super.setState(READY_EXECUTE);
				return true;
			}
		}
		
		if (super.getState() == EXECUTED) {
			if (currentHour != this.startHour) {
				super.setState(SCHEDULED);
				return true;
			}
		}
		return false;
	}
}
