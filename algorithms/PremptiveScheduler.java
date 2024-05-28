package schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PremptiveScheduler {
    private int time = 0;
    private LinkedList<Process> listOfCompletedProcesses = new LinkedList<>();
    private LinkedList<Process> listOfIncomingProcesses;
    private PriorityQueue<Process> processesQueue;
    private String algoToDo;

    public PremptiveScheduler(LinkedList<Process> listOfIncomingProcesses, String algoToDo) {
        this.listOfIncomingProcesses = listOfIncomingProcesses;
        this.algoToDo = algoToDo;
        determineAlgorithm();
    }

    private void determineAlgorithm() {
        if (algoToDo.equalsIgnoreCase("Prio")) {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        } else if (algoToDo.equalsIgnoreCase("SRTF")) {
            processesQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));
        }
    }

    public LinkedList<Process> simulate() {
        while (thereAreProcessesToBeExecuted()) {
            boolean higherProcessArrived = false;
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
                if (higherProcessArrived(currentProcess)) {
                    higherProcessArrived = true;
                    break;
                }
                currentProcess.decrementRemainingBurstTime();
                System.out.println("Time: " + time + " - Running Process: " + currentProcess.getProcessId());
                time++;
            }

            if (higherProcessArrived) {
                processesQueue.offer(currentProcess);
                continue;
            }
            computeTimeStatistics(currentProcess);
            listOfCompletedProcesses.add(currentProcess);
        }

        return listOfCompletedProcesses;
    }

    private boolean thereAreProcessesToBeExecuted() {
        return processesQueue.size() > 0 || listOfIncomingProcesses.size() > 0;
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

    private boolean higherProcessArrived(Process process) {
        if (processesQueue.isEmpty()) {
            return false;
        }
        if (algoToDo.equalsIgnoreCase("Prio")) {
            return processesQueue.peek().getPriority() < process.getPriority();   
        }
        else {
            return processesQueue.peek().getRemainingTime() < process.getRemainingTime();
        }
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
