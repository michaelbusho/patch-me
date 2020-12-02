package RPMrmiInterface;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RPMInterface extends Remote {
    public static int portNumber = 4712;

    public static String processName = "rpmsender";

    public String ping() throws RemoteException;

    public static double getRPMValue() throws RemoteException {
        return 0;
    }

    public void terminate() throws RemoteException, NotBoundException;
}