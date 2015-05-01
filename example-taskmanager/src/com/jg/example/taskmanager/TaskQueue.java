package com.jg.example.taskmanager;

import java.util.Arrays;

public class TaskQueue {
	private static AbstractBaseTask[] queue = new AbstractBaseTask[4];
	private static int size = 0;
	
	int size() {
		return size;
	}
	
	void add(AbstractBaseTask task) {
		if (size + 1 == queue.length)
			queue = Arrays.copyOf(queue, 2*queue.length);
		queue[size++] = task;
	}
	
	AbstractBaseTask get(int i) {
		return queue[i];
	}
	
	void remove(AbstractBaseTask task) {
		for (int i=0; i<size; i++) {
			if (queue[i] == task) {
				removeAt(i);
				break;
			}
		}
	}
	
	void removeAt(int i) {
		assert i<=size;
		
		queue[i] = queue[size];
		queue[size--] = null;
	}
	
	boolean isEmpty() {
		return size==0;
	}
	
	void clear() {
		for (int i=0; i<size; i++) {
			queue[i] = null;
		}
		size = 0;
	}
}
