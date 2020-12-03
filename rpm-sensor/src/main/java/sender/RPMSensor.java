package sender;

import RPMrmiInterface.*;

import utils.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;

public class RPMSensor extends UnicastRemoteObject implements RPMInterface {

    private static final long serialVersionUID = 1L;

    private static final int flywheelTeeth = 30;
    private static  Registry registry = null;

    public RPMSensor() throws RemoteException {
        super();
    }

    @Override
    public String ping() throws RemoteException {
        return "I am Alive";
    }

    @Override
    public void terminate() throws RemoteException, NotBoundException {
        System.out.println("Main process is terminating - Ready to be patched");
        registry.unbind(processName);
        UnicastRemoteObject.unexportObject(registry, true);
        System.exit(0);
    }

    public static void main(String[] args) throws TempSensorException {
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
    private static void detectRPM() throws InterruptedException {
        while (true) {
            getRPMValue();
            Thread.sleep(1000);
        }
    }

    /**
     * Simulation of distance value In real life could come from sensor or another
     * Based on Ahmed's and Sultan's logic
     */
    private static double getRPMValue() {
        double rpm = measureRPM(pulsesPerSecond());
        double showRpm = rpm; //PATCH this it show the rpm for example 1.8 RPMs, deleting / the RPMs will show 1800
        System.out.println(" " + showRpm + " RMPS" + " - v0.0.5");
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
