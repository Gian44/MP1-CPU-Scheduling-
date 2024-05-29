package scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import algorithms.Process;

public class MLFQScheduler {
    private ArrayList<Queue<Process>> queues = new ArrayList<>();;
    private ArrayList<QueueInformation> queueInfos = new ArrayList<>();
    private ArrayList<String> algorithms = new ArrayList<>();
    private int numberOfQueues = 0;
    private int time = 0;
    private int remainingCPUTime = 0;
    private int timeQuantum;
    private LinkedList<Process> listOfIncomingProcesses;
    private LinkedList<Process> listOfCompletedProcesses = new LinkedList<>();

    public MLFQScheduler(LinkedList<Process> listOfIncomingProcesses, ArrayList<String> algorithms, int numberOfQueues) {
        this.listOfIncomingProcesses = listOfIncomingProcesses;
        this.numberOfQueues = numberOfQueues;
        this.algorithms = algorithms;
        initializeQueues();
    }

    public MLFQScheduler(LinkedList<Process> listOfIncomingProcesses, ArrayList<String> algorithms, int numberOfQueues, int timeQuantum) {
        this.listOfIncomingProcesses = listOfIncomingProcesses;
        this.numberOfQueues = numberOfQueues;
        this.algorithms = algorithms;
        this.timeQuantum = timeQuantum;
        initializeQueues();
    }

    private void initializeQueues() {
        for (int i = 0; i < numberOfQueues; i++) {
            queues.add(new LinkedList<>());
            queueInfos.add(new QueueInformation(numberOfQueues - i, Math.pow(2, i + 2)));
        }
    }

    public void simulate() {
        while (thereAreProcessesToBeExecuted()) {
            for (int i = 0; i < numberOfQueues; i++) {
                remainingCPUTime = queueInfos.get(i).getTimeSlot();
                while (remainingCPUTime > 0) {
                    addProcessToQueue(0);
                    if (!currentQueueIsEmpty(i))  {
                        boolean higherProcessArrived = false;
                        if (algorithms.get(i).equalsIgnoreCase("SJF") ||
                        algorithms.get(i).equalsIgnoreCase("SRTF")) {
                            sortProcessQueueByRemainingTIme(queues.get(i));
                        }
                        else if (algorithms.get(i).equalsIgnoreCase("Prio") ||
                        algorithms.get(i).equalsIgnoreCase("NonPrio")) {
                            sortProcessQueueByPriority(queues.get(i));
                        }
                        Process currentProcess = getCurrentProcess(i);
                        int runTime = Math.min(remainingCPUTime, currentProcess.getRemainingTime());
                        runTime = Math.min(runTime, currentProcess.getAllocatedTime());
                        if (algorithms.get(i).equalsIgnoreCase("Robin")) {
                            runTime = Math.min(runTime, timeQuantum);
                        }
                        
                        while (runTime > 0) {
                            addProcessToQueue(0);
                            if (algorithms.get(i).equalsIgnoreCase("SRTF") || 
                            algorithms.get(i).equalsIgnoreCase("NonPrio")) {
                                sortProcessQueueByRemainingTIme(queues.get(0));
                                if (higherProcessArrived(currentProcess, algorithms.get(i))) {
                                    higherProcessArrived = true;
                                    break;
                                }   
                            }
                            currentProcess.decrementRemainingBurstTime();
                            currentProcess.decrementAllocatedTime();
                            System.out.println("Time: " + time + " - Running Process: " + currentProcess.getProcessId() + " at Queue " + (i + 1));
                            time++;
                            remainingCPUTime--;
                            runTime--;
                        }

                        if (currentProcess.hasFinishedExecution()) { // Completion
                            computeTimeStatistics(currentProcess);
                            listOfCompletedProcesses.add(currentProcess);
                        }
                        else if (currentProcess.noMoreAllocatedTime() 
                        && i + 1 < numberOfQueues 
                        && !currentProcess.hasFinishedExecution()) { // Demotion
                            currentProcess.setPriority(currentProcess.getPriority() - 1);
                            currentProcess.setAllocatedTime(queueInfos.get(i + 1).getTimeQuantum());
                            queues.get(i + 1).add(currentProcess);
                        }
                        else if ((remainingCPUTime == 0 || higherProcessArrived) 
                        && i - 1 >= 0 
                        && !currentProcess.hasFinishedExecution()) { // Promotion
                            currentProcess.setPriority(currentProcess.getPriority() + 1);
                            currentProcess.setAllocatedTime(queueInfos.get(i - 1).getTimeQuantum());
                            queues.get(i - 1).add(currentProcess);
                        } 
                        else { // Retention
                            queues.get(i).add(currentProcess);
                        }
                    }
                    else {
                        time++;
                        remainingCPUTime--;
                    }
                }
            }
        }
    }

