package edu.utdallas.cs4348;

public class CPUBurst {
    private int remainingTime;

    private final int priority;

    private boolean running = false;
    private DoneCallback callback;

    private int startTime;
    private int endTime;

    public CPUBurst(int runtime) {
        this(runtime, 0);
    }

    public CPUBurst(int runtime, int priority) {
        this.remainingTime = runtime;
        this.priority = priority;
    }

    public void start(DoneCallback callback) {
        this.callback = callback;
        startTime = TimedTasks.getCurrentTime();
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void timePassed(int amount) {
        if ( running) {
            remainingTime -= amount;
        }
        if ( remainingTime <= 0) {
            endTime = TimedTasks.getCurrentTime();
            running = false;
            callback.done(this);
        }
    }

    public int getPriority() {
        return priority;
    }

    public boolean isRunning() {
        return running;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}
