import java.rmi.RemoteException;

import receiver.RPMBeatChecker;
import sender.RPMSensor;
import sender.TempSensorException;


public class Main {

	public static void main(String[] args) {
		try {
			RPMSensor.main(args);
		} catch (TempSensorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RPMBeatChecker.main(args);

	}

}
