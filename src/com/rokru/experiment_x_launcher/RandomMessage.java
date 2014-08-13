package com.rokru.experiment_x_launcher;

import java.util.Random;

public class RandomMessage {

	private static String[] protips = {
			"Protip: Use W, A, S, D to move.",
			"Protip: If you get stuck on a path, try playing a different path. (NYI)",
			"Protip: Try using the mouse! (NYI)" };

	private static String[] splash = { "Help! I'm trapped in a computer game!",
			"Play it now, play it always!", "Ask your doctor if Experiment X is right for you",
			"But who was phone?", "Let's get ready to rumble!", "Better than stale bread!"};

	private static String[] messages = {};

	public static String getRandomProtip() {
		Random random = new Random();
		return protips[random.nextInt(protips.length)];
	}

	public static String getRandomSplash() {
		Random random = new Random();
		return splash[random.nextInt(splash.length)];
	}

	public static String getRandomMessage() {
		messages = combineArrays(protips, splash);
		Random random = new Random();
		return messages[random.nextInt(messages.length)];
	}

	public static String[] getAllProtips() {
		return protips;
	}

	public static String[] getAllSplashes() {
		return splash;
	}

	public static String[] getAllMessages() {
		messages = combineArrays(protips, splash);
		return messages;
	}

	public static void addProtip(String protip) {
		String[] protips2 = new String[protips.length + 1];
		for (int i = 0; i < protips.length; i++) {
			protips2[i] = protips[i];
		}
		protips2[protips2.length - 1] = protip;
		protips = protips2;
	}

	public static void addSplash(String x) {
		String[] splash2 = new String[splash.length + 1];
		for (int i = 0; i < splash.length; i++) {
			splash2[i] = splash[i];
		}
		splash2[splash2.length - 1] = x;
		splash = splash2;
	}

	private static String[] combineArrays(String[] A, String[] B) {
		int aLen = A.length;
		int bLen = B.length;
		String[] C = new String[aLen + bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}
}