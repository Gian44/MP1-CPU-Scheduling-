package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class NonPreemptiveScheduler {
    private int time = 0;
    private LinkedList<Process> listOfCompletedProcesses = new LinkedList<>();
    private LinkedList<Process> listOfIncomingProcesses;
    private PriorityQueue<Process> processesQueue;
    private LinkedList<Process> roundRobinQueue;
    private String algoToDo;
    private int timeQuantum = 1;

    public NonPreemptiveScheduler(LinkedList<Process> listOfIncomingProcesses, String algoToDo) {
        this.listOfIncomingProcesses = listOfIncomingProcesses;
        this.algoToDo = algoToDo;
        determineAlgorithm(algoToDo);
    }

    public NonPreemptiveScheduler(LinkedList<Process> listOfIncomingProcesses, String algoToDo, int timeQuantum) {
        this.listOfIncomingProcesses = listOfIncomingProcesses;
        this.algoToDo = algoToDo;
        this.timeQuantum = timeQuantum;
        determineAlgorithm(algoToDo);
    }

    private void determineAlgorithm(String algoToDo) {
        if (algoToDo.equalsIgnoreCase("FCFS")) {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getArrivalTime));
        } else if (algoToDo.equalsIgnoreCase("SJF")) {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime));
        } else if (algoToDo.equalsIgnoreCase("RoundRobin")) {
            roundRobinQueue = new LinkedList<>();
        } else {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        }
    }

    public LinkedList<Process> simulate() {
        if (algoToDo.equalsIgnoreCase("RoundRobin")) {
            return simulateRoundRobin();
        } else {
            return simulateNonPreemptive();
        }
    }

    private LinkedList<Process> simulateNonPreemptive() {
        while (thereAreProcessesToBeExecuted()) {
            while (processesQueue.isEmpty()) {
                addProcessToQueue();
                if (queueIsNoLongerEmpty()) {
                    break;
                }
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

    private LinkedList<Process> simulateRoundRobin() {
        while (thereAreProcessesToBeExecuted()) {
            while (roundRobinQueue.isEmpty()) {
                addProcessToRoundRobinQueue();
                if (queueIsNoLongerEmpty()) {
                    break;
                }
                time++;
            }

            Process currentProcess = roundRobinQueue.poll();
            currentProcess.setFirstExecutionTime(time);
            int runTime = Math.min(timeQuantum, currentProcess.getRemainingTime());

            for (int i = 0; i < runTime; i++) {
                addProcessToRoundRobinQueue();
                currentProcess.decrementRemainingBurstTime();
                System.out.println("Time: " + time + " - Running Process: " + currentProcess.getProcessId());
                time++;
            }

            if (!currentProcess.hasFinishedExecution()) {
                roundRobinQueue.add(currentProcess);
            } else {
                computeTimeStatistics(currentProcess);
                listOfCompletedProcesses.add(currentProcess);
            }
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

    private void addProcessToRoundRobinQueue() {
        ArrayList<Integer> elementsToRemove = new ArrayList<>();
        for (int i = 0; i < listOfIncomingProcesses.size(); i++) {
            if (time >= listOfIncomingProcesses.get(i).getArrivalTime()) {
                roundRobinQueue.add(listOfIncomingProcesses.get(i));
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
        if (algoToDo.equalsIgnoreCase("RoundRobin")) {
            return !roundRobinQueue.isEmpty() || !listOfIncomingProcesses.isEmpty();
        } else {
            return !processesQueue.isEmpty() || !listOfIncomingProcesses.isEmpty();
        }
    }

    private void computeTimeStatistics(Process process) {
        process.setCompletionTime(time);
        process.getWaitingTime();
        process.getTurnaroundTime();
        process.getResponseTime();
    }

    private boolean queueIsNoLongerEmpty() {
        if (algoToDo.equalsIgnoreCase("RoundRobin")) {
            return !roundRobinQueue.isEmpty();
        } else {
            return !processesQueue.isEmpty();
        }
    }
}
