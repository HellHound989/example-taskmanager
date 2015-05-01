package com.jg.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jg.example.taskmanager.MinutesTask;
import com.jg.example.taskmanager.SecondsTask;
import com.jg.example.taskmanager.TaskManager;

public class Test {
	
	final static DateFormat format = new SimpleDateFormat("hh:mm:ss aa");
	
	// Example A: This task class utilized the MinutesTask type, in which we will
	// override the runTask() method with our own code. Our runTask() method also
	// will keep track how many times we have called it.
	static class MyTaskA extends MinutesTask {
		
		int count = 1;
		
		public MyTaskA() {
			this.setMinuteDelay(2);
			System.out.println("MyTaskA has been instantialized and set to run every 2 minutes");
		}
		
		@Override
		public void runTask() {
			System.out.println("MyTaskA.runTask() has been called! - Time is [" + format.format(Calendar.getInstance().getTime())
					+ "] - Has now been run [" + count++ + "] times");
		}
	}
	
	// Example B: This task class utilized the SecondsTask type, in which we will
	// override the runTask() method with our own code. Our runTask() method also 
	// will keep track how many times we have called it.
	static class MyTaskB extends SecondsTask{
		
		int count = 1;
		
		public MyTaskB() {
			this.setSecondsDelay(45);
			System.out.println("MyTaskB has been instantialized and set to run every 45 seconds");
		}
		
		@Override
		public void runTask() {
			System.out.println("MyTaskB.runTask() has been called! - Time is [" + format.format(Calendar.getInstance().getTime())
					+ "] - Has now been run [" + count++ + "] times");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
		// Instantiate our test tasks
		MyTaskA taskA = new MyTaskA();
		MyTaskB taskB = new MyTaskB();
		
		// Create a new Task Manager
		TaskManager tm = new TaskManager();
		
		// Now schedule our tasks to the Task Manager
		tm.scheduleTask(taskA);
		tm.scheduleTask(taskB);
		
		// Start the Task Manager
		tm.startTasks();
		
		// Lets print out the current time so we can use it to compare the task times
		System.out.println("Current start time: [" + format.format(Calendar.getInstance().getTime()) + "]");
		
		// We dont want our main program to end immediately, so lets go ahead and sleep for approximately 11 minutes
		// to allow our tasks to run
		Thread.sleep(11*(60*1000));
		
		// Lets now stop the Task Manager
		tm.stopTasks();
		
		System.out.println("Application has ended!");
	}

}
