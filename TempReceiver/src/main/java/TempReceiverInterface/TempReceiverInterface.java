package TempReceiverInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public interface TempReceiverInterface extends Remote {
    public static int pNumber = 4919;
    public static String prName = "tempReceiver";

    public Boolean stopMainTemp() throws RemoteException, NotBoundException;

    public Boolean restoreMainTemp() throws RemoteException;
}
