package sender;

import RPMrmiInterface.*;

import utils.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;

public class RPMSensor extends UnicastRemoteObject implements RPMInterface {

    private static final long serialVersionUID = 1L;

    private static final int flywheelTeeth = 30;

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
     * Simulation of distance value In real life could come from sensor or another
     * Based on Ahmed's and Sultan's logic
     */
    private static void detectRPM() {

        int threshold = -99999999;
        double objectProximity = 0;
        while (true) {
            if (threshold == 99999999) {
                objectProximity = getRPMValue();
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
    private static double getRPMValue() {
        double rpm = measureRPM(pulsesPerSecond());
        double showRpm = rpm/1000; //PATCH this it show the rpm for example 1.8 RPMs, deleting / the RPMs will show 1800
        System.out.println(" " + showRpm + " RMPS");
        return rpm;
    }

    private static double measureRPM(double theTimeBtweenTwoTeeth){
        double rpm;
        rpm = theTimeBtweenTwoTeeth*60/flywheelTeeth;
        return rpm;

    }

    private static double pulsesPerSecond(){
        double min = 100, max = 900; //ms //or patch this

        return min + Math.random() * (max - min);
    }

}
