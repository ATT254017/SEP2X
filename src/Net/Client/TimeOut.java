package Net.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Afonso on 5/24/2017.
 */
public class TimeOut {

    private static ArrayList<TimeOut> timeOuts = new ArrayList<>();
    private ArrayList<TimeOutEvent> timeOutEvents = new ArrayList<>();
    private LocalDateTime timer;
    private static Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = timeOuts.size() - 1; i > -1; i--) {
                    if (LocalDateTime.now().isAfter(timeOuts.get(i).getTimer())) {
                        TimeOut temp = timeOuts.get(i);
                       new Thread(() -> temp.fireTimeElapsedEvent()).start();
                       synchronized (timeOuts) {
                        timeOuts.remove(i);
                       }
                    }
                }
            }
        }
    });

    public TimeOut(int timeOut) {

        synchronized (timeOuts)
        {
        	timeOuts.add(this);
        }
        timer = LocalDateTime.now().plusNanos((long)(timeOut * 1e6));
        checkThread();
    }

    public void addTimeOutEventListener(TimeOutEvent timeOutEvent) {
        timeOutEvents.add(timeOutEvent);
    }

    public void removeTimeOutListener(TimeOutEvent timeOutEvent) {
        timeOutEvents.remove(timeOutEvent);
    }

    public void fireTimeElapsedEvent() {
        for (int i = timeOutEvents.size() - 1; i > -1; i--) {
            timeOutEvents.get(i).timeOutElapsedAction();
        }
    }

    public LocalDateTime getTimer() {
        return timer;
    }

    public void checkThread() {
        synchronized (thread) {
            if (!thread.isAlive()) {
                thread.setDaemon(true);
                thread.start();
            }
        }
    }
}
