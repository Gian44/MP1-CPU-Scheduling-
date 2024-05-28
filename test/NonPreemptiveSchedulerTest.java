package schedulers;

import java.util.LinkedList;

public class NonPreemptiveSchedulerTest {
    public static void main(String[] args) {
        // Creating a list of incoming processes
        LinkedList<Process> listOfIncomingProcesses = new LinkedList<>();
        listOfIncomingProcesses.add(new Process(1, 10, 0, 2)); // Process ID, Arrival Time, CPU Burst Time, Priority
        listOfIncomingProcesses.add(new Process(2, 5, 0, 1));
        listOfIncomingProcesses.add(new Process(3, 2, 0, 3));

        // Creating and testing the NonPreemptiveScheduler with different algorithms
        // testScheduler(listOfIncomingProcesses, "FCFS");
        // testScheduler(listOfIncomingProcesses, "SJF");
        testScheduler(listOfIncomingProcesses, "Prio");
    }

    private static void testScheduler(LinkedList<Process> processes, String algo) {
        System.out.println("Testing NonPreemptiveScheduler with algorithm: " + algo);
        LinkedList<Process> processesCopy = new LinkedList<>(processes); // Making a copy to avoid modifying the original list
        NonPreemptiveScheduler scheduler = new NonPreemptiveScheduler(processesCopy, algo);
        LinkedList<Process> completedProcesses = scheduler.simulate();
        System.out.println("--------------------------------------------------");
    }
}
