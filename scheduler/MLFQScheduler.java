package scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import algorithms.Process;
import source.ExecutionStep;

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
    private LinkedList<ExecutionStep> executionSequence = new LinkedList<>();

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
                System.out.println("############### QUEUE " + (i + 1) + " ##############");
                remainingCPUTime = queueInfos.get(i).getTimeSlot();
                while (remainingCPUTime > 0) {
                    addProcessToQueue(0);
                    if (!currentQueueIsEmpty(i))  {

                        if (algorithms.get(i).equalsIgnoreCase("Shortest Remaining Time First")) {
                            sortProcessQueueByRemainingTime(queues.get(i));
                        }
                        else if (algorithms.get(i).equalsIgnoreCase("Priority (Preemptive)")) {
                            sortProcessQueueByPriority(queues.get(i));
                        }
                        else if (algorithms.get(i).equalsIgnoreCase("Round Robin")) {
                            if (queueInfos.get(i).firstTimeRobinSet()) {
                                queueInfos.get(i).setRobinTimeQueue(timeQuantum);
                                queueInfos.get(i).robinFlip();
                            }
                        }

                        Process currentProcess = getCurrentProcess(i);
                        int runTime = Math.min(remainingCPUTime, currentProcess.getRemainingTime());
                        runTime = Math.min(runTime, currentProcess.getAllocatedTime());
                        if (algorithms.get(i).equalsIgnoreCase("Round Robin")) {
                            runTime = Math.min(runTime, queueInfos.get(i).getRobinRemainingTimeQueue());
                        }
                        
                        while (runTime > 0) {
                            
                            currentProcess.decrementRemainingBurstTime();
                            currentProcess.decrementAllocatedTime();
                            System.out.println("Time: " + time + " - Running Process: " + currentProcess.getProcessId());
                            executionSequence.add(new ExecutionStep(time, currentProcess));
                            time++;
                            remainingCPUTime--;
                            runTime--;
                            addProcessToQueue(0);
                            if (algorithms.get(i).equalsIgnoreCase("Shortest Remaining Time First")) {
                                sortProcessQueueByRemainingTime(queues.get(i));
                                if (higherProcessArrived(currentProcess, algorithms.get(i))) {
                                    break;
                                }   
                            }
                            else if (algorithms.get(i).equalsIgnoreCase("Priority (Preemptive)")) {
                                sortProcessQueueByPriority(queues.get(i));
                                if (higherProcessArrived(currentProcess, algorithms.get(i))) {
                                    break;
                                }   
                            }

                            if (algorithms.get(i).equalsIgnoreCase("Round Robin")) {
                                queueInfos.get(i).decrementRobinRemainingTimeQueue();
                            }
                        }

                        if (currentProcess.hasFinishedExecution()) { // Completion
                            computeTimeStatistics(currentProcess);
                            listOfCompletedProcesses.add(currentProcess);
                            if (algorithms.get(i).equalsIgnoreCase("Round Robin")) {
                                queueInfos.get(i).robinFlip();
                            }
                            else if (algorithms.get(i).equalsIgnoreCase("Shortest Job First")) {
                                sortProcessQueueByRemainingTime(queues.get(i));
                            }
                            else if (algorithms.get(i).equalsIgnoreCase("Priority (Non-preemptive)")) {
                                sortProcessQueueByPriority(queues.get(i));
                            }
                        }
                        else if (currentProcess.noMoreAllocatedTime() 
                        && i + 1 < numberOfQueues 
                        && !currentProcess.hasFinishedExecution()) { // Demotion
                            currentProcess.setAllocatedTime(queueInfos.get(i + 1).getTimeQuantum());
                            queues.get(i + 1).add(currentProcess);
                            System.out.println("Process " + currentProcess.getProcessId() + " has been demoted to Queue #" + (i + 2));
                        }
                        else { // Retention
                            if (algorithms.get(i).equalsIgnoreCase("Round Robin")) {
                                if (!queueInfos.get(i).noMoreRobinRemainingTimeQueue()) {
                                    addToFront(queues.get(i), currentProcess);
                                }
                                else {
                                    queueInfos.get(i).robinFlip();
                                    queues.get(i).add(currentProcess);
                                }
                            }
                            else {
                                addToFront(queues.get(i), currentProcess);
                            }
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
        return algo.equalsIgnoreCase("Shortest Job First") ||
        algo.equalsIgnoreCase("Shortest Remaining Time First") ? 
        queues.get(0).peek().getRemainingTime() < process.getRemainingTime() :
        queues.get(0).peek().getPriority() > process.getPriority();
    }

    private Process getCurrentProcess(int i) {
        Process currentProcess = queues.get(i).poll();
        currentProcess.setFirstExecutionTime(time);
        currentProcess.setAllocatedTime(queueInfos.get(i).getTimeQuantum());
        return currentProcess;
    }
    /*
    public static void main(String[] args) {
        ArrayList<String> algorithms = new ArrayList<>();
        algorithms.add("Robin");
        MLFQScheduler test = new MLFQScheduler(createTestProcesses(), algorithms, 1);
        for (String algorithm : algorithms) {
            if (algorithm.equalsIgnoreCase("Robin")) {
                int timeQuantum = 2;
                test = new MLFQScheduler(createTestProcesses(), algorithms, 1, timeQuantum);
                break;
            }
        }
        test.simulate();
    }

    private static LinkedList<Process> createTestProcesses() {
        LinkedList<Process> processes = new LinkedList<>();
        processes.add(new Process(1, 7, 3, 2)); // Process ID, Arrival Time, Burst Time, Priority
        processes.add(new Process(2, 2, 4, 1));
        processes.add(new Process(3, 1, 9, 3));
        processes.add(new Process(4, 8, 10, 8));
        return processes;
    }*/

    private void sortProcessQueueByPriority(Queue<Process> processes) {
        ArrayList<Process> list = new ArrayList<>(processes);
        list.sort((p1, p2) -> {
            int priorityComparison = Integer.compare(p2.getPriority(), p1.getPriority());
            if (priorityComparison == 0) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
            return priorityComparison;
        });
        processes.clear();
        processes.addAll(list);
    }
    
    private void sortProcessQueueByRemainingTime(Queue<Process> processes) {
        ArrayList<Process> list = new ArrayList<>(processes);
        list.sort((p1, p2) -> {
            int remainingTimeComparison = Integer.compare(p1.getRemainingTime(), p2.getRemainingTime());
            if (remainingTimeComparison == 0) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
            return remainingTimeComparison;
        });
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
    private void addToFront(Queue<Process> processes, Process process) {
        // Convert the queue to a list
        ArrayList<Process> list = new ArrayList<>(processes);
        
        // Add the new element to the front of the list
        list.add(0, process);
        
        // Clear the queue and add all elements from the list back to the queue
        processes.clear();
        processes.addAll(list);
    }

    public LinkedList<ExecutionStep> executionSequence(){
    	return executionSequence;
    }
    public LinkedList<Process> getListOfCompletedProcess(){
    	return listOfCompletedProcesses;
    }
}
