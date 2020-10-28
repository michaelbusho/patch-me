package receiver;

import java.util.Date;
import java.util.TimerTask;
import RPMrmiInterface.*;
import RPMrmiInterfaceSec.RPMInterfaceSec;
import utils.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RPMCheckBeatTask extends TimerTask {
    private static RPMInterface sender;
    private static RPMInterfaceSec senderR;

    private static String successIntroMsg = "RPM Sensor responded with: ";
    private static long maxElapsedTime = 5000; // 5 sec
    private static boolean backup = false;

    @Override
    public void run() {
        try {
            RPMBeatChecker.updateTime(checkAlive());
        } catch (Exception e) {
        }
    }

    /**
     * Checks heartbeat
     * 
     * @throws MalformedURLException
     * @throws RemoteException
     * @throws NotBoundException
     */
    private static long checkAlive() throws MalformedURLException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(RPMInterface.portNumber);
        Registry secRegistry = LocateRegistry.getRegistry(RPMInterfaceSec.portNumber);
        try {
        		if(!backup) {
	                sender = (RPMInterface) registry.lookup(RPMInterface.processName);
	                String response = sender.ping();
	                System.out.println(ConsoleColors.GREEN + successIntroMsg + response + ConsoleColors.RESET);
			    }else {
			        senderR = (RPMInterfaceSec) secRegistry.lookup(RPMInterfaceSec.processName);
			        /**if (!shown)
			            checkSyncExpiry(senderR.getLastSyncTime());*/
			        String recovResponse = senderR.ping();
			        System.out.println(ConsoleColors.GREEN + successIntroMsg + recovResponse + ConsoleColors.RESET);
			
			        /**try {
			            sender = (RMIInterface) registry.lookup(RMIInterface.processName);
			            backup = false;
			        } catch (Exception e) {
			            //TODO: handle exception
			            backup = true;
			        }*/
			
			    }
           

        } catch (Exception e) {
        	backup = true;
            e.notify();
        }

        Date date = new Date();
        long now = date.getTime();
        return now;
    }

}