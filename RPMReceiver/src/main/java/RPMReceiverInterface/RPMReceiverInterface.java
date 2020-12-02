package RPMReceiverInterface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RPMReceiverInterface extends Remote{
    public static int portNumber = 4814;
    public static String processName = "rpmReceiver";

    public Boolean stopMainRpm() throws RemoteException, NotBoundException;

    public Boolean restoreMainRpm() throws RemoteException;
}
