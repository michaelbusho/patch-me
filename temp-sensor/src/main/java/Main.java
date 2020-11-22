import sender.TempSensor;
import sender.TempSensorException;

public class Main {

	public static void main(String[] args) {
		try {
			TempSensor.main(args);
		} catch (TempSensorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}