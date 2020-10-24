package tempSensor.receiver;

import java.util.Date;
import java.util.TimerTask;
import tempSensor.rmiInterface.*;
import utils.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RPMCheckBeatTask extends TimerTask {
    private static RPMInterface sender;

    private static String successIntroMsg = "RPM Sensor responded with: ";
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
        Registry registry = LocateRegistry.getRegistry(RPMInterface.portNumber);
        try {
                sender = (RPMInterface) registry.lookup(RPMInterface.processName);
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