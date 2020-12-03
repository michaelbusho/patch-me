package receiver;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

import TempReceiverInterface.TempReceiverInterface;
import rmiInterface.TEMPInterface;
import utils.ConsoleColors;

public class BeatChecker extends UnicastRemoteObject implements TempReceiverInterface{
	private static String failureMsg = "No beat... ";
	private static boolean exception = false;

	private static int checkingInterval = 1000; // 0.5second
	private static int checkingTime = 1000; // 0.25 sec check the time difference
	private static int expireTime = 1500; // 1 second
	private static Long lastUpdatedTime = (long) 0;

	private static final long serialVersionUID = 19998L;
	private static Boolean backup = false;

	public BeatChecker() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws AlreadyBoundException, RemoteException {
		Timer heartbeatTimer = new Timer();
		Timer latencyTimer = new Timer();
		Registry registry = null;
		try{
			//bind yourself to the registry and be ready to receive commands
			registry = LocateRegistry.createRegistry(TempReceiverInterface.pNumber);
			registry.bind(TempReceiverInterface.prName, new BeatChecker());

			// Reads a heartbeat
			heartbeatTimer.scheduleAtFixedRate(new CheckBeatTask(backup), 0, checkingInterval);
			latencyTimer.scheduleAtFixedRate(new CheckLatencyTask(), 0, checkingTime);
		}catch (Exception e){
			heartbeatTimer.cancel();
			latencyTimer.cancel();
			throw e;
		}

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

	@Override
	/**
	 * Method to stop main process while patching
	 * The Beat-checker stops main and starts listening from alternative
	 * Returns true for success , false for failure
	 */
	public Boolean stopMainTemp() throws RemoteException, NotBoundException {
		//1 alternate processes
		System.out.println("PATCHING PHASE - SWITCHING TO BACK UP");
		backup = true;
		System.out.println("PATCHING PHASE - STOPPING MAIN PROCESS SO IT CAN BE PROPERLY PATCHED");
		//2) stop main
		try{
			Registry registry = LocateRegistry.getRegistry(TEMPInterface.portNumber);
			TEMPInterface sender = (TEMPInterface) registry.lookup(TEMPInterface.processName);
			sender.terminate();
		} catch(Exception e){
//			System.out.println(e);
		}
		return true;
	}

	@Override
	/**
	 * Method to restore main process after patching and patching validation
	 * The Beat-checker starts listening from main
	 * Returns true for success , false for failure
	 */
	public Boolean restoreMainTemp(){
		System.out.println("RECOVERY PHASE - STARTING MAIN PROCESS AGAIN");
		//1) start main
		//2) alternate to main
		System.out.println("RECOVERY PHASE - SWITCHING BACK TO MAIN");
		backup = false;
		return false;
	}

}