import java.rmi.RemoteException;

import receiver.BeatChecker;
import sender.TempSensor;

public class Main {

	public static void main(String[] args) {
		try {
			TempSensor ts = new TempSensor();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BeatChecker bc = new BeatChecker();
	}

}
