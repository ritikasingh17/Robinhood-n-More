package edu.utdallas.cs4348;

import java.util.ArrayList;

public class TimedTasks extends ArrayList<CPUBurst> {

    private static Timer timer;

    public static void addTimer(Timer newTimer) {
        timer = newTimer;
    }

    public static void clearTimer() {
        timer = null;
    }

    //------------------------------------------------------
    // From here below is about testing; you don't need to
    // worry about them. :)
    //-----------------------------------------------------

    private static int currentTime = 0;

    public static void resetTime() {
        currentTime = 0;
    }

    public static int getCurrentTime() {
        return currentTime;
    }

    public void timePasses(int amount) {
        currentTime += amount;
        for ( CPUBurst burst : this) {
            // We ONLY need to hit the running task, as the others won't do anything
            if ( burst.isRunning()) {
                burst.timePassed(amount);

                // Since we tell the burst about time passing BEFORE the timer,
                // stop now if another burst has started (because
                // below would be a new timer)
                if ( !burst.isRunning()) {
                    return;
                }
                break;
            }
        }

        // Check to see if a timer has gone off
        if ( timer != null && timer.timePasses(amount)) {
            timer.timerExpired();
        }
    }
}
