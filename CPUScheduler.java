package edu.utdallas.cs4348;

public interface CPUScheduler extends DoneCallback {
    void addTask(CPUBurst burst);
    boolean hasTasks();
}
