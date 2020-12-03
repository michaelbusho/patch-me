package sender;



import utils.ConsoleColors;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import rmiInterfaceSec.TEMPInterfaceSec;

public class TempSensor extends UnicastRemoteObject implements TEMPInterfaceSec {

    private static final long serialVersionUID = 1L;
    private static double temp = 20;
    private static String sender;
    private static double Currenttemp = 20;

    public static void SetTemp(double set) throws RemoteException {

        Currenttemp = set;
    }



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
            registry = LocateRegistry.createRegistry(5000);
            registry.bind(TEMPInterfaceSec.processName, new TempSensor());


            System.out.println(ConsoleColors.YELLOW_BACKGROUND + ConsoleColors.WHITE_BOLD
                    + " Tempature Sensor In Progress " + ConsoleColors.RESET);

            // Synchronise main process with backup
            //synchronizeTimer.scheduleAtFixedRate(new SynchroniseTask(), 0, synchroniseInterval);

            // < - Run Critical method ->
            detectTemp();

        } catch (Exception e) {
            synchronizeTimer.cancel();
            throw new TempSensorException(e.toString(), TEMPInterfaceSec.processName, registry);
        }
    }

    /**
     * THIS CREATES AN EXCEPTION Based on Sultan's and Ahmed's logic Calculates the
     * object distance
     */
    private static void detectTemp() throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            getTempValue();
        }
    }


    private static double getTempValue() {
        double temp = getEnginTemp();
        double fahrenheitTemp = convertToFahrenheit(temp);
        System.out.println("Tempature in Celsius: " + temp + " Degrees, in Fahrenheit: "+fahrenheitTemp + " -v0.0.4");
        return temp;
    }

    private static double getEnginTemp(){
        double min = -5, max = 5;
        double changeInTemp = (min + Math.random() * (max - min));
        if((temp + changeInTemp)>10) {
        	temp += changeInTemp;
        }
            
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
        return temp;
    }
    public static double convertToFahrenheit(double CelsiusTemp){
        return (CelsiusTemp * 9/5) + 32; 
    }

    private static void alrtHihgTemp() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {

            System.out.println("The Temperature is high");
            //alertCoolingSystem();

    }
    private static void cooling(double rpm, double temp) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {

        double cool=rpm/temp;
        SetTemp(cool);

    }


}