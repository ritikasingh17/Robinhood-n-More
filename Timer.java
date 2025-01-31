package edu.utdallas.cs4348;

public abstract class Timer {
    private int remainingTime;

    public Timer(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public boolean timePasses(int amount) {
        remainingTime -= amount;
        //System.out.println(remainingTime);
        if ( amount < 0) {
            throw new RuntimeException("Timer allowed to go past it's expiration!");
        } else {
            return (remainingTime == 0);
        }
    }

    abstract void timerExpired();
}
