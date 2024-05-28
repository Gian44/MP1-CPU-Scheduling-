package schedulers;

import java.util.LinkedList;

public class PremptiveSchedulerTest {
    public static void main(String[] args) {
        // Creating a list of incoming processes
        LinkedList<Process> listOfIncomingProcesses = new LinkedList<>();
        listOfIncomingProcesses.add(new Process(1, 10, 0, 2)); // Process ID, Arrival Time, CPU Burst Time, Priority
        listOfIncomingProcesses.add(new Process(2, 5, 2, 1));
        listOfIncomingProcesses.add(new Process(3, 2, 4, 3));

        // Creating and testing the PremptiveScheduler with SRTF algorithm
        // testScheduler(listOfIncomingProcesses, "SRTF");
        // Creating and testing the PremptiveScheduler with Prio algorithm
        testScheduler(listOfIncomingProcesses, "Prio");
    }

    private static void testScheduler(LinkedList<Process> processes, String algo) {
        System.out.println("Testing PremptiveScheduler with algorithm: " + algo);
        LinkedList<Process> processesCopy = new LinkedList<>(processes); // Making a copy to avoid modifying the original list
        PremptiveScheduler scheduler = new PremptiveScheduler(processesCopy, algo);
        LinkedList<Process> completedProcesses = scheduler.simulate();

        // Printing the completed processes
        for (Process process : completedProcesses) {
            System.out.println(process.getProcessId());
        }
        System.out.println("--------------------------------------------------");
    }
}
