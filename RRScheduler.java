package edu.utdallas.cs4348;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class RRScheduler implements CPUScheduler {

    private int timeQuantum;
    private CPUBurst runningTask;
    private Queue<CPUBurst> taskQueue = new LinkedList<>();
    private TimedTasks timedTasks = new TimedTasks();
    private Timer timer;

    public RRScheduler(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void addTask(CPUBurst burst) {
        if (!hasTasks()) {
            runningTask = burst;
            runningTask.start(this);
            timer = new Timer(timeQuantum) {
                @Override
                void timerExpired() {
                    runningTask.stop();
                    taskQueue.add(runningTask);
                    runningTask = taskQueue.poll();
                    if (runningTask != null) {
                        runningTask.start(RRScheduler.this);
                    }
                }
            };
            TimedTasks.addTimer(timer);
        } else {
            taskQueue.add(burst);
        }
    }

    @Override
    public void done(CPUBurst burst) {
        TimedTasks.clearTimer();
        runningTask = taskQueue.poll();
        if (runningTask != null) {
            runningTask.start(this);
            timer = new Timer(timeQuantum) {
                @Override
                void timerExpired() {
                    runningTask.stop();
                    taskQueue.add(runningTask);
                    runningTask = taskQueue.poll();
                    if (runningTask != null) {
                        runningTask.start(RRScheduler.this);
                    }
                }
            };
            TimedTasks.addTimer(timer);
        }
    }

    @Override
    public boolean hasTasks() {
        if (runningTask == null) {
            return false;
        }
        else {
            return true;
        }
    }
}