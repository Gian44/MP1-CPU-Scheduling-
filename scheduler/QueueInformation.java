package scheduler;

public class QueueInformation {
    private int timeSlot;
    private int timeQuantum;

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
}
