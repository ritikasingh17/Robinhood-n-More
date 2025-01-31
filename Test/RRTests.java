package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class RRTests extends BaseTest {

    @Test
    public void testNoExpiring() {
        TimedTasks.resetTime();
        CPUScheduler rrScheduler = new RRScheduler(8);
        TimedTasks taskList = new TimedTasks();
        addBursts(rrScheduler, taskList, 4, 6, 3, 7);

        runATask(rrScheduler, taskList, 0, 4);
        runATask(rrScheduler, taskList, 1, 6);
        runATask(rrScheduler, taskList, 2, 3);
        runATask(rrScheduler, taskList, 3, 7);
    }

    @Test
    public void testExpiring() {
        TimedTasks.resetTime();
        CPUScheduler rrScheduler = new RRScheduler(5);
        TimedTasks taskList = new TimedTasks();
        addBursts(rrScheduler, taskList, 4, 6, 3, 11, 2);

        runATask(rrScheduler, taskList, 0, 4);
        runATask(rrScheduler, taskList, 1, 5); // Kicked off with 1ms remaining
        runATask(rrScheduler, taskList, 2, 3);
        runATask(rrScheduler, taskList, 3, 5); // Kicked off with 6ms remaining
        runATask(rrScheduler, taskList, 4, 2, 1);
        runATask(rrScheduler, taskList, 1, 1, 3);
        runATask(rrScheduler, taskList, 3, 5, 3);
        runATask(rrScheduler, taskList, 3, 1, -1);
    }
}
