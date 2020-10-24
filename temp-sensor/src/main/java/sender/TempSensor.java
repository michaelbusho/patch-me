package sender;

import rmiInterface.*;

import utils.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;

public class TempSensor extends UnicastRemoteObject implements RMIInterface {

    private static final long serialVersionUID = 1L;

    public TempSensor() throws RemoteException {
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
            registry = LocateRegistry.createRegistry(RMIInterface.portNumber);
            registry.bind(RMIInterface.processName, new TempSensor());

            System.out.println(ConsoleColors.YELLOW_BACKGROUND + ConsoleColors.WHITE_BOLD
                    + " Tempature Sensor In Progress " + ConsoleColors.RESET);

            // Synchronise main process with backup
            //synchronizeTimer.scheduleAtFixedRate(new SynchroniseTask(), 0, synchroniseInterval);

            // < - Run Critical method ->
            detectTemp();

        } catch (Exception e) {
            synchronizeTimer.cancel();
            throw new TempSensorException(e.toString(), RMIInterface.processName, registry);
        }
    }

    /**
     * THIS CREATES AN EXCEPTION Based on Sultan's and Ahmed's logic Calculates the
     * object distance
     */
    private static void detectTemp() {
        int threshold = -99999999;
        float objectProximity = 0;
        while (true) {
            if (threshold == 99999999) {
                objectProximity = getTempValue();
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
    private static int getTempValue() {
        int temp = new Random().nextInt(999);
        System.out.println("Tempature: " + temp + " Degrees");
        return temp;
    }

}