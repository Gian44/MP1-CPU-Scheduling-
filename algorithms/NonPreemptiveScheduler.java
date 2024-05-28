package schedulers;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class NonPreemptiveScheduler {
    private int time = 0;
    private LinkedList<Process> listOfCompletedProcesses = new LinkedList<>();
    private LinkedList<Process> listOfIncomingProcesses;
    private PriorityQueue<Process> processesQueue;

    public NonPreemptiveScheduler(LinkedList<Process> listOfIncomingProcesses, String algoToDo) {
        this.listOfIncomingProcesses = listOfIncomingProcesses;
        determineAlgorithm(algoToDo);
    }

    private void determineAlgorithm(String algoToDo) {
        if (algoToDo.equalsIgnoreCase("FCFS")) {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.getArrivalTime()));
        }
        else if (algoToDo.equalsIgnoreCase("SJF")) {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.getBurstTime()));
        }
        else {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.getPriority()));
        }
    }

    public LinkedList<Process> simulate() {
        while (thereAreProcessesToBeExecuted()) {
            while(processesQueue.isEmpty()) {
                addProcessToQueue();
                if(queueIsNoLongerEmpty()) {break;}
                time++;
            }

            Process currentProcess = processesQueue.poll();
            currentProcess.setFirstExecutionTime(time);
            while (!currentProcess.hasFinishedExecution()) {
                addProcessToQueue();
                currentProcess.decrementRemainingBurstTime();
                System.out.println("Time: " + time + " - Running Process: " + currentProcess.getProcessId());
                time++;
            }
            computeTimeStatistics(currentProcess);
            listOfCompletedProcesses.add(currentProcess);
        }
        return listOfCompletedProcesses;
    }

    private void addProcessToQueue() {
        ArrayList<Integer> elementsToRemove = new ArrayList<>();
        for (int i = 0; i < listOfIncomingProcesses.size(); i++) {
            if (time >= listOfIncomingProcesses.get(i).getArrivalTime()) {
                processesQueue.add(listOfIncomingProcesses.get(i));
                elementsToRemove.add(i);
            }
        }
        removeProcessesFromList(elementsToRemove);
    }

    private void removeProcessesFromList(ArrayList<Integer> elementsToRemove) {
        elementsToRemove.sort(Comparator.reverseOrder());
        for (int index : elementsToRemove) {
            listOfIncomingProcesses.remove(index);
        }
    }

    private boolean thereAreProcessesToBeExecuted() {
        return processesQueue.size() > 0 || listOfIncomingProcesses.size() > 0;
    }

    private void computeTimeStatistics(Process process) {
        process.setCompletionTime(time);
        process.getWaitingTime();
        process.getTurnaroundTime();
        process.getResponseTime();
    }

    private boolean queueIsNoLongerEmpty() {
        return !processesQueue.isEmpty();
    }
}
