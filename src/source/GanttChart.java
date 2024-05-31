package source;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;
import algorithms.Process;

public class GanttChart {
    private LinkedList<ExecutionStep> executionSequence;
    private Map<Process, Color> processColors;
    private int currentStepIndex = 0;
    private Timer timer;
    private TimerTask timerTask;
    private JPanel animationPanel;
    private JScrollPane scrollPane;
    private boolean isTimerStarted = false;
    private JPanel timePanel;

    public GanttChart(JPanel animationPanel, JPanel timePanel, JScrollPane scrollPane, LinkedList<ExecutionStep> executionSequence) {
        this.animationPanel = animationPanel;
        this.executionSequence = executionSequence;
        this.scrollPane = scrollPane;
        this.timePanel = timePanel;
        resetAnimationPanelSize(1030, 200);
        resetScrollPane();
        initializeProcessColors();
        setupTimerTask();
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

    private void setupTimerTask() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
            	
                if (currentStepIndex < executionSequence.size()) {
                	//System.out.println("Started to expand");
                    updateScrollPane();
                    animationPanel.repaint();
                    currentStepIndex++; 
                } else {
                    timer.cancel();
                }
            }
        };
    }

    private void updateScrollPane() {
        int timeUnitWidth = 30;
        int additionalSpaceThreshold = 280;

        // Calculate the drawn width based on actual time units covered
        int drawnWidth = 0;
        if (currentStepIndex > 0) {
            int lastTime = executionSequence.get(currentStepIndex - 1).getTime();
            drawnWidth = (lastTime + 1) * timeUnitWidth;
        }
        
        //System.out.println("Current width: " + (animationPanel.getWidth() - drawnWidth) + " Additional Threshold: " + additionalSpaceThreshold);
        if (animationPanel.getWidth() - drawnWidth < additionalSpaceThreshold) {
        	//System.out.println("New Width: " +drawnWidth + additionalSpaceThreshold);
            animationPanel.setPreferredSize(new Dimension(drawnWidth + additionalSpaceThreshold, animationPanel.getHeight()));
            animationPanel.revalidate();
        }
        
        if (scrollPane != null) {
            SwingUtilities.invokeLater(() -> {
                JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
                horizontalBar.setValue(horizontalBar.getMaximum());
            });
        }
        
    }

    public void startSimulation() {
        if (!isTimerStarted) {
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
            isTimerStarted = true; // Set the flag to true when the timer starts
        }
    } 

    public boolean isTimerStarted() {
        return isTimerStarted;
    }

    public void drawGanttChart(Graphics g) {
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
        
     // Create a bold font
        
     // Clear the area where the current time is drawn in the time panel
        if (timePanel != null) {
            Graphics2D g2d = (Graphics2D) timePanel.getGraphics();
            g2d.setColor(timePanel.getBackground());
            g2d.fillRect(0, 0, timePanel.getWidth(), timePanel.getHeight());
        }

        // Draw the current time in the time panel
        String currentTimeString = "Current Time: " + (currentStepIndex > 0 ? executionSequence.get(currentStepIndex - 1).getTime() : 0);
        if (timePanel != null) {
            Graphics2D g2d = (Graphics2D) timePanel.getGraphics();
            Font boldFont = new Font(g2d.getFont().getName(), Font.BOLD, g2d.getFont().getSize());
            g2d.setFont(boldFont);
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth(currentTimeString);
            g2d.drawString(currentTimeString, timePanel.getWidth() - stringWidth - 50, fm.getHeight() + 6);
        }
    }

    public void resetScrollPane() {
        if (scrollPane != null) {
            SwingUtilities.invokeLater(() -> {
                JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
                horizontalBar.setValue(0); // Set scrollbar position to initial
            });
        }
    }

    private void resetAnimationPanelSize(int width, int height) {
        if (animationPanel != null) {
            animationPanel.setPreferredSize(new Dimension(width, height));
            animationPanel.revalidate();
        }
    }
}
