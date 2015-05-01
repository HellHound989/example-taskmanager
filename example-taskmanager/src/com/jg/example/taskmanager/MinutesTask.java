package com.jg.example.taskmanager;

import java.util.Calendar;

public abstract class MinutesTask extends AbstractBaseTask {
	
	private int delayTime = 5;
	private int startSecond;
	
	protected MinutesTask() {
		super(TYPE_MINUTE);
		this.startSecond = -1;
	}
	
	protected MinutesTask(int delay) {
		super(TYPE_MINUTE);
		setMinuteDelay(delay);
		this.startSecond = -1;
	}
	
	public abstract void runTask();
	
	public void setMinuteDelay(int delay) {
		this.delayTime = delay;
		if (delay < 1) this.delayTime = 1;
		if (delay > 59) this.delayTime = 59;
	}
	
	@Override
	boolean checkTask() {
		if (this.startSecond == -1) {
			this.startSecond = super.getStartTime().get(Calendar.SECOND);
		}
		
		int startMinute = super.getStartTime().get(Calendar.MINUTE);
		int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
		int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
		
		if (super.getState() == SCHEDULED) {
			// If the current minute has wrapped around, add 60 to it so the subtraction will work
			// example: start time is 12:58pm, and the current time is 1:02pm, add 60 to the 2 to make 62
			// now 62 - 58 = 4 minutes have passed
			if (currentMinute < startMinute) currentMinute += 60;
			
			if (((currentMinute - startMinute) >= this.delayTime) && (currentSecond == this.startSecond)) {
				super.setState(READY_EXECUTE);
				return true;
			}
		}
		
		if (super.getState() == EXECUTED) {
			if (currentMinute != startMinute) {
				super.setState(SCHEDULED);
				return true;
			}
		}
		
		return false;
	}
}