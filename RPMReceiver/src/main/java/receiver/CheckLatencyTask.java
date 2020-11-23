package receiver;

import java.util.Date;
import java.util.TimerTask;

public class CheckLatencyTask extends TimerTask {
    @Override
    public void run() {
        if (RPMBeatChecker.getLastUpdatedTime() > 0) {
            Date previous = new Date(RPMBeatChecker.getLastUpdatedTime());
            Date moment = new Date();
            long latencyInMillies = moment.getTime() - previous.getTime();
            if (latencyInMillies > RPMBeatChecker.getExpireTime()) {
                RPMBeatChecker.ProcessException("Timed out");
            }
        }
    }
}
