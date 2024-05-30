package source;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import algorithms.Process;

public class GanttChart {
    private LinkedList<ExecutionStep> executionSequence;
    private Map<Process, Color> processColors;
    private int currentStepIndex = 0;
    private Timer timer;
    private JPanel animationPanel;
    private JScrollPane scrollPane;
    private boolean isTimerStarted = false;

    public GanttChart(JPanel animationPanel, JScrollPane scrollPane, LinkedList<ExecutionStep> executionSequence) {
        this.animationPanel = animationPanel;
        this.executionSequence = executionSequence;
        this.scrollPane = scrollPane;
        initializeProcessColors();
        setupTimer();
    }

    private void initializeProcessColors() {
        processColors = new HashMap<>();
        int numberOfProcesses = executionSequence.stream()
                                                 .map(ExecutionStep::getProcess)
                                                 .distinct()
                                                 .toArray().length;
        
        for (ExecutionStep step : executionSequence) {
            Process process = step.getProcess();
            if (!processColors.containsKey(process)) {
                processColors.put(process, generateUniqueColor(processColors.size(), numberOfProcesses));
            }
        }
    }

    private Color generateUniqueColor(int index, int totalColors) {
        // Generate a unique color by spreading colors evenly in the RGB spectrum
        float hue = (float) index / totalColors;
        return Color.getHSBColor(hue, 0.7f, 0.9f); // Using high saturation and brightness for vibrant colors
    }

    private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Timer");
                if (currentStepIndex < executionSequence.size()) {
                	updateScrollPane();
                    animationPanel.repaint();
                    currentStepIndex++;
                    
                } else {
                    timer.stop();
                }
            }
        });
    }
    
    
    private void updateScrollPane() {
        int timeUnitWidth = 30;
        int additionalSpaceThreshold = 280;
        int drawnWidth = (currentStepIndex + 1) * timeUnitWidth;

        if (animationPanel.getWidth() - drawnWidth < additionalSpaceThreshold) {
            animationPanel.setPreferredSize(new Dimension(drawnWidth + additionalSpaceThreshold, animationPanel.getHeight()));
            animationPanel.revalidate();
        }

        if (scrollPane != null) {
            JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
            horizontalBar.setValue(horizontalBar.getMaximum());
        }
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
        int width = animationPanel.getWidth();
        int height = animationPanel.getHeight();
        int barHeight = 50;
        int timeUnitWidth = 30; // Constant width for each time unit

        int startX = 120;

        for (int i = 0; i < currentStepIndex; i++) {
            ExecutionStep step = executionSequence.get(i);
            Process process = step.getProcess();
            int processStartX = step.getTime() * timeUnitWidth + startX;
            int processLength = timeUnitWidth;
            
            // Draw the process bar
            g.setColor(processColors.get(process));
            g.fillRect(processStartX, height / 2 - barHeight / 2, 30, barHeight);
            g.setColor(Color.BLACK);
            g.drawRect(processStartX, height / 2 - barHeight / 2, 30, barHeight);
            g.drawString(String.valueOf(process.getProcessId()), processStartX + processLength / 2 - 10, height / 2 + 5);
        } 

        // Draw the current time indicator 
        g.setColor(Color.RED);
        if (currentStepIndex > 0) {
            int currentTimeX = executionSequence.get(currentStepIndex - 1).getTime() * timeUnitWidth;
            g.drawLine(currentTimeX + startX, 0, currentTimeX + startX, height); 
        }

        // Draw the current time in the upper right corner
        g.setColor(Color.BLACK);
        String currentTimeString = "Current Time: " + (currentStepIndex > 0 ? executionSequence.get(currentStepIndex - 1).getTime() : 0);
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(currentTimeString);
        g.drawString(currentTimeString, width - stringWidth - 50, fm.getHeight());
    }
}
