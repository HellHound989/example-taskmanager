package com.jg.example.taskmanager;

import java.util.Calendar;

public abstract class HoursTask extends AbstractBaseTask {
	
	private int delayTime = 1;
	private int startMinute;
	
	protected HoursTask() {
		super(TYPE_HOURLY);
		this.startMinute = -1;
	}
	
	protected HoursTask(int delay) {
		super(TYPE_HOURLY);
		setHourlyDelay(delay);
		this.startMinute = -1;
	}
	
	public abstract void runTask();
	
	public void setHourlyDelay(int delay) {
		this.delayTime = delay;
		if (delay < 1) this.delayTime = 1;
		if (delay > 23) this.delayTime = 23;
	}

	@Override
	boolean checkTask() {
		if (this.startMinute == -1) {
			this.startMinute = super.getStartTime().get(Calendar.MINUTE);
		}
		
		int startHour = super.getStartTime().get(Calendar.HOUR_OF_DAY);
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
		
		if (super.getState() == SCHEDULED) {
			// If the current hour has wrapped around, add 24 to it so the subtraction will work
			// example: start time is 23:58, and the current time is 00:58, add 24 to the 00 to make 24
			// now 24 - 23 = 1 hour has passed
			if (currentHour < startHour) currentHour += 24;
			
			// This is different than the MinutesTask. If the hour has passed, and it falls
			// within the same minute since last hour, run the task. Example: 12:34 start, 13:22 now
			// and the task is set to run every 1 hour. Taking just the hour calculation, 13 - 12 = 1
			// it would appear that an hour has passed. But 34 does not equal 22, hence not a full hour has passed
			if (((currentHour - startHour) >= this.delayTime) && (currentMinute == this.startMinute)) {
				super.setState(READY_EXECUTE);
				return true;
			}
		}
		
		if (super.getState() == EXECUTED) {
			if (currentMinute != this.startMinute) {
				super.setState(SCHEDULED);
				return true;
			}
		}
		return false;
	}

}
