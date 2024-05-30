package source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import scheduler.MLFQScheduler;
import algorithms.Process;

public class CPUScheduler {
    private LinkedList<Process> processes = new LinkedList<>();
    private String prioOrder;
    private int NumOfQueues;
    private MLFQScheduler mlfqsched;
    private LinkedList<ExecutionStep> output;
    private LinkedList<Process> listOfCompletedProcesses;
    private ArrayList<String> algorithms = new ArrayList<>();

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
            processes.add(new Process(i+1, burstTimeArray[i], arrivalTimeArray[i], priorityArray[i]));
            System.out.println("i: " +i);
        }
        // Sort processes by arrival time
        sortProcessesByArrivalTime();
        System.out.println(NumOfQueues);
        checkNumberOfQueues(NumOfQueues, mainUI);
        printProcesses();
        listofCompletedProcess();
    }  

    private void checkNumberOfQueues(int NumOfQueues, MainUI mainUI) {
        if (NumOfQueues == 1) {
            String queue1Algo = mainUI.getQueue1Algo();
            algorithms.add(queue1Algo);
            int queueQuantum = mainUI.getQueueQuantum();
            System.out.println(queue1Algo);
            mlfqsched = new MLFQScheduler(processes, algorithms, 1, queueQuantum);
            mlfqsched.simulate(); 
            output = mlfqsched.executionSequence();
            
        }
        if (NumOfQueues == 2) { 
        	String queue1Algo = mainUI.getQueue1Algo();
            algorithms.add(queue1Algo);
            
            String queue2Algo = mainUI.getQueue2Algo();
            algorithms.add(queue2Algo);
            
            int queueQuantum = mainUI.getQueueQuantum(); 
            
            mlfqsched = new MLFQScheduler(processes, algorithms, 2, queueQuantum);
            mlfqsched.simulate(); 
            output = mlfqsched.executionSequence();
            
        }
        if (NumOfQueues == 3) {
        	String queue1Algo = mainUI.getQueue1Algo();
            algorithms.add(queue1Algo);
            
            int queueQuantum = mainUI.getQueueQuantum();
            
            String queue2Algo = mainUI.getQueue2Algo();
            algorithms.add(queue2Algo);
            
            
            String queue3Algo = mainUI.getQueue3Algo();
            algorithms.add(queue3Algo);
            
            mlfqsched = new MLFQScheduler(processes, algorithms, 3, queueQuantum);
            mlfqsched.simulate(); 
            output = mlfqsched.executionSequence();
            
        } 
        listOfCompletedProcesses = mlfqsched.getListOfCompletedProcess();
        AverageWaitingTime(mainUI);
        AverageTurnAroundTime(mainUI);
        AverageResponseTime(mainUI);
    }
    
    public void listofCompletedProcess() {
    	for(Process process: listOfCompletedProcesses) {
    		System.out.println(process);
    	}
    }
    
    public void AverageWaitingTime(MainUI mainUI) {
    	int sum = 0;
    	double average = 0;
    	for(Process process: listOfCompletedProcesses) {
    		sum += process.getWaitingTime();
    	}
    	average = (double) sum / listOfCompletedProcesses.size();
    	mainUI.getAverageWT().setText(""+String.format("%.2f", average));
    }
    
    public void AverageTurnAroundTime(MainUI mainUI) {
    	int sum = 0;
    	double average = 0;
    	for(Process process: listOfCompletedProcesses) {
    		sum += process.getTurnaroundTime();
    	}
    	average = (double) sum / listOfCompletedProcesses.size();
    	mainUI.getAverageTT().setText(""+String.format("%.2f", average));
    }
    
    public void AverageResponseTime(MainUI mainUI) {
    	int sum = 0;
    	double average = 0;
    	for(Process process: listOfCompletedProcesses) {
    		sum += process.getResponseTime();
    	}
    	average = (double) sum / listOfCompletedProcesses.size();
    	mainUI.getAverageRT().setText(""+String.format("%.2f", average));
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

    public LinkedList<ExecutionStep> getOrderedProcesses() {
        return output;
    }

    public String getPrioOrder() {
        return prioOrder;
    }
    
    public void printProcesses() {
    	System.out.println("Output");
        for (ExecutionStep process : output) {
            System.out.println(process);
        }
    }
}
