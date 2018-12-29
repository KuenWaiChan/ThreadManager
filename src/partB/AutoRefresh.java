package partB;

import java.util.Timer;
import java.util.TimerTask;

class AutoRefresh extends Timer{

    private Controller c;

    AutoRefresh(Controller c) {
        this.c = c;
    }

    TimerTask task = new TimerTask() {
        public void run() {
            c.refresh();
        }
    };


}
