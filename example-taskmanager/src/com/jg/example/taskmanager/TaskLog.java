package com.jg.example.taskmanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskLog {
	
	static ClassLoader cLoader = null;
	static Class<?> cLogger;
	static Object oLogger;
	private static boolean enabled = false;
	
	public static void log(String func, String text) throws NoSuchMethodException, SecurityException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (enabled) {
			Method method = cLogger.getMethod(func, String.class);
			method.invoke(oLogger, new Object[] { text });
		}
	}
	
	public static void enableLogging(String loggerClass, String logger) throws ClassNotFoundException, 
				InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, 
				IllegalArgumentException, InvocationTargetException {
		if (cLoader == null) {
			cLoader = TaskLog.class.getClassLoader();
			cLogger = cLoader.loadClass(loggerClass);
			Method method = cLogger.getMethod("getLogger", String.class);
			oLogger = method.invoke(null, new Object[] { logger });
		}
		enabled = true;
	}
	
	public static void disableLogging() {
		enabled = false;
	}
}
