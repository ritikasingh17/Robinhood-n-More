package edu.utdallas.cs4348;

import java.util.*;

public class PriorityScheduler implements CPUScheduler {
    private HashMap<Integer, LinkedList<CPUBurst>> taskPriorities = new HashMap<>();
    private CPUBurst runningTask;
    @Override
    public void addTask(CPUBurst burst) {
        int priority = burst.getPriority();

        if (!hasTasks()) {
            if (!taskPriorities.containsKey(priority)) {
                taskPriorities.put(priority, new LinkedList<>());
            }
            burst.start(this);
            runningTask = burst;
        }
        else {
            if (runningTask != null && (burst.getPriority() > runningTask.getPriority())) {
                runningTask.stop();
                taskPriorities.get(runningTask.getPriority()).addFirst(runningTask);

                if (!taskPriorities.containsKey(priority)) {
                    taskPriorities.put(priority, new LinkedList<CPUBurst>());
                }

                burst.start(this);
                runningTask = burst;
            }
            else {
                if (taskPriorities.containsKey(priority)) {
                    taskPriorities.get(priority).addLast(burst);
                }
                else {
                    taskPriorities.put(priority, new LinkedList<CPUBurst>());
                    taskPriorities.get(priority).addLast(burst);
                }
            }
        }
    }

    @Override
    public void done(CPUBurst burst) {
        boolean selected = false;

        List<Integer> sortedPriorities = new ArrayList<>(taskPriorities.keySet());
        Collections.sort(sortedPriorities, Collections.reverseOrder());

        for (Integer key : sortedPriorities) {
            LinkedList<CPUBurst> selectedTask = taskPriorities.get(key);
            if (selectedTask != null && !selectedTask.isEmpty()) {
                runningTask = selectedTask.removeFirst();
                runningTask.start(this);
                selected = true;
                break;
            }
        }
        if (!selected) {
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