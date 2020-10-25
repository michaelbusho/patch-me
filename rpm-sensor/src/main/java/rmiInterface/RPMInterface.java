package rmiInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RPMInterface extends Remote {
    public static int portNumber = 4712;

    public static String processName = "rpmsender";

    public String ping() throws RemoteException;

    public static int getRPMValue() throws RemoteException {
        return 0;
    }
}