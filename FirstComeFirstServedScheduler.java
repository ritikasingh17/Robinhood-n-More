package edu.utdallas.cs4348;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FirstComeFirstServedScheduler implements CPUScheduler {

    private Queue<CPUBurst> tasks = new LinkedList<>();
    private CPUBurst runningTask;

    @Override
    public void addTask(CPUBurst burst) {
        if (tasks.isEmpty() && !hasTasks()) {
            burst.start(this);
            runningTask = burst;
        }
        else {
            tasks.add(burst);
        }
    }

    @Override
    public void done(CPUBurst burst) {
        if (!tasks.isEmpty()) {
            CPUBurst newTask = tasks.poll();
            newTask.start(this);
            runningTask = newTask;
        }
        else {
            runningTask = null;
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