package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class FirstComeFirstServedTests extends BaseTest {


    @Test
    public void testSingleBurst() {
        TimedTasks.resetTime();
        CPUScheduler scheduler = new FirstComeFirstServedScheduler();
        TimedTasks timedTasks = new TimedTasks();
        CPUBurst burst = new CPUBurst(5);
        timedTasks.add(burst);
        scheduler.addTask(burst);

        assert(scheduler.hasTasks());
        timedTasks.timePasses(4);
        assert(scheduler.hasTasks());
        timedTasks.timePasses(1);
        assert(!scheduler.hasTasks());

        System.out.println("Start time is " + burst.getStartTime());
        assert(burst.getStartTime() == 0);
        assert(burst.getEndTime() == 5);
    }

    @Test
    public void testAllArrivingAtSameTime() {
        TimedTasks.resetTime();
        CPUScheduler scheduler = new FirstComeFirstServedScheduler();
        TimedTasks timedTasks = new TimedTasks();
        addBursts(scheduler, timedTasks, 2, 15, 3, 4);

        runATask(scheduler, timedTasks, 0, 2);
        runATask(scheduler, timedTasks, 1, 15);
        runATask(scheduler, timedTasks, 2, 3);
        runATask(scheduler, timedTasks, 3, 4);
    }

    @Test
    public void testArrivingAtIntervals() {
        TimedTasks.resetTime();
        CPUScheduler scheduler = new FirstComeFirstServedScheduler();
        TimedTasks timedTasks = new TimedTasks();

        addBursts(scheduler, timedTasks, 4, 10, 3);
        runATask(scheduler, timedTasks, 0, 4);

        addBursts(scheduler, timedTasks, 2, 5, 8);
        runATask(scheduler, timedTasks, 1, 10);
        runATask(scheduler, timedTasks, 2, 3);

        addBursts(scheduler, timedTasks, 7, 4);
        runATask(scheduler, timedTasks, 3, 2);
        runATask(scheduler, timedTasks, 4, 5);
        runATask(scheduler, timedTasks, 5, 8);
        runATask(scheduler, timedTasks, 6, 7);
        runATask(scheduler, timedTasks, 7, 4);

    }
}
