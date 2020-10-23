package tempSensor.rmiInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    public static int portNumber = 4711;

    public static String processName = "sender";

    public String ping() throws RemoteException;

}
