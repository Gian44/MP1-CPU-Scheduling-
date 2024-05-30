package src.source;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import src.source.GanttChart;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainUI extends javax.swing.JFrame {
	
	 // Variables declaration - do not modify                     
    private JLabel AnimationTitle;
    private JButton SimulateBtn;
    private JComboBox<String> ArrivalQueueComboBox;
    private JLabel ArrivalQueueLbl;
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
    private JTextField Queue1QuantumInput;
    private JLabel Queue1QuantumLbl;
    private JComboBox<String> Queue2AlgoOptions;
    private JLabel Queue2Label;
    private JTextField Queue2QuantumInput;
    private JLabel Queue2QuantumLbl;
    private JComboBox<String> Queue3AlgoOptions;
    private JLabel Queue3Label;
    private JTextField Queue3QuantumInput;
    private JLabel Queue3QuantumLbl;
    private JRadioButton RandRadioBtn;
    //private JRadioButton UserInputRadioBtn;
    private JPanel animationPanel;
    private JTextField numOfProcessTxtField;
    private JComboBox<String> PrioOrder;
    private static Random randNum;
    private int numberOfProcess;
    private String selectedPrioOrder;
    private int numberOfQueues;
    private String Queue1Algo;
    private int Queue1Quantum;
    private String Queue2Algo;
    private int Queue2Quantum;
    private String Queue3Algo;
    private int Queue3Quantum;
    private CPUScheduler cpuScheduler;
    private LinkedList<Process> outputProcess = new LinkedList<>();
    private GanttChart gChart;
    
    // End of variables declaration  

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();
    }
                       
    private void initComponents() { 

        GeneralPanel = new JPanel();
        
        AnimationTitle = new JLabel();
        SimulateBtn = new JButton();
        ProcessPanel = new JPanel();
        ProcessInputLabel = new JLabel();
        RandRadioBtn = new JRadioButton();
        Num_of_Proc_Label = new JLabel();
        ArrivalTimeLabel = new JLabel();
        BurstTimeLabel = new JLabel();
        PriorityLabel = new JLabel();
        PriorityTxtFld = new JTextField();
        BurstTimeTxtFld = new JTextField();
        numOfProcessTxtField = new JTextField();
        ArrivalTimesTxtField = new JTextField();
        MultilevelQueuePanel = new JPanel();
        ArrivalQueueComboBox = new JComboBox<>();
        MultiLvl_Queue_Label = new JLabel();
        ArrivalQueueLbl = new JLabel();
        Queue3Label = new JLabel();
        Queue3AlgoOptions = new JComboBox<>();
        Queue1Label = new JLabel();
        Queue1AlgoOptions = new JComboBox<>();
        Queue2Label = new JLabel();
        Queue2AlgoOptions = new JComboBox<>();
        Queue3QuantumLbl = new JLabel();
        Queue3QuantumInput = new JTextField();
        Queue1QuantumLbl = new JLabel();
        Queue1QuantumInput = new JTextField();
        Queue2QuantumLbl = new JLabel();
        Queue2QuantumInput = new JTextField();
        NumOfQueueLbl = new JLabel();
        NumOfQueueComboBox = new JComboBox<>();
        AveragesPanel = new JPanel();
        AverageWTLabel = new JLabel();
        AverageRTLabel = new JLabel();
        AverageTTLabel = new JLabel();
        AverageWT = new JTextField();
        AverageRT = new JTextField();
        AverageTT = new JTextField();
        randNum = new Random();
        PrioOrder = new JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        //General Panel
        GeneralPanel.setBackground(new java.awt.Color(51, 51, 51));
        GeneralPanel.setLayout(null);
        
        
        
        //Panel for Gantt Chart
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gChart != null && gChart.isTimerStarted()) {
                    gChart.drawGanttChart(g);
                }
            }
        };
        JScrollPane scrollPane = new JScrollPane(animationPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        // Add scroll pane to the content pane of the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        animationPanel.setBackground(new java.awt.Color(255, 255, 255));
        animationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        animationPanel.setLayout(null);

        AnimationTitle.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        AnimationTitle.setText("Gantt Chart of Process Execution");
        animationPanel.add(AnimationTitle);
        AnimationTitle.setBounds(400, 0, 240, 20);
        
        SimulateBtn.setForeground(new java.awt.Color(51, 51, 51));
        SimulateBtn.setText("Simulate");
        SimulateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimulateBtnActionPerformed(evt);
            }

        });
        animationPanel.add(SimulateBtn);
        SimulateBtn.setBounds(940, 200, 80, 23);

        GeneralPanel.add(animationPanel); 
        animationPanel.setBounds(10, 10, 1030, 230);
        //End of the current Panel
        //Should include adding the graphics for Gantt Chart
        
        //Process Panel
        //Panel for Process Input
        //Either random or user input
        ProcessPanel.setBackground(new java.awt.Color(255, 255, 255));
        ProcessPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ProcessPanel.setLayout(null);
        
        //Process Panel Title
        ProcessInputLabel.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        ProcessInputLabel.setText("Process Input"); 
        ProcessPanel.add(ProcessInputLabel);
        ProcessInputLabel.setBounds(100, 10, 120, 16);
        //end
        
        //Random Radio Button
        RandRadioBtn.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        RandRadioBtn.setSelected(false);
        RandRadioBtn.setText("Random ");
        RandRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandRadioBtnActionPerformed(evt);
            }
        }); 
        ProcessPanel.add(RandRadioBtn);  
        RandRadioBtn.setBounds(50, 35, 90, 20);
        //end
         
        
        //
        //Number of Process 
        //
        Num_of_Proc_Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Num_of_Proc_Label.setText("Number of Process: ");
        ProcessPanel.add(Num_of_Proc_Label);
        Num_of_Proc_Label.setBounds(20, 70, 110, 14);
        
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
        numOfProcessTxtField.setEditable(false);
        ProcessPanel.add(numOfProcessTxtField);
        numOfProcessTxtField.setBounds(130, 68, 140, 23);
        //end
        
        
        //
        //Arrival Times
        //
        ArrivalTimeLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ArrivalTimeLabel.setText("Arrival Times:");
        ProcessPanel.add(ArrivalTimeLabel);
        ArrivalTimeLabel.setBounds(20, 100, 80, 30);
        
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
        //end
        
        
        //
        //Burst Times
        //
        BurstTimeLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BurstTimeLabel.setText("Burst Times:");
        ProcessPanel.add(BurstTimeLabel);
        BurstTimeLabel.setBounds(20, 140, 70, 30);
        
        BurstTimeTxtFld.setText("");
        BurstTimeTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	BurstTimeTxtFldActionPerformed(evt);
            }
        });
        ProcessPanel.add(BurstTimeTxtFld);
        BurstTimeTxtFld.setBounds(130, 143, 140, 23);
        //end
        
        
        //
        //Priority Numbers
        //
        PriorityLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        PriorityLabel.setText("Priority:");
        ProcessPanel.add(PriorityLabel);
        PriorityLabel.setBounds(20, 180, 50, 14);
        
        PriorityTxtFld.setText("");
        PriorityTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	PriorityTxtFldActionPerformed(evt);
            }
        });
        ProcessPanel.add(PriorityTxtFld);
        PriorityTxtFld.setBounds(130, 178, 140, 23);

        PrioOrder.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lowest First", "Highest First" }));
        PrioOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrioOrderActionPerformed(evt);
            }
        });
        ProcessPanel.add(PrioOrder);
        PrioOrder.setBounds(130, 210, 140, 22);

        GeneralPanel.add(ProcessPanel);
        ProcessPanel.setBounds(10, 250, 290, 320);
        //end
        
        GeneralPanel.add(ProcessPanel); //add the Process Panel to the General Panel
        ProcessPanel.setBounds(10, 250, 290, 320);
        //end of Process Panel
        
        
        
        //Multilevel Queue Panel
        MultilevelQueuePanel.setBackground(new java.awt.Color(255, 255, 255));
        MultilevelQueuePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MultilevelQueuePanel.setLayout(null);
        
        MultiLvl_Queue_Label.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        MultiLvl_Queue_Label.setText("Multilevel Queue");
        MultilevelQueuePanel.add(MultiLvl_Queue_Label);
        MultiLvl_Queue_Label.setBounds(220, 10, 120, 14); 
        
        //Number of Queues
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
        NumOfQueueComboBox.setBounds(140, 34, 72, 22);
        //Number of Queues

        //Choosing Arrival Queue
        ArrivalQueueComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"1"}));
        MultilevelQueuePanel.add(ArrivalQueueComboBox);
        ArrivalQueueComboBox.setBounds(410, 34, 72, 22);

        ArrivalQueueLbl.setFont(new java.awt.Font("Arial", 0, 12)); 
        ArrivalQueueLbl.setText("Arrival Queue:");
        MultilevelQueuePanel.add(ArrivalQueueLbl);
        ArrivalQueueLbl.setBounds(310, 40, 90, 14);
        //end
        
        //Queue1
        Queue1Label.setFont(new java.awt.Font("Arial", 0, 12));
        Queue1Label.setText("Queue 1:");
        MultilevelQueuePanel.add(Queue1Label);
        Queue1Label.setBounds(75, 80, 50, 14);

        Queue1AlgoOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Priority (Preemptive)", "Priority (Non-preemptive)", "Round Robin" }));
        Queue1AlgoOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue1AlgoOptionsActionPerformed(evt);
            }
        });
        MultilevelQueuePanel.add(Queue1AlgoOptions);
        Queue1AlgoOptions.setBounds(140, 74, 198, 22);
      
        Queue1QuantumLbl.setFont(new java.awt.Font("Arial", 0, 12)); 
        Queue1QuantumLbl.setText("Quantum: ");
        
        Queue1QuantumLbl.setBounds(70, 110, 60, 14); 
        Queue1QuantumInput.setText("1");
        Queue1QuantumInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue1QuantumInputActionPerformed(evt);
            }
        }); 
        Queue1QuantumInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Queue1QuantumInputKeyTyped(evt);
            }
        });
        Queue1QuantumInput.setBounds(140, 104, 70, 23);
        Queue1QuantumInput.getAccessibleContext().setAccessibleName("");
        //Queue1

        //Queue 2
        Queue2Label.setFont(new java.awt.Font("Arial", 0, 12));  
        Queue2Label.setText("Queue 2:");
        Queue2Label.setOpaque(false); 
        Queue2Label.setBounds(75, 150, 60, 14);

        Queue2AlgoOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Priority (Preemptive)", "Priority (Non-preemptive)", "Round Robin" }));
        Queue2AlgoOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue2AlgoOptionsActionPerformed(evt);
            }
        });
        Queue2AlgoOptions.setBounds(140, 144, 198, 22);
        
        Queue2QuantumLbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Queue2QuantumLbl.setText("Quantum: ");
        Queue2QuantumLbl.setBounds(70, 180, 60, 14);

        Queue2QuantumInput.setText("1");
        Queue2QuantumInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue2QuantumInputActionPerformed(evt);
            }
        });
        Queue2QuantumInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Queue2QuantumInputKeyTyped(evt);
            }
        });
        Queue2QuantumInput.setBounds(140, 174, 70, 23);
        Queue2QuantumInput.getAccessibleContext().setAccessibleName("");
        //Queue 2
         
        //Queue 3
        Queue3Label.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Queue3Label.setText("Queue 3:");
        Queue3Label.setBounds(75, 220, 60, 14);

        Queue3AlgoOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Priority (Preemptive)", "Priority (Non-preemptive)", "Round Robin" }));
        Queue3AlgoOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue3AlgoOptionsActionPerformed(evt);
            }
        }); 
        Queue3AlgoOptions.setBounds(140, 214, 198, 22);

        Queue3QuantumLbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Queue3QuantumLbl.setText("Quantum: ");
        Queue3QuantumLbl.setBounds(70, 250, 60, 14);

        Queue3QuantumInput.setText("1");
        Queue3QuantumInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Queue3QuantumInputActionPerformed(evt);
            }
        });
        Queue3QuantumInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Queue3QuantumInputKeyTyped(evt);
            }
        }); 
        Queue3QuantumInput.setBounds(140, 244, 70, 23);
        //end of Queue 3

        
        GeneralPanel.add(MultilevelQueuePanel);
        MultilevelQueuePanel.setBounds(310, 250, 510, 320);
        
        //End of Multilevel Queue Panel
        
        //Time averages Panel
        AveragesPanel.setBackground(new java.awt.Color(255, 255, 255));
        AveragesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        AveragesPanel.setLayout(null);

        AverageWTLabel.setFont(new java.awt.Font("Arial Black", 0, 12)); 
        AverageWTLabel.setText("Average Waiting Time:");
        AveragesPanel.add(AverageWTLabel);
        AverageWTLabel.setBounds(40, 15, 160, 14);

        AverageRTLabel.setFont(new java.awt.Font("Arial Black", 0, 12)); 
        AverageRTLabel.setText("Average Response Time:");
        AveragesPanel.add(AverageRTLabel);
        AverageRTLabel.setBounds(25, 95, 170, 14);

        AverageTTLabel.setFont(new java.awt.Font("Arial Black", 0, 12)); 
        AverageTTLabel.setText("Average Turnaround Time:");
        AveragesPanel.add(AverageTTLabel); 
        AverageTTLabel.setBounds(18, 175, 180, 14);

        AverageWT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AverageWT.setText("");
        AverageWT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AverageWTActionPerformed(evt);
            }
        });
        AveragesPanel.add(AverageWT);
        AverageWT.setBounds(60, 45, 80, 22);

        AverageRT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AverageRT.setText("");
        AverageRT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AverageRTActionPerformed(evt);
            }
        });
        AveragesPanel.add(AverageRT);
        AverageRT.setBounds(60, 125, 80, 22);

        AverageTT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AverageTT.setText("");
        AveragesPanel.add(AverageTT);
        AverageTT.setBounds(60, 205, 80, 22);

        GeneralPanel.add(AveragesPanel);
        AveragesPanel.setBounds(830, 250, 210, 320);
        //end of the time averages panel
        
        //Layout of panels
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GeneralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GeneralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
 
        
        pack(); //JFrame adjust to the size of the general panel 
        setLocationRelativeTo(null); //center the application when displayed
    }// </editor-fold>                        
    
     
     
    /*********    ActionListener Events     ***************/
    
    private void RandRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {                                             
    	if (RandRadioBtn.isSelected()) {
            
            int randomNumber = randNum.nextInt(20) + 1;
            numberOfProcess = randomNumber;
            numOfProcessTxtField.setText(Integer.toString(randomNumber));
            
            generateRandomNumbers(0, randomNumber, ArrivalTimesTxtField);
            generateRandomNumbers(1, randomNumber, BurstTimeTxtFld);
            generateRandomNumbers(1,randomNumber, PriorityTxtFld);
            
            numOfProcessTxtField.setEditable(!RandRadioBtn.isSelected());
            ArrivalTimesTxtField.setEditable(!RandRadioBtn.isSelected());
            BurstTimeTxtFld.setEditable(!RandRadioBtn.isSelected());
            PriorityTxtFld.setEditable(!RandRadioBtn.isSelected());
        } 
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
                numOfProcessTxtField.setText(String.valueOf(c));
            } else {
                String newText = currentText + c;
                try {
                    int value = Integer.parseInt(newText);
                    if (value < 1 || value > 30) {
                        evt.consume();
                    } else {
                        numOfProcessTxtField.setText(newText);
                        numberOfProcess = value;
                        //System.out.println(numberOfProcess);
                    }
                } catch (NumberFormatException e) {
                    evt.consume(); // Consume input if the entered value is not a valid number
                }
            }
        }



	}
    
    private void ArrivalTimesTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        
    }  
    
    private void ArrivalTimesTxtFieldKeyTyped(KeyEvent evt) {
		
	}
    
    private void BurstTimeTxtFldActionPerformed(java.awt.event.ActionEvent evt) {
    	
    }
    
    private void PriorityTxtFldActionPerformed(ActionEvent evt) {
		
	}

    private void PrioOrderActionPerformed(ActionEvent evt) {
        String selectedValue = (String) PrioOrder.getSelectedItem();
        selectedPrioOrder = selectedValue;
    }

    private void NumOfQueueComboBoxActionPerformed(ActionEvent evt) {
    	ArrivalQueueComboBox.removeAllItems(); // Clear existing items

        // Add options to comboBox2 based on the selected value of comboBox1
    	int selectedValue = Integer.parseInt((String) NumOfQueueComboBox.getSelectedItem());
        numberOfQueues = selectedValue;
        for (int i = 1; i <= selectedValue; i++) {
        	ArrivalQueueComboBox.addItem(Integer.toString(i));
        }
        if(selectedValue == 1) { 
        	MultilevelQueuePanel.remove(Queue2Label);
            MultilevelQueuePanel.remove(Queue2AlgoOptions);
            MultilevelQueuePanel.remove(Queue2QuantumLbl);
			MultilevelQueuePanel.remove(Queue2QuantumInput);
            MultilevelQueuePanel.remove(Queue3Label);
            MultilevelQueuePanel.remove(Queue3AlgoOptions);
            MultilevelQueuePanel.remove(Queue3QuantumLbl);
			MultilevelQueuePanel.remove(Queue3QuantumInput);
        }
        else if(selectedValue == 2) {
            MultilevelQueuePanel.add(Queue2Label);
            MultilevelQueuePanel.add(Queue2AlgoOptions);
            MultilevelQueuePanel.remove(Queue3Label);
            MultilevelQueuePanel.remove(Queue3AlgoOptions);
            MultilevelQueuePanel.remove(Queue3QuantumLbl);
			MultilevelQueuePanel.remove(Queue3QuantumInput);
        }else if(selectedValue == 3) {
            MultilevelQueuePanel.add(Queue2Label);
            MultilevelQueuePanel.add(Queue2AlgoOptions);
            MultilevelQueuePanel.add(Queue3Label);
            MultilevelQueuePanel.add(Queue3AlgoOptions);
        }
        revalidate();
        repaint();
	}                                               
                                                   
    private void Queue1AlgoOptionsActionPerformed(ActionEvent evt) {
		String choseNalgo = (String) Queue1AlgoOptions.getSelectedItem();
        Queue1Algo = choseNalgo;
		if(choseNalgo.equals("Round Robin")) {
			MultilevelQueuePanel.add(Queue1QuantumLbl);
			MultilevelQueuePanel.add(Queue1QuantumInput);
		}else {
			MultilevelQueuePanel.remove(Queue1QuantumLbl);
			MultilevelQueuePanel.remove(Queue1QuantumInput);
		}
		revalidate();
		repaint();
	}
    private void Queue1QuantumInputActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        
    } 
    private void Queue1QuantumInputKeyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        String currentText = Queue1QuantumInput.getText();
    
        if (c == KeyEvent.VK_BACK_SPACE) {
            if (!currentText.isEmpty()) {
                // Allow backspace if the text field is not empty
                Queue1QuantumInput.setText(currentText.substring(0, currentText.length() - 1));
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
                    Queue1QuantumInput.setText(newText);
                    Queue1Quantum = value;
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
		if(choseNalgo.equals("Round Robin")) {
			MultilevelQueuePanel.add(Queue2QuantumLbl);
			MultilevelQueuePanel.add(Queue2QuantumInput);
		}else {
			MultilevelQueuePanel.remove(Queue2QuantumLbl);
			MultilevelQueuePanel.remove(Queue2QuantumInput);
		}
		revalidate();
		repaint();
	}

    private void Queue2QuantumInputActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    } 
    private void Queue2QuantumInputKeyTyped(KeyEvent evt) {
    	char c = evt.getKeyChar();
        String currentText = Queue2QuantumInput.getText();
    
        if (c == KeyEvent.VK_BACK_SPACE) {
            if (!currentText.isEmpty()) {
                // Allow backspace if the text field is not empty
                Queue2QuantumInput.setText(currentText.substring(0, currentText.length() - 1));
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
                    Queue2QuantumInput.setText(newText);
                    Queue2Quantum = value;
                    evt.consume(); // Consume the event to prevent default behavior
                }
            } catch (NumberFormatException e) {
                evt.consume(); // Consume input if the entered value is not a valid number
            }
        }
		
	} 
    
    private void Queue3AlgoOptionsActionPerformed(ActionEvent evt) {
		String choseNalgo = (String) Queue3AlgoOptions.getSelectedItem();
        Queue3Algo = choseNalgo;
		if(choseNalgo.equals("Round Robin")) {
			MultilevelQueuePanel.add(Queue3QuantumLbl);
			MultilevelQueuePanel.add(Queue3QuantumInput);
		}else {
			MultilevelQueuePanel.remove(Queue3QuantumLbl);
			MultilevelQueuePanel.remove(Queue3QuantumInput);
		}
		revalidate();
		repaint();
	}
    private void Queue3QuantumInputActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    } 
    private void Queue3QuantumInputKeyTyped(KeyEvent evt) {
    	char c = evt.getKeyChar();
        String currentText = Queue3QuantumInput.getText();
    
        if (c == KeyEvent.VK_BACK_SPACE) {
            if (!currentText.isEmpty()) {
                // Allow backspace if the text field is not empty
                Queue3QuantumInput.setText(currentText.substring(0, currentText.length() - 1));
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
                    Queue3QuantumInput.setText(newText);
                    Queue3Quantum = value;
                    evt.consume(); // Consume the event to prevent default behavior
                }
            } catch (NumberFormatException e) {
                evt.consume(); // Consume input if the entered value is not a valid number
            }
        }
	}
    
    private void AverageRTActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void AverageWTActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
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
            	gChart = new GanttChart(getAnimationPanel(), outputProcess);
            	gChart.startSimulation();
            } else {
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
    public int getQueue1Quantum(){
        return Queue1Quantum;
    }
    public String getQueue2Algo(){
        return Queue1Algo;
    }
    public int getQueue2Quantum(){
        return Queue2Quantum;
    }
    public String getQueue3Algo(){
        return Queue1Algo;
    }
    public int getQueue3Quantum(){
        return Queue3Quantum;
    }
    public JPanel getAnimationPanel() {
    	return animationPanel;
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


