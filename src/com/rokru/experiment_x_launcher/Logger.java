package com.rokru.experiment_x_launcher;

public class Logger {

	public static void logPlain(String message){
		System.out.println(message);
	}
	
	public static void logInfo(String message){
		System.out.println("[INFO] " + message);
	}
	
	public static void logError(String message, int priorityLevel){
		String priority;
		if(priorityLevel <= 1) priority = "!";
		else if(priorityLevel == 2) priority = "!!";
		else priority = "!!!";
		System.out.println("[ERROR] " + priority + " " + message);
	}
	
	public static void logPropertyChange(String property, String value){
		System.out.println("[PROPERTY] '" + property + "': " + value);
	}
	
	public static void logButtonEvent(String buttonName){
		System.out.println("[ACTION] Button Activated: " + buttonName);
	}
	
}
