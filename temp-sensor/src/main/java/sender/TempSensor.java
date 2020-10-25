package sender;

import rmiInterface.*;

import utils.*;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import static rmiInterface.RPMInterface.*;

public class TempSensor extends UnicastRemoteObject implements TEMPInterface {

    private static final long serialVersionUID = 1L;
    private static int temp = 20;
    private static String sender;


    public TempSensor() throws RemoteException {
        super();
    }

    @Override
    public String ping() throws RemoteException {
        return "I am Alive";
    }

    public static void main(String[] args) throws TempSensorException {
        Registry registry = null;
        Registry RMPregistry = null;
        Timer synchronizeTimer = new Timer();
        int synchroniseInterval = 1000; // 1 sec

        try {
            // Bind Registry
            registry = LocateRegistry.createRegistry(TEMPInterface.portNumber);
            registry.bind(TEMPInterface.processName, new TempSensor());
            RMPregistry = LocateRegistry.createRegistry(RPMInterface.portNumber);
            RMPregistry.bind(RPMInterface.processName, new TempSensor());

            System.out.println(ConsoleColors.YELLOW_BACKGROUND + ConsoleColors.WHITE_BOLD
                    + " Tempature Sensor In Progress " + ConsoleColors.RESET);

            // Synchronise main process with backup
            //synchronizeTimer.scheduleAtFixedRate(new SynchroniseTask(), 0, synchroniseInterval);

            // < - Run Critical method ->
            detectTemp();

        } catch (Exception e) {
            synchronizeTimer.cancel();
            throw new TempSensorException(e.toString(), TEMPInterface.processName, registry);
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
            if (temp> 70)// PATCH we can change the Temperature where the system alert high Temperature
            {
                try {
                    alrtHihgTemp();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (AlreadyBoundException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }
    public static int convertToFahrenheit(int CelsiusTemp){
        return (CelsiusTemp * 9/5) + 33; //PATCH wrong formula, the right formula (CelsiusTemp * 9/5) + 32
    }

    private static void alrtHihgTemp() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {

            System.out.println("The Temperature is high");
            alertCoolingSystem();

    }
    private static void cooling(int rpm, int temp) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {



    }


    private static void alertCoolingSystem() throws MalformedURLException, RemoteException, NotBoundException, AlreadyBoundException {
        Registry RPMregistry = LocateRegistry.getRegistry(RPMInterface.portNumber);
        RPMregistry.bind(RPMInterface.processName, new RPMSensor());
        try {
            RPMInterface rpmsender = (RPMInterface) RPMregistry.lookup(RPMInterface.processName);
            cooling(RPMInterface.getRPMValue(),getTempValue());
        } catch (Exception e) {
            e.notify();
        }


    }
}