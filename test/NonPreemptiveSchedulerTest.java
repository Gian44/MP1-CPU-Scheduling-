package test;

import algorithms.NonPreemptiveScheduler;
import algorithms.Process;
import java.util.LinkedList;

public class NonPreemptiveSchedulerTest {
    public static void main(String[] args) {
        testFCFS();
        testSJF();
        testPrio();
        testRoundRobin();
    }

    private static void testFCFS() {
        System.out.println("Testing NonPreemptiveScheduler with algorithm: FCFS");
        LinkedList<Process> processes = createTestProcesses();
        NonPreemptiveScheduler scheduler = new NonPreemptiveScheduler(processes, "FCFS");
        LinkedList<Process> completedProcesses = scheduler.simulate();
        printProcessCompletionOrder(completedProcesses);
    }

    private static void testSJF() {
        System.out.println("Testing NonPreemptiveScheduler with algorithm: SJF");
        LinkedList<Process> processes = createTestProcesses();
        NonPreemptiveScheduler scheduler = new NonPreemptiveScheduler(processes, "SJF");
        LinkedList<Process> completedProcesses = scheduler.simulate();
        printProcessCompletionOrder(completedProcesses);
    }

    private static void testRoundRobin() {
        System.out.println("Testing NonPreemptiveScheduler with algorithm: RoundRobin");
        LinkedList<Process> processes = createTestProcesses();
        int timeQuantum = 2;
        NonPreemptiveScheduler scheduler = new NonPreemptiveScheduler(processes, "RoundRobin", timeQuantum);
        LinkedList<Process> completedProcesses = scheduler.simulate();
        printProcessCompletionOrder(completedProcesses);
    }

    private static void testPrio() {
        System.out.println("Testing NonPreemptiveScheduler with algorithm: Prio");
        LinkedList<Process> processes = createTestProcesses();
        NonPreemptiveScheduler scheduler = new NonPreemptiveScheduler(processes, "Prio");
        LinkedList<Process> completedProcesses = scheduler.simulate();
        printProcessCompletionOrder(completedProcesses);
    }

    private static LinkedList<Process> createTestProcesses() {
        LinkedList<Process> processes = new LinkedList<>();
        processes.add(new Process(1, 3, 5, 1)); // Process ID, Arrival Time, Burst Time, Priority
        processes.add(new Process(2, 5, 3, 3));
        processes.add(new Process(3, 7, 8, 2));
        processes.add(new Process(4, 9, 6, 4));
        return processes;
    }

    private static void printProcessCompletionOrder(LinkedList<Process> completedProcesses) {
        for (Process process : completedProcesses) {
            System.out.println("Process ID: " + process.getProcessId() + ", Completion Time: " + process.getCompletionTime());
        }
        System.out.println();
    }
}
