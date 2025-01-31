package edu.utdallas.cs4348;

public class BaseTest {

    protected void addBurstWithPriority(CPUScheduler scheduler, TimedTasks tasks, int... timerOrPriority) {
        assert((timerOrPriority.length % 2) == 0);
        for ( int i=0; i<timerOrPriority.length; i+=2) {
            CPUBurst burst = new CPUBurst(timerOrPriority[i], timerOrPriority[i+1]);
            scheduler.addTask(burst);
            tasks.add(burst);
        }
    }


    protected void addBursts(CPUScheduler scheduler, TimedTasks tasks, int... timers) {
        for ( int timer : timers) {
            CPUBurst burst = new CPUBurst(timer);
            scheduler.addTask(burst);
            tasks.add(burst);
        }
    }

    protected void onlyOneRunning(TimedTasks tasks, int index) {
        for ( int i=0; i<tasks.size(); i++) {
            boolean shouldBeRunning = (i == index);
            //System.out.println("Should " + i + " be running: " + shouldBeRunning);
            assert(tasks.get(i).isRunning() == shouldBeRunning);
        }
    }

    private void noneRunning(TimedTasks tasks) {
        for ( CPUBurst burst : tasks) {
            assert(!burst.isRunning());
        }
    }

    protected void runATask(CPUScheduler scheduler, TimedTasks tasks, int index, int runtime) {
        runATask(scheduler, tasks, index, runtime, index+1);
    }

    protected void runATask(CPUScheduler scheduler, TimedTasks tasks, int index, int runtime, int nextIndex) {

        System.out.println("Running task " + index);
        assert(scheduler.hasTasks());
        onlyOneRunning(tasks, index);
        tasks.timePasses(runtime-1);
        assert(scheduler.hasTasks());
        onlyOneRunning(tasks, index);
        tasks.timePasses(1);
        if ( (nextIndex >= 0) && (nextIndex < tasks.size())) {
            onlyOneRunning(tasks, nextIndex);
        } else {
            noneRunning(tasks);
        }

        // Last task if we've run through them all or if we're told we're done (nextIndex < 0)
        if ( (nextIndex >= tasks.size()) || (nextIndex < 0)) {
            // Last task
            assert(!scheduler.hasTasks());
        } else {
            assert(scheduler.hasTasks());
        }


    }

}
