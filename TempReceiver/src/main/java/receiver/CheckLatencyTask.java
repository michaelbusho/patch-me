package receiver;

import java.util.Date;
import java.util.TimerTask;

public class CheckLatencyTask extends TimerTask {
    @Override
    public void run() {
        if (BeatChecker.getLastUpdatedTime() > 0) {
            Date previous = new Date(BeatChecker.getLastUpdatedTime());
            Date moment = new Date();
            long latencyInMillies = moment.getTime() - previous.getTime();
            if (latencyInMillies > BeatChecker.getExpireTime()) {
                BeatChecker.ProcessException("Timed out");
            }
        }
    }
}
