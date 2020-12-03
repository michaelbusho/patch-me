package receiver;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

import RPMReceiverInterface.RPMReceiverInterface;
import RPMrmiInterface.RPMInterface;
import utils.ConsoleColors;

public class RPMBeatChecker extends UnicastRemoteObject implements RPMReceiverInterface {
	private static String failureMsg = "No beat... ";
	private static boolean exception = false;

	private static int checkingInterval = 1000; // 0.5second
	private static int checkingTime = 1000; // 0.25 sec check the time difference
	private static int expireTime = 1500; // 1 second
	private static Long lastUpdatedTime = (long) 0;
	private static Boolean backup = false;

	private static final long serialVersionUID = 12643L;

	public RPMBeatChecker() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws AlreadyBoundException, RemoteException {
		//Be able to receive remote commands from patcher
		Registry registry = null;
		Timer synchronizeTimer = new Timer();

		Timer heartbeatTimer = new Timer();
		Timer latencyTimer = new Timer();
		try {
			//bind yourself to the registry and be ready to receive commands
			registry = LocateRegistry.createRegistry(RPMReceiverInterface.portNumber);
			registry.bind(RPMReceiverInterface.processName, new RPMBeatChecker());

			// Reads a heartbeat
			heartbeatTimer.scheduleAtFixedRate(new RPMCheckBeatTask(backup), 0, checkingInterval);
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
	public Boolean stopMainRpm() throws RemoteException, NotBoundException {
		//1 alternate processes
		System.out.println("PATCHING PHASE - SWITCHING TO BACK UP");
		backup = true;
		System.out.println("PATCHING PHASE - STOPPING MAIN PROCESS SO IT CAN BE PROPERLY PATCHED");
		//2) stop main
		try{
			Registry registry = LocateRegistry.getRegistry(RPMInterface.portNumber);
			RPMInterface sender = (RPMInterface) registry.lookup(RPMInterface.processName);
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
	public Boolean restoreMainRpm(){
		System.out.println("RECOVERY PHASE - STARTING MAIN PROCESS AGAIN");
		//1) start main
		//2) alternate to main
		System.out.println("RECOVERY PHASE - SWITCHING BACK TO MAIN");
		backup = false;
		return false;
	}
}
