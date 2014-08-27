package com.rokru.experiment_x_launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

public class Config {

	private static HashMap<String, String> configValueMap = new HashMap<String, String>();
	
	public Config(){
		Random random = new Random();
		configValueMap.put("username", "Player" + random.nextInt(999));
		
		if(new File(Launcher.getDirectory() + "/options.properties").exists()){
			for(int i = 0; i<configValueMap.size();i++){
				configValueMap.put((String) configValueMap.keySet().toArray()[i], getValue((String) configValueMap.keySet().toArray()[i]));
			}
		}else{
			Properties prop = new Properties();
			OutputStream output = null;
			try {
				output = new FileOutputStream(Launcher.getDirectory() + "/options.properties");
				// set the properties value
				for(int i = 0; i<configValueMap.size();i++){
					prop.setProperty((String) configValueMap.keySet().toArray()[i], configValueMap.get(configValueMap.keySet().toArray()[i]));
				}
		 
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
	
	public static void reload(boolean validate){
		for(int i = 0; i<configValueMap.size();i++){
			configValueMap.put((String) configValueMap.keySet().toArray()[i], getValue((String) configValueMap.keySet().toArray()[i]));
		}
		if(validate)
			validateUsername(configValueMap.get("username"));
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
			s = prop.getProperty(key, configValueMap.get(key));
			
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
			for(int i = 0; i < configValueMap.size(); i++){
				prop.setProperty((String) configValueMap.keySet().toArray()[i], configValueMap.get(configValueMap.keySet().toArray()[i]));
			}
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
		configValueMap.put(key, value);
	}
	
	public static String getProperty(String property){
		if(configValueMap.containsKey(property)) return configValueMap.get(property);
		else return null;
	}
	
	private static void validateUsername(String username){
		Random random = new Random();
		if(username != null){
			if(username.length() <= 18){
				if(username.length() > 0){
					if(!username.matches("[^a-zA-Z0-9]"))
						return;
					else 
						setValue("username", "Player" + random.nextInt(999));
				}else{
					setValue("username", "Player" + random.nextInt(999));
				}
			}else{
				setValue("username", username.substring(0, 18));
			}
		}else{
			setValue("username", "Player" + random.nextInt(999));
		}
	}
}
