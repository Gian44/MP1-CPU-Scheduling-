

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import algorithms.Process;

public class CPUScheduler {
    private LinkedList<Process> processes = new LinkedList<>();
    private String prioOrder;
    private int NumOfQueues;
    private String Queue1Algo;
    private int Queue1Quantum;
    private String Queue2Algo;
    private int Queue2Quantum;
    private String Queue3Algo;
    private int Queue3Quantum;
    //PremptiveScheduler preempAlgo;
    private LinkedList<Process> output;
    private GanttChart gChart;

    public CPUScheduler(MainUI mainUI) {
        // Extract data from MainUI
    	output = new LinkedList<>();
        int numOfProcess = mainUI.getNumofProcess(); 
        String arrivalTimes = mainUI.getArrivalTime();
        String burstTimes = mainUI.getBurstTime();
        String priorities = mainUI.getPriority();
        this.NumOfQueues = mainUI.getNumOfQueues();
        
        // Parse the data and create Process objects
        int[] arrivalTimeArray = parseStringToIntArray(arrivalTimes, numOfProcess);
        int[] burstTimeArray = parseStringToIntArray(burstTimes, numOfProcess);
        int[] priorityArray = parseStringToIntArray(priorities, numOfProcess);
 
        for (int i = 0; i < numOfProcess; i++) {
            processes.add(new Process(""+(i+1), arrivalTimeArray[i], burstTimeArray[i], priorityArray[i]));
            System.out.println("i: " +i);
        }
        // Sort processes by arrival time
        sortProcessesByArrivalTime();
        System.out.println(NumOfQueues);
        checkNumberOfQueues(NumOfQueues, mainUI);
        printProcesses();
    }  

    private void checkNumberOfQueues(int NumOfQueues, MainUI mainUI) {
        if (NumOfQueues >= 1) {
            String queue1Algo = mainUI.getQueue1Algo();
            int queue1Quantum = mainUI.getQueue1Quantum();
            System.out.println(queue1Algo);
        }
        if (NumOfQueues >= 2) { 
            String queue2Algo = mainUI.getQueue2Algo();
            int queue2Quantum = mainUI.getQueue2Quantum();
            
        }
        if (NumOfQueues >= 3) {
            String queue3Algo = mainUI.getQueue3Algo();
            int queue3Quantum = mainUI.getQueue3Quantum();
            
        }
        
    }

    private int[] parseStringToIntArray(String input, int numOfProcess) {
        String[] parts = input.trim().split("\\s+");
        int[] array = new int[numOfProcess];
        for (int i = 0; i < numOfProcess; i++) {
        	System.out.println(parts[i]);
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    } 

    private void sortProcessesByArrivalTime() {
        Collections.sort(processes, new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });
    }

    public LinkedList<Process> getOrderedProcesses() {
        return output;
    }

    public String getPrioOrder() {
        return prioOrder;
    }
    
    public void printProcesses() {
        for (Process process : output) {
            System.out.println(process);
        }
    }
}
