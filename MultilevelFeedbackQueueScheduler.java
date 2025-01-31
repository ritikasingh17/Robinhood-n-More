package edu.utdallas.cs4348;

import java.util.LinkedList;
import java.util.Queue;

public class MultilevelFeedbackQueueScheduler implements CPUScheduler {

    private CPUBurst runningTask;
    private static final int FIRST_QUEUE_QUANTUM = 3;
    private static final int SECOND_QUEUE_QUANTUM = 6;
    private Queue<CPUBurst> firstQueue = new LinkedList<>();
    private Queue<CPUBurst> secondQueue = new LinkedList<>();
    private Queue<CPUBurst> thirdQueue = new LinkedList<>();
    private Timer timer;

    public void startNextTask() {
        runningTask = firstQueue.poll();
        if (runningTask != null) {
            runningTask.start(this);
            timer = new Timer(FIRST_QUEUE_QUANTUM) {
                @Override
                void timerExpired() {
                    runningTask.stop();
                    secondQueue.add(runningTask);
                    startNextTask();
                }
            };
            TimedTasks.addTimer(timer);
        } else {
            runningTask = secondQueue.poll();
            if (runningTask != null) {
                runningTask.start(this);
                timer = new Timer(SECOND_QUEUE_QUANTUM) {
                    @Override
                    void timerExpired() {
                        runningTask.stop();
                        thirdQueue.add(runningTask);
                        startNextTask();
                    }
                };
                TimedTasks.addTimer(timer);
            } else {
                runningTask = thirdQueue.poll();
                if (runningTask != null) {
                    runningTask.start(this);
                } else {
                    runningTask = null;
                }
            }
        }
    }
    @Override
    public void addTask(CPUBurst burst) {
        if (!hasTasks()) {
            runningTask = burst;
            runningTask.start(this);
            timer = new Timer(FIRST_QUEUE_QUANTUM) {
                @Override
                void timerExpired() {
                    runningTask.stop();
                    secondQueue.add(runningTask);
                }
            };
            TimedTasks.addTimer(timer);
        } else {
            firstQueue.add(burst);
        }
    }

    @Override
    public void done(CPUBurst burst) {
        TimedTasks.clearTimer();
        if (!firstQueue.isEmpty()) {
            runningTask = firstQueue.poll();
            runningTask.start(this);
            timer = new Timer(FIRST_QUEUE_QUANTUM) {
                @Override
                void timerExpired() {
                    runningTask.stop();
                    secondQueue.add(runningTask);
                    startNextTask();
                }
            };
            TimedTasks.addTimer(timer);
        } else if (!secondQueue.isEmpty()) {
            runningTask = secondQueue.poll();
            runningTask.start(this);
            timer = new Timer(SECOND_QUEUE_QUANTUM) {
                @Override
                void timerExpired() {
                    runningTask.stop();
                    thirdQueue.add(runningTask);
                    startNextTask();
                }
            };
            TimedTasks.addTimer(timer);
        } else if (!thirdQueue.isEmpty()) {
            runningTask = thirdQueue.poll();
            runningTask.start(this);
        } else {
            runningTask = null;
        }
    }

    @Override
    public boolean hasTasks() {
        return runningTask != null || !firstQueue.isEmpty() || !secondQueue.isEmpty() || !thirdQueue.isEmpty();
    }
}