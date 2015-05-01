package com.jg.example.taskmanager;

public class TaskManagerThread extends Thread {
	private TaskQueue taskQueue;
	private static boolean started = true;
	
	TaskManagerThread(TaskQueue queue) {
		this.taskQueue = queue;
	}
	
	void stopManagerThread(){
		started = false;
	}
	
	public void run() {

		try {
			TaskLog.log("info", "TaskManagerThread is starting");
			mainLoop();
			TaskLog.log("warning", "TaskManagerThread has stopped");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			taskQueue.clear();
		}
	}
	
	private void mainLoop() {
		while(started) {
			try {
				synchronized(taskQueue) {
					//Wait until we have started adding tasks to the queue. Once added, don't worry about this anymore
					while (taskQueue.isEmpty())
						taskQueue.wait();
					
					//Task has been added, now to start iterating through them, check on the tasks.
					AbstractBaseTask task = null;
					for (int i = 0; i < taskQueue.size(); i++) {
						task = taskQueue.get(i);
						task.checkTask();
						if (task.getState() == AbstractBaseTask.READY_EXECUTE) {
							task.beginTask();
							TaskLog.log("info", "Task - " + task.getClass().getName() + " - Has started");
						}
					}
				}
				sleep(200);
			} catch (Exception e) {;
			}
		}
	}
}
