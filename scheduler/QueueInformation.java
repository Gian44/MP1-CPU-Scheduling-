package scheduler;

public class QueueInformation {
    private int timeSlot;
    private int timeQuantum;
    private int robinRemainingTimeQueue;
    private boolean robinFlip = true;

    public QueueInformation(int timeSlot, double d) {
        this.timeSlot = timeSlot;
        this.timeQuantum = (int) d;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public int getRobinRemainingTimeQueue() {
        return robinRemainingTimeQueue;
    }

    public void decrementRobinRemainingTimeQueue() {
        this.robinRemainingTimeQueue--;
    }

    public boolean noMoreRobinRemainingTimeQueue() {
        return robinRemainingTimeQueue == 0;
    }

    public void setRobinTimeQueue(int queueTimeQuantum) {
        this.robinRemainingTimeQueue = queueTimeQuantum;
    }

    public void robinFlip() {
        this.robinFlip = !robinFlip;
    }

    public boolean firstTimeRobinSet() {
        return robinFlip;
    }
}
