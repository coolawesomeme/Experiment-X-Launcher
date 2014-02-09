package com.rokru.experiment_x_launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

public class Config {

	private static Random random = new Random();
	public static String username = "Player" + random.nextInt(999);
	
	public Config(){
		if(new File(Launcher.getDirectory() + "/options.properties").exists()){
			username = getValue("username");
			validateUsername();
		}else{
			Properties prop = new Properties();
			OutputStream output = null;
			try {
				output = new FileOutputStream(Launcher.getDirectory() + "/options.properties");
				// set the properties value
				prop.setProperty("username", username);
		 
				// save properties to project root folder
				prop.store(output, null);
		 
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		 
			}
		
		}
	}
	
	//i specifies whether or not to validate username
	public static void reload(boolean i){
		username = getValue("username");
		if(i)
			validateUsername();
	}
	
	private static String getValue(String key){
		Properties prop = new Properties();
		InputStream input = null;
		String s = null;
		try {
			input = new FileInputStream(Launcher.getDirectory() + "/options.properties");
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out
			s = prop.getProperty(key);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return s;
	}
	
	public static void setValue(String key, String value){
		Properties prop = new Properties();
		OutputStream output = null;
	 
		try {
	 
			output = new FileOutputStream(Launcher.getDirectory() + "/options.properties");
	 
			// set the properties value
			prop.setProperty(key, value);
	 
			// save properties to project root folder
			prop.store(output, null);
	 
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
		reload(false);
	}
	
	private static void validateUsername(){
		if(username != null){
			if(username.length() <= 18){
				if(username.length() > 0){
					;
				}else{
					setValue("username", "Player" + random.nextInt(999));
				}
			}else{
				setValue("username", username.substring(0, 18));
			}
		}
	}
	
}
