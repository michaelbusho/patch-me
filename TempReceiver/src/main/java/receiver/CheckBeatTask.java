package receiver;

import java.util.Date;
import java.util.TimerTask;
import rmiInterface.TEMPInterface;
import rmiInterfaceSec.TEMPInterfaceSec;
import utils.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CheckBeatTask extends TimerTask {
    private static TEMPInterface sender;
    private static TEMPInterfaceSec senderR;

    private static String successIntroMsg = "Tempature Sensor responded with: ";
    private static String successIntroMsg2 = "Second Tempature Sensor responded with: ";
    private static long maxElapsedTime = 5000; // 5 sec
    private static boolean backup = false;

    public CheckBeatTask(Boolean isFirst){
        backup = isFirst;
    }

    @Override
    public void run() {
        try {
            BeatChecker.updateTime(checkAlive());
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
        Registry registry = LocateRegistry.getRegistry(TEMPInterface.portNumber);
        Registry secRegistry = LocateRegistry.getRegistry(TEMPInterfaceSec.portNumber);
        try {
        		if(!backup) {
	                sender = (TEMPInterface) registry.lookup(TEMPInterface.processName);
	                String response = sender.ping();
	                System.out.println(ConsoleColors.GREEN + successIntroMsg + response + ConsoleColors.RESET);
        		}else {
                    senderR = (TEMPInterfaceSec) secRegistry.lookup(TEMPInterfaceSec.processName);
                    /**if (!shown)
                        checkSyncExpiry(senderR.getLastSyncTime());*/
                    String recovResponse = senderR.ping();
                    System.out.println(ConsoleColors.GREEN + successIntroMsg2 + recovResponse + ConsoleColors.RESET);

                    try {
                    	sender = (TEMPInterface) registry.lookup(TEMPInterface.processName);
                        backup = false;
                    } catch (Exception e) {
                        //TODO: handle exception
                        backup = true;
                    }

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