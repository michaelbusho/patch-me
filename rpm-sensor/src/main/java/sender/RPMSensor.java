package sender;

import rmiInterface.*;

import utils.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;

public class RPMSensor extends UnicastRemoteObject implements RPMInterface {

    private static final long serialVersionUID = 1L;

    public RPMSensor() throws RemoteException {
        super();
    }

    @Override
    public String ping() throws RemoteException {
        return "I am Alive";
    }

    public static void main(String[] args) throws TempSensorException {
        Registry registry = null;
        Timer synchronizeTimer = new Timer();
        int synchroniseInterval = 1000; // 1 sec

        try {
            // Bind Registry
            registry = LocateRegistry.createRegistry(RPMInterface.portNumber);
            registry.bind(RPMInterface.processName, new RPMSensor());

            System.out.println(ConsoleColors.YELLOW_BACKGROUND + ConsoleColors.WHITE_BOLD
                    + " RMP Sensor In Progress " + ConsoleColors.RESET);

            // Synchronise main process with backup
            //synchronizeTimer.scheduleAtFixedRate(new SynchroniseTask(), 0, synchroniseInterval);

            // < - Run Critical method ->
            detectRPM();

        } catch (Exception e) {
            synchronizeTimer.cancel();
            throw new TempSensorException(e.toString(), RPMInterface.processName, registry);
        }
    }

    /**
     * THIS CREATES AN EXCEPTION Based on Sultan's and Ahmed's logic Calculates the
     * object distance
     */
    private static void detectRPM() {
        int threshold = -99999999;
        float objectProximity = 0;
        while (true) {
            if (threshold == 99999999) {
                try {
                    objectProximity = getRPMValue();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                threshold = -99999999;
            } else {
                threshold++;
            }
        }
    }

    /**
     * Simulation of distance value In real life could come from sensor or another
     * Based on Ahmed's and Sultan's logic
     */
    public static int getRPMValue() throws RemoteException {
        int rpm = new Random().nextInt(9999);
        System.out.println(" " + rpm + " rpms");
        return rpm;
    }

}
