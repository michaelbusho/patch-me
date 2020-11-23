package RPMrmiInterfaceSec;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RPMInterfaceSec extends Remote {
    public static int portNumber = 4714;

    public static String processName = "rpmsendersec";

    public String ping() throws RemoteException;

    //public abstract double getRPMValue();
}