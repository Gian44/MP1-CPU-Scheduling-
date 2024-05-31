package source;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainUI extends javax.swing.JFrame {
	
	 // Variables declaration - do not modify                     
    private JLabel AnimationTitle;
    private JButton SimulateBtn;
    private JComboBox<String> ArrivalQueueComboBox;
    private JLabel ArrivalTimeLabel;
    private JTextField ArrivalTimesTxtField;
    private JTextField AverageRT;
    private JLabel AverageRTLabel;
    private JTextField AverageTT;
    private JLabel AverageTTLabel;
    private JTextField AverageWT;
    private JLabel AverageWTLabel;
    private JPanel AveragesPanel;
    private JLabel BurstTimeLabel;
    private JTextField BurstTimeTxtFld;
    private JPanel GeneralPanel;
    private JLabel MultiLvl_Queue_Label;
    private JPanel MultilevelQueuePanel;
    private JComboBox<String> NumOfQueueComboBox;
    private JLabel NumOfQueueLbl;
    private JLabel Num_of_Proc_Label;
    private JLabel PriorityLabel;
    private JTextField PriorityTxtFld;
    private JPanel ProcessPanel;
    private JLabel ProcessInputLabel;
    private JComboBox<String> Queue1AlgoOptions;
    private JLabel Queue1Label;
    private JTextField QueueQuantumInput;
    private JLabel QueueQuantumLbl;
    private JComboBox<String> Queue2AlgoOptions;
    private JLabel Queue2Label;
    //private JTextField Queue2QuantumInput;
    //private JLabel Queue2QuantumLbl;
    private JComboBox<String> Queue3AlgoOptions;
    private JLabel Queue3Label;
    //private JTextField Queue3QuantumInput;
    //private JLabel Queue3QuantumLbl;
    private JButton RandRadioBtn;
    //private JRadioButton UserInputRadioBtn;
    private JPanel animationPanel;
    private JTextField numOfProcessTxtField;
    private static Random randNum;
    private int numberOfProcess;
    private String selectedPrioOrder;
    private int numberOfQueues;
    private String Queue1Algo;
    private int QueueQuantum;
    private String Queue2Algo;
    //private int Queue2Quantum;
    private String Queue3Algo;
    //private int Queue3Quantum;
    private LinkedList<ExecutionStep> outputProcess = new LinkedList<>();
    private GanttChart gChart;
    private JScrollPane jScrollPane1;
    private JPanel TitlePanel;
    private JPanel TimePanel;
    private int roundRobinQueueCount = 0;
    // End of variables declaration  

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();
    }
                       
    private void initComponents() {

        RandRadioBtn = new javax.swing.JButton();
        GeneralPanel = new javax.swing.JPanel();
        ProcessPanel = new javax.swing.JPanel();
        Num_of_Proc_Label = new javax.swing.JLabel();
        ArrivalTimeLabel = new javax.swing.JLabel();
        BurstTimeLabel = new javax.swing.JLabel();
        PriorityLabel = new javax.swing.JLabel();
        PriorityTxtFld = new javax.swing.JTextField();
        BurstTimeTxtFld = new javax.swing.JTextField();
        numOfProcessTxtField = new javax.swing.JTextField();
        ArrivalTimesTxtField = new javax.swing.JTextField();
        ProcessInputLabel = new javax.swing.JLabel();
        MultilevelQueuePanel = new javax.swing.JPanel();
        ArrivalQueueComboBox = new javax.swing.JComboBox<>();
        Queue3Label = new javax.swing.JLabel();
        Queue3AlgoOptions = new javax.swing.JComboBox<>();
        Queue1Label = new javax.swing.JLabel();
        Queue1AlgoOptions = new javax.swing.JComboBox<>();
        Queue2Label = new javax.swing.JLabel();
        Queue2AlgoOptions = new javax.swing.JComboBox<>();
        NumOfQueueLbl = new javax.swing.JLabel();
        NumOfQueueComboBox = new javax.swing.JComboBox<>();
        MultiLvl_Queue_Label = new javax.swing.JLabel();
        QueueQuantumLbl = new javax.swing.JLabel();
        QueueQuantumInput = new javax.swing.JTextField();
        SimulateBtn = new javax.swing.JButton();
        AveragesPanel = new javax.swing.JPanel();
        AverageWTLabel = new javax.swing.JLabel();
        AverageRTLabel = new javax.swing.JLabel();
        AverageTTLabel = new javax.swing.JLabel();
        AverageWT = new javax.swing.JTextField();
        AverageRT = new javax.swing.JTextField();
        AverageTT = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TitlePanel = new javax.swing.JPanel();
        TimePanel = new javax.swing.JPanel();
        
        AnimationTitle = new javax.swing.JLabel();
        randNum = new Random();

        RandRadioBtn.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GeneralPanel.setBackground(new java.awt.Color(51, 51, 51));
        GeneralPanel.setLayout(null);

        ProcessPanel.setBackground(new java.awt.Color(255, 255, 255));
        ProcessPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ProcessPanel.setLayout(null);

        RandRadioBtn.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        RandRadioBtn.setSelected(true);
        RandRadioBtn.setText("Random ");
        RandRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandRadioBtnActionPerformed(evt);
            }
        });
        
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gChart != null && gChart.isTimerStarted()) {
                    gChart.drawGanttChart(g);
                }
            } 
        }; 
        ProcessPanel.add(RandRadioBtn);
        RandRadioBtn.setBounds(35, 35, 79, 20); 
        RandRadioBtn.setSelected(false);

        Num_of_Proc_Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Num_of_Proc_Label.setText("Number of Process: ");
        ProcessPanel.add(Num_of_Proc_Label);
        Num_of_Proc_Label.setBounds(20, 70, 110, 14);

        ArrivalTimeLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ArrivalTimeLabel.setText("Arrival Times:");
        ProcessPanel.add(ArrivalTimeLabel);
        ArrivalTimeLabel.setBounds(20, 100, 80, 30);

        BurstTimeLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BurstTimeLabel.setText("Burst Times:");
        ProcessPanel.add(BurstTimeLabel);
        BurstTimeLabel.setBounds(20, 140, 70, 30);

        PriorityLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        PriorityLabel.setText("Priority:");
        ProcessPanel.add(PriorityLabel);
        PriorityLabel.setBounds(20, 180, 50, 14);

        PriorityTxtFld.setText("");
        ProcessPanel.add(PriorityTxtFld);
        PriorityTxtFld.setBounds(130, 178, 140, 23);

        BurstTimeTxtFld.setText("");
        ProcessPanel.add(BurstTimeTxtFld);
        BurstTimeTxtFld.setBounds(130, 143, 140, 23);

        numOfProcessTxtField.setText("1");
        numOfProcessTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numOfProcessTxtFieldActionPerformed(evt);
            } 
        });
        numOfProcessTxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                numOfProcessTxtFieldKeyTyped(evt);
            }
        });
        ProcessPanel.add(numOfProcessTxtField);
        numOfProcessTxtField.setBounds(130, 68, 140, 23);

        ArrivalTimesTxtField.setText("");
        ArrivalTimesTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrivalTimesTxtFieldActionPerformed(evt);
            }
        });
        ArrivalTimesTxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ArrivalTimesTxtFieldKeyTyped(evt);
            }
        });
        ProcessPanel.add(ArrivalTimesTxtField);
        ArrivalTimesTxtField.setBounds(130, 104, 140, 23);

        ProcessInputLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ProcessInputLabel.setText("Process Input");
        ProcessPanel.add(ProcessInputLabel); 
        ProcessInputLabel.setBounds(100, 10, 90, 16);


        GeneralPanel.add(ProcessPanel);
        ProcessPanel.setBounds(10, 250, 290, 320);

        MultilevelQueuePanel.setBackground(new java.awt.Color(255, 255, 255));
        MultilevelQueuePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MultilevelQueuePanel.setLayout(null);

        Queue3Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Queue3Label.setText("Queue 3:");
        Queue3Label.setOpaque(false); 
        //MultilevelQueuePanel.add(Queue3Label);
        Queue3Label.setBounds(70, 160, 60, 14);

        Queue3AlgoOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Priority (Preemptive)", "Priority (Non-preemptive)", "Round Robin" }));
        ActionEvent initialEvent3 = new ActionEvent(Queue3AlgoOptions, ActionEvent.ACTION_PERFORMED, "");
        Queue3AlgoOptionsActionPerformed(initialEvent3);
        //MultilevelQueuePanel.add(Queue3AlgoOptions);
        Queue3AlgoOptions.setBounds(140, 160, 191, 22);
        Queue3AlgoOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue3AlgoOptionsActionPerformed(evt);
            }
        });
        Queue3AlgoOptions.setOpaque(false);

        Queue1Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Queue1Label.setText("Queue 1:");
        MultilevelQueuePanel.add(Queue1Label);
        Queue1Label.setBounds(75, 80, 50, 14);

        Queue1AlgoOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Priority (Preemptive)", "Priority (Non-preemptive)", "Round Robin" }));
        ActionEvent initialEvent1 = new ActionEvent(Queue1AlgoOptions, ActionEvent.ACTION_PERFORMED, "");
        Queue1AlgoOptionsActionPerformed(initialEvent1);
        Queue1AlgoOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue1AlgoOptionsActionPerformed(evt);
            }
        });
        MultilevelQueuePanel.add(Queue1AlgoOptions);
        Queue1AlgoOptions.setBounds(140, 78, 191, 22);

        Queue2Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Queue2Label.setText("Queue 2:");
        Queue2Label.setOpaque(false); 
        //MultilevelQueuePanel.add(Queue2Label);
        Queue2Label.setBounds(70, 120, 60, 14);

        Queue2AlgoOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Priority (Preemptive)", "Priority (Non-preemptive)", "Round Robin" }));
        ActionEvent initialEvent2 = new ActionEvent(Queue2AlgoOptions, ActionEvent.ACTION_PERFORMED, "");
        Queue2AlgoOptionsActionPerformed(initialEvent2);
        Queue2AlgoOptions.setOpaque(false); 
        Queue2AlgoOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue2AlgoOptionsActionPerformed(evt);
            }
        });
        //MultilevelQueuePanel.add(Queue2AlgoOptions);
        Queue2AlgoOptions.setBounds(140, 120, 191, 22);

        NumOfQueueLbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        NumOfQueueLbl.setText("Number of Queues:");
        MultilevelQueuePanel.add(NumOfQueueLbl);
        NumOfQueueLbl.setBounds(20, 40, 110, 14);

        NumOfQueueComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        ActionEvent initialEvent = new ActionEvent(NumOfQueueComboBox, ActionEvent.ACTION_PERFORMED, "");
        NumOfQueueComboBoxActionPerformed(initialEvent);
        NumOfQueueComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumOfQueueComboBoxActionPerformed(evt);
            }
        });
        MultilevelQueuePanel.add(NumOfQueueComboBox);
        NumOfQueueComboBox.setBounds(140, 38, 72, 22); 

        MultiLvl_Queue_Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        MultiLvl_Queue_Label.setText("Multilevel Queue");
        MultilevelQueuePanel.add(MultiLvl_Queue_Label);
        MultiLvl_Queue_Label.setBounds(200, 10, 100, 14);

        QueueQuantumLbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        QueueQuantumLbl.setText("Quantum: ");
        QueueQuantumLbl.setOpaque(false);
        //MultilevelQueuePanel.add(QueueQuantumLbl);
        QueueQuantumLbl.setBounds(70, 200, 60, 14);

        QueueQuantumInput.setText("1");
        QueueQuantumInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QueueQuantumInputActionPerformed(evt);
            }
        });
        QueueQuantumInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                QueueQuantumInputKeyTyped(evt);
            }
        });
        //MultilevelQueuePanel.add(QueueQuantumInput);
        QueueQuantumInput.setBounds(140, 200, 70, 23);

        SimulateBtn.setForeground(new java.awt.Color(51, 51, 51));
        SimulateBtn.setText("Simulate");
        SimulateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimulateBtnActionPerformed(evt);
            }
        });
        MultilevelQueuePanel.add(SimulateBtn);
        SimulateBtn.setBounds(210, 280, 80, 23);

        GeneralPanel.add(MultilevelQueuePanel);
        MultilevelQueuePanel.setBounds(310, 250, 510, 320);

        AveragesPanel.setBackground(new java.awt.Color(255, 255, 255));
        AveragesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        AveragesPanel.setLayout(null);

        AverageWTLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        AverageWTLabel.setText("Average Waiting Time:");
        AveragesPanel.add(AverageWTLabel);
        AverageWTLabel.setBounds(40, 15, 130, 14);

        AverageRTLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        AverageRTLabel.setText("Average Response Time:");
        AveragesPanel.add(AverageRTLabel);
        AverageRTLabel.setBounds(30, 95, 140, 14);

        AverageTTLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        AverageTTLabel.setText("Average Turnaround Time:");
        AveragesPanel.add(AverageTTLabel);
        AverageTTLabel.setBounds(30, 175, 150, 14);

        AverageWT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AverageWT.setText("0");
        AverageWT.setEditable(false);
        AveragesPanel.add(AverageWT);
        AverageWT.setBounds(60, 45, 80, 22);

        AverageRT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AverageRT.setText("0");
        AverageRT.setEditable(false);
        
        AveragesPanel.add(AverageRT);
        AverageRT.setBounds(60, 125, 80, 22);

        AverageTT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AverageTT.setText("0");
        AverageTT.setEditable(false);
        AveragesPanel.add(AverageTT);
        AverageTT.setBounds(60, 205, 80, 22);

        GeneralPanel.add(AveragesPanel);
        AveragesPanel.setBounds(830, 250, 210, 320);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        AnimationTitle.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        AnimationTitle.setText("Gantt Chart of Process Execution");
        //animationPanel.setLayout(new OverlayLayout(animationPanel));
        //animationPanel.add(AnimationTitle);
        
        javax.swing.GroupLayout TitlePanelLayout = new javax.swing.GroupLayout(TitlePanel);
        TitlePanel.setLayout(TitlePanelLayout);
        TitlePanel.setBackground(MultilevelQueuePanel.getBackground());
        TitlePanelLayout.setHorizontalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addGap(388, 388, 388)
                .addComponent(AnimationTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(402, Short.MAX_VALUE))
        );
        TitlePanelLayout.setVerticalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AnimationTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(animationPanel);
        jScrollPane1.setBackground(MultilevelQueuePanel.getBackground());

        GeneralPanel.add(jScrollPane1);
        jScrollPane1.setBounds(10, 50, 1030, 200);
        
        GeneralPanel.add(TitlePanel);
        TitlePanel.setBounds(12, 10, 840, 40);
        
        javax.swing.GroupLayout TimePanelLayout = new javax.swing.GroupLayout(TimePanel);
        TimePanel.setLayout(TimePanelLayout);
        TimePanel.setBackground(MultilevelQueuePanel.getBackground());
        TimePanelLayout.setHorizontalGroup(
            TimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );
        TimePanelLayout.setVerticalGroup(
            TimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        GeneralPanel.add(TimePanel);
        TimePanel.setBounds(850, 10, 188, 40);
        
        javax.swing.GroupLayout animationPanelLayout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(animationPanelLayout);
        animationPanel.add(AnimationTitle);
        animationPanelLayout.setHorizontalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animationPanelLayout.createSequentialGroup()
                .addGap(382, 382, 382)
                .addComponent(AnimationTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(406, Short.MAX_VALUE))
        );
        animationPanelLayout.setVerticalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AnimationTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GeneralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1047, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GeneralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        pack();
    }
      
     
    /*********    ActionListener Events     ***************/
    
    private void RandRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {                                             
    	
            
         int randomNumber = randNum.nextInt(20) + 1;
            numberOfProcess = randomNumber;
            numOfProcessTxtField.setText(Integer.toString(randomNumber));
            
            generateRandomNumbers(0, randomNumber, ArrivalTimesTxtField);
            generateRandomNumbers(1, randomNumber, BurstTimeTxtFld);
            generateRandomNumbers(1,randomNumber, PriorityTxtFld);
    }  
     
    private void numOfProcessTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        
    } 
     
    private void numOfProcessTxtFieldKeyTyped(KeyEvent evt) {
    	char c = evt.getKeyChar();
        String currentText = numOfProcessTxtField.getText();
    
        if (c == KeyEvent.VK_BACK_SPACE) {
            if (!currentText.isEmpty()) {
                // Allow backspace if the text field is not empty
            	numOfProcessTxtField.setText(currentText.substring(0, currentText.length() - 1));
                evt.consume();
            }
        } else if (!Character.isDigit(c) || currentText.length() >= 2) {
            evt.consume(); // Consume invalid input
        } else {
            if (currentText.equals("0")) {
                // If the current text is "0", replace it with the typed digit
                currentText = "";
            }
    
            String newText = currentText + c;
            try {
                int value = Integer.parseInt(newText);
                if (value < 1 || value > 15) {
                    evt.consume(); // Consume input if the entered number is out of range
                } else {
                	numOfProcessTxtField.setText(newText);
                    numberOfProcess = value;
                    evt.consume(); // Consume the event to prevent default behavior
                }
            } catch (NumberFormatException e) {
                evt.consume(); // Consume input if the entered value is not a valid number
            }
        }

	}
         
    private void ArrivalTimesTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        
    }  
    
    private void ArrivalTimesTxtFieldKeyTyped(KeyEvent evt) {
		
	}
    
    

    
    private void handleAlgorithmSelection(String selectedAlgorithm) {
        

        if (selectedAlgorithm.equals("Round Robin")) {
            roundRobinQueueCount++;
            System.out.println("RoundRob: "+roundRobinQueueCount);
        }else{
            if(roundRobinQueueCount > 0){
                roundRobinQueueCount--;
                System.out.println("RoundRob: "+roundRobinQueueCount);
            }
        }
    
        if (roundRobinQueueCount > 0) {
            if (!MultilevelQueuePanel.isAncestorOf(QueueQuantumLbl)) {
                MultilevelQueuePanel.add(QueueQuantumLbl);
            }
            if (!MultilevelQueuePanel.isAncestorOf(QueueQuantumInput)) {
                MultilevelQueuePanel.add(QueueQuantumInput);
            }
        } else {
            MultilevelQueuePanel.remove(QueueQuantumLbl);
            MultilevelQueuePanel.remove(QueueQuantumInput);
        }
    
        revalidate();
        repaint();
    }
                                                   
    private void Queue1AlgoOptionsActionPerformed(ActionEvent evt) {
		String choseNalgo = (String) Queue1AlgoOptions.getSelectedItem();
        Queue1Algo = choseNalgo;
        handleAlgorithmSelection(choseNalgo);
	}
    private void QueueQuantumInputActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        
    } 
    private void QueueQuantumInputKeyTyped(KeyEvent evt) {
    	char c = evt.getKeyChar();
        String currentText = QueueQuantumInput.getText();
    
        if (c == KeyEvent.VK_BACK_SPACE) {
            if (!currentText.isEmpty()) {
                // Allow backspace if the text field is not empty
                QueueQuantumInput.setText(currentText.substring(0, currentText.length() - 1));
                evt.consume();
            }
        } else if (!Character.isDigit(c) || currentText.length() >= 2) {
            evt.consume(); // Consume invalid input
        } else {
            if (currentText.equals("0")) {
                // If the current text is "0", replace it with the typed digit
                currentText = "";
            }
    
            String newText = currentText + c;
            try {
                int value = Integer.parseInt(newText);
                if (value < 1 || value > 15) {
                    evt.consume(); // Consume input if the entered number is out of range
                } else {
                    QueueQuantumInput.setText(newText);
                    QueueQuantum = value;
                    evt.consume(); // Consume the event to prevent default behavior
                }
            } catch (NumberFormatException e) {
                evt.consume(); // Consume input if the entered value is not a valid number
            }
        }
    }
    
    
    private void Queue2AlgoOptionsActionPerformed(ActionEvent evt) {
		String choseNalgo = (String) Queue2AlgoOptions.getSelectedItem();
        Queue2Algo = choseNalgo;
        handleAlgorithmSelection(choseNalgo);
	}

   
    private void Queue3AlgoOptionsActionPerformed(ActionEvent evt) {
		String choseNalgo = (String) Queue3AlgoOptions.getSelectedItem();
        Queue3Algo = choseNalgo;
        handleAlgorithmSelection(choseNalgo);
	}
    
    private void NumOfQueueComboBoxActionPerformed(ActionEvent evt) {
        ArrivalQueueComboBox.removeAllItems(); // Clear existing items

        // Add options to comboBox2 based on the selected value of comboBox1
        int selectedValue = Integer.parseInt((String) NumOfQueueComboBox.getSelectedItem());
        numberOfQueues = selectedValue;
        System.out.println("Number of Queues: "+numberOfQueues);
        for (int i = 1; i <= selectedValue; i++) {
            ArrivalQueueComboBox.addItem(Integer.toString(i));
        }
        if(selectedValue == 1) { 
            MultilevelQueuePanel.remove(Queue2Label);
            MultilevelQueuePanel.remove(Queue2AlgoOptions);
            MultilevelQueuePanel.remove(Queue3Label);
            MultilevelQueuePanel.remove(Queue3AlgoOptions);
        }
        else if(selectedValue == 2) {
            MultilevelQueuePanel.add(Queue2Label);
            MultilevelQueuePanel.add(Queue2AlgoOptions);
            MultilevelQueuePanel.remove(Queue3Label);
            MultilevelQueuePanel.remove(Queue3AlgoOptions);
            
        }else if(selectedValue == 3) {
            MultilevelQueuePanel.add(Queue2Label);
            MultilevelQueuePanel.add(Queue2AlgoOptions);
            MultilevelQueuePanel.add(Queue3Label);
            MultilevelQueuePanel.add(Queue3AlgoOptions);
        }
        revalidate();
        repaint();
    } 
    
    public static void generateRandomNumbers(int start, int count, javax.swing.JTextField txtfield) {

        txtfield.setText("");
        for (int i = 0; i < count; i++) {
            int randomNum = randNum.nextInt(count - start + 1) + start; // Generates random number between 0 and 30
            txtfield.setText(txtfield.getText()+randomNum+" ");
        } 
    }

	private void SimulateBtnActionPerformed(ActionEvent evt) { 
		System.out.println(numberOfProcess);
        ArrivalTimesTxtField.setInputVerifier(new NumberInputVerifier(0, 20, numberOfProcess, true));
        BurstTimeTxtFld.setInputVerifier(new NumberInputVerifier(1, 20, numberOfProcess, false));
        PriorityTxtFld.setInputVerifier(new NumberInputVerifier(1, 20, numberOfProcess, false));

        boolean arrivalValid = ArrivalTimesTxtField.getInputVerifier().verify(ArrivalTimesTxtField);
        boolean burstValid = BurstTimeTxtFld.getInputVerifier().verify(BurstTimeTxtFld);
        boolean priorityValid = PriorityTxtFld.getInputVerifier().verify(PriorityTxtFld);
        
            if (arrivalValid && burstValid && priorityValid) {
            	CPUScheduler cpuSched = new CPUScheduler(this);
                outputProcess = cpuSched.getOrderedProcesses();
                gChart = new GanttChart(getAnimationPanel(), getTimePanel(), getScrollPane(), outputProcess);
                
                // Disable the simulate button
                SimulateBtn.setEnabled(false);
                
                // Add a listener to enable the button when drawing is complete
                gChart.addPropertyChangeListener(event -> { // Renamed the lambda parameter to 'event'
                    if ("drawingComplete".equals(event.getPropertyName())) {
                        SimulateBtn.setEnabled(true);
                    }
                });
                
                gChart.startSimulation();
            } 
            else {
                JOptionPane.showMessageDialog(this, "Mismatch or invalid entries in one of the fields. \n Arrival Time (0-20) only separated by spaces. \n Burst Time (1-20) only separated by spaces. \n Priority (1-20) only separated by spaces \n Arrival Valid: "+arrivalValid+ " Burst Valid: "+burstValid+" Priority Valid: "+priorityValid);
            }		 
	}  
    
    
    //getter methods
    public int getNumofProcess(){
        return numberOfProcess;
    }
    public String getArrivalTime(){
        return ArrivalTimesTxtField.getText();
    }
    public String getBurstTime(){
        return BurstTimeTxtFld.getText();
    }
    public String getPriority(){
        return PriorityTxtFld.getText();
    }
    public String getPrioOrder(){
        return selectedPrioOrder;
    }
    public int getNumOfQueues(){
        return numberOfQueues;
    }
    public String getQueue1Algo(){
        return Queue1Algo;
    }
    public int getQueueQuantum(){
        return QueueQuantum;
    }
    public String getQueue2Algo(){
        return Queue2Algo;
    }
    public String getQueue3Algo(){
        return Queue3Algo;
    }
 
    public JPanel getAnimationPanel() {
    	return animationPanel;
    }
    
    public JScrollPane getScrollPane() {
    	return jScrollPane1;
    }
    
    public JTextField getAverageRT() {
    	return AverageRT;
    }
    public JTextField getAverageWT() {
    	return AverageWT;
    }
    public JTextField getAverageTT() {
    	return AverageTT;
    }
    public JPanel getTimePanel() {
    	return TimePanel;
    }
}

class NumberInputVerifier extends InputVerifier {
    private final int minValue;
    private final int maxValue;
    private final int maxEntries;
    private final boolean allowZero;

    public NumberInputVerifier(int minValue, int maxValue, int maxEntries, boolean allowZero) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.maxEntries = maxEntries;
        this.allowZero = allowZero;
    }

    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            String text = ((JTextField) input).getText();
            return isValid(text);
        }
        return false;
    }

    private boolean isValid(String text) {
        if (text.isEmpty()) return false;
        String[] parts = text.split("\\s+");
        if (parts.length > maxEntries) return false;

        for (String part : parts) {
            if (!part.isEmpty()) {
                try {
                    int value = Integer.parseInt(part);
                    if (value < minValue || value > maxValue || (value == 0 && !allowZero)) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }
}


