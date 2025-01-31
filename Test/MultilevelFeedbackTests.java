package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class MultilevelFeedbackTests extends BaseTest {

    @Test
    public void testOneLevel() {
        TimedTasks.resetTime();
        CPUScheduler multilevelScheduler = new MultilevelFeedbackQueueScheduler();
        TimedTasks tasks = new TimedTasks();

        addBursts(multilevelScheduler, tasks, 2, 1, 2);
        runATask(multilevelScheduler, tasks, 0, 2);
        runATask(multilevelScheduler, tasks, 1, 1);
        runATask(multilevelScheduler, tasks, 2, 2);
    }

    @Test
    public void testAllArrivingTogether() {
        TimedTasks.resetTime();
        CPUScheduler multilevelScheduler = new MultilevelFeedbackQueueScheduler();
        TimedTasks tasks = new TimedTasks();

        addBursts(multilevelScheduler, tasks, 2, 4, 3, 17, 9, 2, 10);

        // 1st queue
        runATask(multilevelScheduler, tasks, 0, 2);
        runATask(multilevelScheduler, tasks, 1, 3); // 2nd quene, 1ms left
        runATask(multilevelScheduler, tasks, 2, 3);
        runATask(multilevelScheduler, tasks, 3, 3); // 2nd queue, 14ms left
        runATask(multilevelScheduler, tasks, 4, 3); // 2nd queue, 6ms left
        runATask(multilevelScheduler, tasks, 5, 2);
        runATask(multilevelScheduler, tasks, 6, 3, 1); // 2nd queue, 7ms left

        // 2nd queue
        runATask(multilevelScheduler, tasks, 1, 1, 3);
        runATask(multilevelScheduler, tasks, 3, 6, 4); // Last queue, 8ms left
        runATask(multilevelScheduler, tasks, 4, 6, 6);
        runATask(multilevelScheduler, tasks, 6, 6, 3); // Last queue, 1ms left

        // Last queue
        runATask(multilevelScheduler, tasks,3, 8, 6);
        runATask(multilevelScheduler, tasks, 6, 1);
    }

    @Test
    public void testIntermittentArrival() {
        TimedTasks.resetTime();
        CPUScheduler multilevelScheduler = new MultilevelFeedbackQueueScheduler();
        TimedTasks tasks = new TimedTasks();

        addBursts(multilevelScheduler, tasks, 2, 15, 5, 3);

        runATask(multilevelScheduler, tasks, 0, 2);
        runATask(multilevelScheduler, tasks, 1, 3); // 2nd queue, 12ms left

        // Indexes 4-5
        addBursts(multilevelScheduler, tasks, 11, 3);
        runATask(multilevelScheduler, tasks, 2, 3); // 2nd queue, 2ms left
        runATask(multilevelScheduler, tasks, 3, 3);
        runATask(multilevelScheduler, tasks, 4, 3); // 2nd queue, 8ms left
        runATask(multilevelScheduler, tasks, 5, 3, 1);

        // Add a new tasks partway through
        // No preemption, so current one should finish
        runATask(multilevelScheduler, tasks, 1, 3, 1); // Still active, 2nd queue, 3ms left in 2nd queue, 10ms left overall
        // Index 6
        addBursts(multilevelScheduler, tasks, 5);
        runATask(multilevelScheduler, tasks, 1, 3, 6); // Last queue, 6ms left
        runATask(multilevelScheduler, tasks, 6, 3, 2); // 2nd queue, 2ms left

        // Run the rest to completion
        // 2nd queue
        runATask(multilevelScheduler, tasks, 2, 2, 4);
        runATask(multilevelScheduler, tasks, 4, 6, 6); // Last queue, 2ms left
        runATask(multilevelScheduler, tasks, 6, 2, 1);
        // Last queue
        runATask(multilevelScheduler, tasks, 1, 6, 4);
        runATask(multilevelScheduler, tasks, 4, 2, -1);

    }
}
