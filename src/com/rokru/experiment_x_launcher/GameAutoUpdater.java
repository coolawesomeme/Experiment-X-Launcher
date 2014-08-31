package com.rokru.experiment_x_launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameAutoUpdater {

	public boolean versionsOnDisk = false;
	public boolean internetConnection = false;
	public boolean updatesAvailable = true;

	public static String latestVersion;
	private static String download_url;
	public String highestVersion;

	private int vMajor = 0;
	private int vMinor = 0;
	private int vRevision = 0;

	public GameAutoUpdater(){
		highestVersion = getHighestDownloadedVersion();
		if(highestVersion != null){
			vMajor = Integer.parseInt(highestVersion.split("\\.")[0]);
			vMinor = Integer.parseInt(highestVersion.split("\\.")[1]);
			vRevision = Integer.parseInt(highestVersion.split("\\.")[2]);
		}
		getUpdateFileInfo();
		updatesAvailable = isOutdated(latestVersion);
	}

	public String getHighestDownloadedVersion() {
		File f = new File(Launcher.getGameDirectory() + "/versions");
		if (f.exists() && f.listFiles().length > 0) {
			List<File> versionFolders = new ArrayList<File>();
			for (File q : f.listFiles()) {
				if (q.getName().startsWith("Experiment X ")) {
					versionFolders.add(q);
				}
			}
			if (!versionFolders.isEmpty()) {
				String highestVersion = versionFolders.get(0).getName()
						.split(" ")[2];
				for (File a : versionFolders) {
					String version = a.getName().split(" ")[2];
					highestVersion = compareVersions(highestVersion, version);
				}
				File x = new File(Launcher.getGameDirectory() + "/versions/Experiment X " + highestVersion);
				if(x.listFiles().length > 0){
					for(File a : x.listFiles()){
						if(a.getName().equals("Experiment X.jar")){
							versionsOnDisk = true;
							return highestVersion;
						}
					}
					versionsOnDisk = false;
					return null;
				}else{
					versionsOnDisk = false;
					return null;
				}
			} else {
				versionsOnDisk = false;
				return null;
			}
		} else {
			versionsOnDisk = false;
			return null;
		}
	}

	/** @return split contents of update file */
	public String[] getUpdateFileInfo() {
		URL url;
		try {
			 url = new URL(
					"https://raw.github.com/coolawesomeme/Experiment-X/master/UPDATE.txt");
			Scanner s = new Scanner(url.openStream());
			String raw = s.nextLine();
			internetConnection = true;
			if (raw.contains("|")) {
				String[] info = raw.split("\\|", -1);
				latestVersion = info[0];
				download_url = info[1];
				s.close();
				return info;
			} else {
				s.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			internetConnection = false;
			latestVersion = highestVersion;
			return null;
		}
	}

	private boolean isOutdated(String version) {
		try{
			int vMajor = Integer.parseInt(version.split("\\.")[0]), vMinor = Integer
				.parseInt(version.split("\\.")[1]), vRevision = Integer
				.parseInt(version.split("\\.")[2]);
			return isOutdated(vMajor, vMinor, vRevision);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private boolean isOutdated(int vMajor, int vMinor, int vRevision) {
		if (this.vMajor < vMajor) {
			return true;
		} else if (this.vMajor > vMajor) {
			return false;
		} else {
			if (this.vMinor < vMinor) {
				return true;
			} else if (this.vMinor > vMinor) {
				return false;
			} else {
				if (this.vRevision < vRevision) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	private String compareVersions(String version1, String version2) {
		int vMajor1 = Integer.parseInt(version1.split("\\.")[0]), vMinor1 = Integer
				.parseInt(version1.split("\\.")[1]), vRevision1 = Integer
				.parseInt(version1.split("\\.")[2]);
		int vMajor2 = Integer.parseInt(version2.split("\\.")[0]), vMinor2 = Integer
				.parseInt(version2.split("\\.")[1]), vRevision2 = Integer
				.parseInt(version2.split("\\.")[2]);
		if (vMajor2 < vMajor1) {
			return version1;
		} else if (vMajor2 > vMajor1) {
			return version2;
		} else {
			if (vMinor2 < vMinor1) {
				return version1;
			} else if (vMinor2 > vMinor1) {
				return version2;
			} else {
				if (vRevision2 < vRevision1) {
					return version1;
				} else if (vRevision2 == vRevision1) {
					return version1;
				} else {
					return version2;
				}
			}
		}
	}

	public boolean update() {
		try{
			File f = new File(Launcher.getGameDirectory() + "/versions/Experiment X " + latestVersion);
			f.mkdirs();
			URL website = new URL(download_url);
			URLConnection conn = website.openConnection();
        	InputStream in = conn.getInputStream();
        	FileOutputStream out = new FileOutputStream(f.getAbsolutePath() + "/Experiment X.jar");
        	byte[] b = new byte[1024];
        	int count;
        	while ((count = in.read(b)) >= 0) {
            	out.write(b, 0, count);
        	}
        	out.flush(); out.close(); in.close();
        	Logger.logInfo("Update " + latestVersion + " Downloaded.");
        	return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}