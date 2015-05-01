package com.jg.example.taskmanager;

public class TaskManager {
	private TaskQueue taskQueue;
	private TaskManagerThread timerThread;
	
	public TaskManager() {
		this.taskQueue = new TaskQueue();
		this.timerThread = new TaskManagerThread(this.taskQueue);
	}
	
	public void scheduleTask(AbstractBaseTask task) {
		synchronized(taskQueue) {
			task.resetTime();
			task.setState(AbstractBaseTask.SCHEDULED);
			taskQueue.add(task);
			taskQueue.notify();
		}
	}
	
	public void unScheduleTask(AbstractBaseTask task) {
		taskQueue.remove(task);
	}
	
	public void startTasks() {
		this.timerThread.setDaemon(true);
		this.timerThread.setName("WNTimerThread");
		this.timerThread.start();
	}
	
	public void stopTasks() {
		this.timerThread.stopManagerThread();
	}
}
