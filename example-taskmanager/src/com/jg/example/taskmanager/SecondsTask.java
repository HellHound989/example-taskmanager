package com.jg.example.taskmanager;

import java.util.Calendar;

public abstract class SecondsTask extends AbstractBaseTask {

	private int delayTime = 30;
	
	protected SecondsTask() {
		super(TYPE_SECOND);
	}
	
	protected SecondsTask(int delay) {
		super(TYPE_SECOND);
		setSecondsDelay(delay);
	}
	
	public abstract void runTask();
	
	public void setSecondsDelay(int delay) {
		this.delayTime = delay;
		if (delay < 5) this.delayTime = 5;
		if (delay > 59) this.delayTime = 59;
	}

	@Override
	boolean checkTask() {
		
		int startSecond = super.getStartTime().get(Calendar.SECOND);
		int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
		
		if (super.getState() == SCHEDULED) {
			if (currentSecond < startSecond) currentSecond += 60;
			
			if ((currentSecond - startSecond) >= this.delayTime) {
				super.setState(READY_EXECUTE);
				return true;
			}
		}
		
		if (super.getState() == EXECUTED) {
			if (currentSecond != startSecond) {
				super.setState(SCHEDULED);
				return true;
			}
		}
		
		return false;
	}

}
