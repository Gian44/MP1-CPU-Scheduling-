package schedulers;
public class Process {
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private int firstExecutionTime = -1;
    private int completionTime;
    private int remainingTime;
    private String processId;

    public Process(Object processId, int burstTime, int arrivalTime, int priority) {
        this.processId = processId.toString();
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }

    public void decrementRemainingBurstTime() {
        this.remainingTime--;
    }

    public boolean hasFinishedExecution() {
        return this.remainingTime == 0;
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getCompletionTime() {
        return this.completionTime;
    }

    public int getFirstExecutionTime() {
        return this.firstExecutionTime;
    }

    public String getProcessId() {
        return this.processId;
    }

    public void setFirstExecutionTime(int firstExecutionTime) {
        if (this.firstExecutionTime == -1) {
            this.firstExecutionTime = firstExecutionTime;
        }
    }
    
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getWaitingTime() {
        return this.completionTime - this.arrivalTime - this.burstTime; 
    }

    public int getTurnaroundTime() {
        return this.completionTime - this.arrivalTime;
    }

    public int getResponseTime() {
        return this.firstExecutionTime - this.arrivalTime;
    }

    public void resetRemainingTime() {
        this.remainingTime = this.burstTime;
    }
}