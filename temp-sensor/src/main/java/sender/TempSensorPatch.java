package sender;



import RPMrmiInterface.RPMInterface;
import rmiInterface.TEMPInterface;
import utils.*;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;

public class TempSensorPatch extends UnicastRemoteObject implements TEMPInterface {

    private static final long serialVersionUID = 1L;

    private static int temp = 20;
    private static String sender;
    private static int Currenttemp = 20;

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
            detectTemp();;

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
        //PATCHed Temperature in the message is written wrong
        System.out.println("Temperature in Celsius: " + temp + " Degrees, in Fahrenheit: "+fahrenheitTemp);

        return temp;
    }

    private static int getEnginTemp(){
        int min = -20, max = 80;

        int newTemp = (int) (min + Math.random() * (max - min));
        if (temp+5 > newTemp && temp - 5 < newTemp){
            temp = newTemp;
            if (temp> 75)// PATCHed we can change the Temperature where the system alert high Temperature
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
        return (CelsiusTemp * 9/5) + 32; //PATCHed wrong formula, the right formula (CelsiusTemp * 9/5) + 32
    }

    private static void alrtHihgTemp() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {

        System.out.println("The Temperature is higher than 75 C");//patched
        alertCoolingSystem();

    }
    private static void cooling(int rpm, int temp) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {

        int cool=rpm/temp;
        SetTemp(cool);

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

    public static void SetTemp(int set) throws RemoteException {

        Currenttemp = set;
    }
}