package sender;



import rmiInterface.TEMPInterface;
import utils.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;

public class TempSensorPatch extends UnicastRemoteObject implements TEMPInterface {

    private static final long serialVersionUID = 1L;

    protected TempSensorPatch() throws RemoteException {
        super();
    }

    @Override
    public String ping() throws RemoteException {
        return "I am Alive";
    }

    public static void main(String[] args) throws TempSensorException {
        Registry registry = null;
        Timer synchronizeTimer = new Timer();

        try {
            // Bind Registry
            registry = LocateRegistry.createRegistry(TEMPInterface.portNumber);
            registry.bind(TEMPInterface.processName, new TempSensor());

            System.out.println(ConsoleColors.YELLOW_BACKGROUND + ConsoleColors.WHITE_BOLD
                    + " Object Detection In Progress " + ConsoleColors.RESET);

            // Synchronise main process with backup
            //synchronizeTimer.scheduleAtFixedRate(new SynchroniseTask(), 0, synchroniseInterval);

            // < - Run Critical method ->
            detectObject();

        } catch (Exception e) {
            synchronizeTimer.cancel();
            throw new TempSensorException(e.toString(), TEMPInterface.processName, registry);
        }
    }

    /**
     * THIS CREATES AN EXCEPTION Based on Sultan's and Ahmed's logic Calculates the
     * object distance
     */
    private static void detectObject() {
        int threshold = -99999999;
        double objectProximity = 0;
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
    private static double getTempValue() {
        double distance = new Random().nextDouble()*1000.0;
        System.out.println("Tempature: " + distance + " Degrees");
        return distance;
    }
}

