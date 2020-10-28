package rmiInterfaceSec;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TEMPInterfaceSec extends Remote {
    public static int portNumber = 5000;

    public static String processName = "senderSec";

    public String ping() throws RemoteException;

}
