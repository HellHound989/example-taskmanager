package com.jg.example.taskmanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskLog {
	
	static ClassLoader cLoader = null;
	static Class<?> cLogger;
	private static boolean enabled = false;
	
	public static void log(String func, String text) throws NoSuchMethodException, SecurityException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (enabled) {
			Method method = cLogger.getMethod(func, new Class[] { Object.class });
			method.invoke(cLogger, new Object[] { text });
		}
	}
	
	public static void enableLogging(String loggerClass, String logger) throws ClassNotFoundException, 
				InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, 
				IllegalArgumentException, InvocationTargetException {
		if (cLoader == null) {
			cLoader = TaskLog.class.getClassLoader();
			cLogger = (Class<?>) cLoader.loadClass(loggerClass).newInstance();
			Method method = cLogger.getMethod("getLogger", new Class[] { String.class });
			method.invoke(cLogger, new Object[] { (String) logger });
		}
		enabled = true;
	}
	
	public static void disableLogging() {
		enabled = false;
	}
	
}
