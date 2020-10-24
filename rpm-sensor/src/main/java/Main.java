import java.rmi.RemoteException;

import receiver.RPMBeatChecker;
import sender.RPMSensor;


public class Main {

	public static void main(String[] args) {
		try {
			RPMSensor rpmsensor = new RPMSensor();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RPMBeatChecker rpmbc = new RPMBeatChecker();

	}

}
