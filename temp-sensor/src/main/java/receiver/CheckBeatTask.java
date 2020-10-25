package receiver;

import java.util.Date;
import java.util.TimerTask;
import rmiInterface.*;
import utils.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CheckBeatTask extends TimerTask {
    private static TEMPInterface sender;

    private static String successIntroMsg = "Tempature Sensor responded with: ";
    private static long maxElapsedTime = 5000; // 5 sec

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
        try {
                sender = (TEMPInterface) registry.lookup(TEMPInterface.processName);
                String response = sender.ping();
                System.out.println(ConsoleColors.GREEN + successIntroMsg + response + ConsoleColors.RESET);
           

        } catch (Exception e) {
            e.notify();
        }

        Date date = new Date();
        long now = date.getTime();
        return now;
    }

}