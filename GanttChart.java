

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import algorithms.Process;

public class GanttChart {
    private LinkedList<Process> processes;
    private Map<Process, Color> processColors;
    private int currentTime = 0;
    private int currentProcessIndex = 0;
    private Timer timer;
    private JPanel animationPanel;
    private boolean isTimerStarted = false;

    public GanttChart(JPanel animationPanel, LinkedList<Process> processes) {
        this.animationPanel = animationPanel;
        this.processes = processes;
        initializeProcessColors();
        setupTimer();
    }

    private void initializeProcessColors() {
        processColors = new HashMap<>();
        Random random = new Random();
        for (Process process : processes) {
            // Create lighter colors
            int red = 200 + random.nextInt(56);   // Values between 200 and 255
            int green = 200 + random.nextInt(56); // Values between 200 and 255
            int blue = 200 + random.nextInt(56);  // Values between 200 and 255
            processColors.put(process, new Color(red, green, blue));
        }
    }

    private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProcessIndex < processes.size()) {
                    currentTime++;
                    animationPanel.repaint();
                    System.out.println("Current Time: " + currentTime);
                    System.out.println("Current Process Index: " + currentProcessIndex);
                } else {
                    System.out.println("Stopped");
                    timer.stop();
                }
            }
        });
    }

    public void startSimulation() {
        if (!isTimerStarted) {
            timer.start();
            isTimerStarted = true; // Set the flag to true when the timer starts
        }
    }

    public boolean isTimerStarted() {
        return isTimerStarted;
    }

    public void drawGanttChart(Graphics g) {
        System.out.println("Entered Graphics");
        int width = animationPanel.getWidth();
        int height = animationPanel.getHeight();
        int barHeight = 50;
        int timeUnitWidth = 30; // Constant width for each time unit

        int startX =0;

        for (int i = 0; i < currentProcessIndex; i++) {
            Process process = processes.get(i);
            int processStartX = process.getArrivalTime() * timeUnitWidth + startX;
            int processLength = timeUnitWidth;
            System.out.println("Value of i: " + i);
            System.out.println("Process ID: " + process.getProcessId());
            System.out.println("ProcessStartX: " + processStartX);
            System.out.println("ProcessLength: " + processLength);
            System.out.println();

            // Draw the process bar
            g.setColor(processColors.get(process));
            g.fillRect(processStartX, height / 2 - barHeight / 2, processLength, barHeight);
            g.setColor(Color.BLACK);
            g.drawRect(processStartX, height / 2 - barHeight / 2, processLength, barHeight);
            g.drawString(process.getProcessId(), processStartX + processLength / 2 - 10, height / 2 + 5);

            // Update the starting X coordinate for the next process
            startX += processLength;
        }

        // Draw the current time indicator
        g.setColor(Color.RED);
        int currentTimeX = currentTime * timeUnitWidth;
        g.drawLine(currentTimeX, 0, currentTimeX, height);

        // Draw the current time in the upper right corner
        g.setColor(Color.BLACK);
        String currentTimeString = "Current Time: " + currentTime;
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(currentTimeString);
        g.drawString(currentTimeString, width - stringWidth - 10, fm.getHeight());

        // Draw the next process bar if it's time to do so
        if (currentProcessIndex < processes.size()) {
            Process process = processes.get(currentProcessIndex);
            if (currentTime >= process.getArrivalTime()) {
                int processStartX = process.getArrivalTime() * timeUnitWidth;
                int processLength = timeUnitWidth;
                System.out.println("Drawing Process ID: " + process.getProcessId());
                System.out.println("ProcessStartX: " + processStartX);
                System.out.println("ProcessLength: " + processLength);

                // Draw the process bar
                g.setColor(processColors.get(process));
                g.fillRect(processStartX, height / 2 - barHeight / 2, processLength, barHeight);
                g.setColor(Color.BLACK);
                g.drawRect(processStartX, height / 2 - barHeight / 2, processLength, barHeight);
                g.drawString(process.getProcessId(), processStartX + processLength / 2 - 10, height / 2 + 5);

                // Update the starting X coordinate for the next process
                //startX += processLength;

                // Move to the next process
                currentProcessIndex++;
            }
        }
    }
}

