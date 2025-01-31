package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class PrioritySchedulerTests extends BaseTest {

    @Test
    public void testSinglePriorityLevel() {
        TimedTasks.resetTime();
        CPUScheduler priorityScheduler = new PriorityScheduler();
        TimedTasks taskList = new TimedTasks();
        addBursts(priorityScheduler, taskList, 5, 4, 6);

        runATask(priorityScheduler, taskList, 0, 5);
        runATask(priorityScheduler, taskList, 1, 4);
        runATask(priorityScheduler, taskList, 2, 6);
    }

    @Test
    public void testAllArrivingAtOnce() {
        TimedTasks.resetTime();
        CPUScheduler priorityScheduler = new PriorityScheduler();
        TimedTasks taskList = new TimedTasks();
        addBurstWithPriority(priorityScheduler, taskList,
                4, 10,
                5, 0,
                7, 25,
                5, 10,
                4, 10);

        runATask(priorityScheduler, taskList, 2, 7, 0);
        runATask(priorityScheduler, taskList, 0, 4, 3);
        runATask(priorityScheduler, taskList, 3, 5, 4);
        runATask(priorityScheduler, taskList, 4, 4, 1);
        runATask(priorityScheduler, taskList, 1, 5, -1);
    }

    @Test
    public void testPreemption() {

        // Tests are added in this order
        // 0: priority 10, 5ms
        // 1: priority 30, 10ms
        // 2: priority 40, 8ms
        // 3: priority 10, 6ms

        // 4: priority 40, 5ms
        // 5: priority 50, 4ms

        // 6: priority 60, 4ms

        // Order will be 2 (partial), 5, 2 (finish), 6, 4, 1, 0, 3

        TimedTasks.resetTime();
        CPUScheduler priorityScheduler = new PriorityScheduler();
        TimedTasks taskList = new TimedTasks();
        addBurstWithPriority(priorityScheduler, taskList,
                5, 10,
                10, 30,
                8, 40,
                6, 10);

        // Preempt in the middle of something running
        onlyOneRunning(taskList, 2);
        taskList.timePasses(5); // Task 2 has 3 ms left
        onlyOneRunning(taskList, 2);
        addBurstWithPriority(priorityScheduler, taskList,
                5, 40,
                4, 50);
        runATask(priorityScheduler, taskList, 5, 4, 2);
        // The preempted task should be back on the CPU
        runATask(priorityScheduler, taskList, 2, 3, 4);

        // Preempt in-between tasks
        addBurstWithPriority(priorityScheduler, taskList,
                4, 60);
        runATask(priorityScheduler, taskList, 6, 4, 4);
        runATask(priorityScheduler, taskList, 4, 5, 1);
        runATask(priorityScheduler, taskList, 1, 10, 0);
        runATask(priorityScheduler, taskList, 0, 5, 3);
        runATask(priorityScheduler, taskList, 3, 6, -1);


    }
}
