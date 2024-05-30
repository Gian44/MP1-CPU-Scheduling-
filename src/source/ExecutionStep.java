package source;

public class ExecutionStep {
    private int time;
    private algorithms.Process process;

    public ExecutionStep(int time, algorithms.Process process) {
        this.time = time;
        this.process = process;
    }

    public int getTime() {
        return time;
    }

    public algorithms.Process getProcess() {
        return process;
    }

    @Override
    public String toString() {
        return "Time: " + time + ", Process: " + process.getProcessId();
    }
}