    private boolean higherProcessArrived(Process process, String algo) {
        if (queues.get(0).peek() == null) {
            return false;
        }
        return algo.equalsIgnoreCase("SJF") ||
        algo.equalsIgnoreCase("SRTF") ? 
        queues.get(0).peek().getRemainingTime() < process.getRemainingTime() :
        queues.get(0).peek().getPriority() > process.getPriority();
    }

    private Process getCurrentProcess(int i) {
        Process currentProcess = queues.get(i).poll();
        currentProcess.setFirstExecutionTime(time);
        currentProcess.setAllocatedTime(queueInfos.get(i).getTimeQuantum());
        return currentProcess;
    }

    public static void main(String[] args) {
        ArrayList<String> algorithms = new ArrayList<>();
        algorithms.add("Robin");
        algorithms.add("Prio");
        algorithms.add("FCFS");
        MLFQScheduler test = new MLFQScheduler(createTestProcesses(), algorithms, 3);
        for (String algorithm : algorithms) {
            if (algorithm.equalsIgnoreCase("Robin")) {
                int timeQuantum = 2;
                test = new MLFQScheduler(createTestProcesses(), algorithms, 3, timeQuantum);
                break;
            }
        }
        test.simulate();
    }

    private static LinkedList<Process> createTestProcesses() {
        LinkedList<Process> processes = new LinkedList<>();
        processes.add(new Process(1, 3, 5, 3)); // Process ID, Arrival Time, Burst Time, Priority
        processes.add(new Process(2, 5, 3, 5));
        processes.add(new Process(3, 7, 8, 4));
        processes.add(new Process(4, 9, 6, 2));
        return processes;
    }

    private void sortProcessQueueByPriority(Queue<Process> processes) {
        ArrayList<Process> list = new ArrayList<>(processes);
        list.sort((p1, p2) -> Integer.compare(p2.getPriority(), p1.getPriority())); // Reverse the comparison
        processes.clear();
        processes.addAll(list);
    }    
    private void sortProcessQueueByRemainingTIme(Queue<Process> processes) {
        ArrayList<Process> list = new ArrayList<>(processes);
        list.sort(Comparator.comparingInt(Process::getRemainingTime));
        processes.clear();
        processes.addAll(list);
    }
    private boolean thereAreProcessesToBeExecuted() {
        for (Queue<Process> queue: queues) {
            if (!queue.isEmpty()) {
                return true;
            }
        }
        if (!listOfIncomingProcesses.isEmpty()) {
            return true;
        }
        return false;
    }
    private boolean currentQueueIsEmpty(int index) {
        return queues.get(index).isEmpty();
    }
    private void addProcessToQueue(int index) {
        ArrayList<Integer> elementsToRemove = new ArrayList<>();
        for (int i = 0; i < listOfIncomingProcesses.size(); i++) {
            if (time >= listOfIncomingProcesses.get(i).getArrivalTime()) {
                queues.get(index).add(listOfIncomingProcesses.get(i));
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
    private void computeTimeStatistics(Process process) {
        process.setCompletionTime(time);
        process.getWaitingTime();
        process.getTurnaroundTime();
        process.getResponseTime();
    }
}
