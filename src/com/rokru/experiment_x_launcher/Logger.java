package com.rokru.experiment_x_launcher;

public class Logger {

	public static void logInfo(String message){
		System.out.println("[INFO] " + message);
	}
	
	public static void logError(String message){
		System.out.println("[ERROR] " + message);
	}
	
	public static void logButtonEvent(String buttonName){
		System.out.println("[ACTION] Button Activated: " + buttonName);
	}
	
}
