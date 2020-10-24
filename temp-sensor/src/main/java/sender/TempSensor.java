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
    private static int temp = 20;


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


    private static int getTempValue() {
        int temp = getEnginTemp();
        int fahrenheitTemp = convertToFahrenheit(temp);
        //PATCH Temperature in the message is written wrong
        System.out.println("Tempature in Celsius: " + temp + " Degrees, in Fahrenheit: "+fahrenheitTemp);
        return temp;
    }

    private static int getEnginTemp(){
        int min = -20, max = 80;

        int newTemp = (int) (min + Math.random() * (max - min));
        if (temp+5 > newTemp && temp - 5 < newTemp){
            temp = newTemp;
        }
        return temp;
    }
    public static int convertToFahrenheit(int CelsiusTemp){
        return (CelsiusTemp * 9/5) + 33; //PATCH wrong formula, the right formula (CelsiusTemp * 9/5) + 32
    }
}