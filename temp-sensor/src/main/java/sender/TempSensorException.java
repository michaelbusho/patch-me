package sender;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TempSensorException extends Exception {
    private static final long serialVersionUID = 3L;

    public TempSensorException(String s, String processName, Registry registry) {
        super(s);
        // Unbind this from the registry
        if (registry != null)
            stop(processName, registry);

        // Possibly also kill the process with system out since there is an infinite
        // thread?
    }

    /**
     * Unbinds and unexports the process from the registry
     * 
     * @param processName
     * @param registry
     */
    private void stop(String processName, Registry registry) {
        try {
            registry.unbind(processName);
            UnicastRemoteObject.unexportObject(registry, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
