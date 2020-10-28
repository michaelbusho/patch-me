package receiver;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import utils.ConsoleColors;

public class BeatChecker {
	private static String failureMsg = "No beat... ";
	private static boolean exception = false;

	private static int checkingInterval = 1000; // 0.5second
	private static int checkingTime = 1000; // 0.25 sec check the time difference
	private static int expireTime = 1000; // 1 second
	private static Long lastUpdatedTime = (long) 0;

	public static void main(String[] args) {
		Timer heartbeatTimer = new Timer();
		Timer latencyTimer = new Timer();

		// Reads a heartbeat
		heartbeatTimer.scheduleAtFixedRate(new CheckBeatTask(), 0, checkingInterval);
		latencyTimer.scheduleAtFixedRate(new CheckLatencyTask(), 0, checkingTime);
	}

	public static Long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public static void updateTime(Long time) {
		lastUpdatedTime = time;
	}

	public static int getExpireTime() {
		return expireTime;
	}

	public static boolean hasException() {
		return exception;
	}
	
	public static void ProcessException(String msg) {
		System.out.println(ConsoleColors.RED + failureMsg + ConsoleColors.RESET);

	}

}