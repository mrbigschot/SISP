package gui;

import mvc.Controller;
import swarmintelligence.Globals;
import swarmintelligence.SIModel;
import bugs.BugConfiguration;
import bugs.SwarmConfiguration;
import javax.swing.DefaultListModel;

public class ControlsFrame extends javax.swing.JFrame {

    private SIFrame theFrame;
    private SIModel theModel;
    private Controller theController;
    private boolean lockHives, lockResources, lockWalls;
    private SwarmConfiguration swarmConfig;
    private DefaultListModel<BugConfiguration> configs;
    private BugConfiguration currentConfig;


    public ControlsFrame() {
        initComponents();
        this.setLocation(800, 0);
        setTitle("Controls");
        setVisible(true);
    }

    public ControlsFrame(SIFrame f, SIModel m, Controller c) {
        this();
        theFrame = f;
        theModel = m;
        theController = c;
        init();
    }

    private void init() {
        swarmConfig = theModel.swarmA.getConfiguration();
        configs = new DefaultListModel<>();
        for (BugConfiguration bugConfig : swarmConfig) {
            configs.addElement(bugConfig);
        }
        configList.setModel(configs);
        ph1Slider.setValue(Globals.PHER_1_PERSISTENCE);
        ph2Slider.setValue(Globals.PHER_2_PERSISTENCE);
        ph3Slider.setValue(Globals.PHER_3_PERSISTENCE);

    }

    private void restart() {
        theController.restart();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {

        }
        theModel.restart();
        theFrame.repaint();
        runButton.setSelected(theController.getRunning());
    }

    private void reset() {
        theController.restart();
        theModel.reset(lockHives, lockResources, lockWalls);
        theFrame.repaint();
        runButton.setSelected(theController.getRunning());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        viewButtons = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        ctrlPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pher1Toggle = new javax.swing.JRadioButton();
        pher2Toggle = new javax.swing.JRadioButton();
        standardViewToggle = new javax.swing.JRadioButton();
        pher3Toggle = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        ctrlButton = new javax.swing.JToggleButton();
        contestButton = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        runButton = new javax.swing.JToggleButton();
        restartButton = new javax.swing.JButton();
        timeSlider = new javax.swing.JSlider();
        bugPanel = new javax.swing.JPanel();
        setButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        tendencies = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        condenseSlider = new javax.swing.JSlider();
        matchSlider = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        avoidTF = new javax.swing.JTextField();
        matchTF = new javax.swing.JTextField();
        condenseTF = new javax.swing.JTextField();
        avoidSlider = new javax.swing.JSlider();
        grabCheck = new javax.swing.JCheckBox();
        randomizeButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        sizeSlider = new javax.swing.JSlider();
        swarmSizeTF = new javax.swing.JTextField();
        swarmSizeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        configList = new javax.swing.JList();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        envPanel = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();
        lockPanel = new javax.swing.JPanel();
        lockHiveCheck = new javax.swing.JCheckBox();
        lockResourceCheck = new javax.swing.JCheckBox();
        lockWallCheck = new javax.swing.JCheckBox();
        envObjectsPanel = new javax.swing.JPanel();
        resourcesLabel = new javax.swing.JLabel();
        goalSlider = new javax.swing.JSlider();
        wallsLabel = new javax.swing.JLabel();
        wallSlider = new javax.swing.JSlider();
        manualResources = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ph1Slider = new javax.swing.JSlider();
        ph2Slider = new javax.swing.JSlider();
        ph3Slider = new javax.swing.JSlider();
        ph1Check = new javax.swing.JCheckBox();
        ph2Check = new javax.swing.JCheckBox();
        ph3Check = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ctrlPanel.setOpaque(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "VIEWS"));
        jPanel1.setOpaque(false);

        viewButtons.add(pher1Toggle);
        pher1Toggle.setText("PH 1");
        pher1Toggle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pher1ToggleStateChanged(evt);
            }
        });

        viewButtons.add(pher2Toggle);
        pher2Toggle.setText("PH 2");
        pher2Toggle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pher2ToggleStateChanged(evt);
            }
        });

        viewButtons.add(standardViewToggle);
        standardViewToggle.setSelected(true);
        standardViewToggle.setText("Standard");
        standardViewToggle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                standardViewToggleStateChanged(evt);
            }
        });

        viewButtons.add(pher3Toggle);
        pher3Toggle.setText("PH 3");
        pher3Toggle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pher3ToggleStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(standardViewToggle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pher1Toggle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pher2Toggle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pher3Toggle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pher1Toggle)
                    .addComponent(pher2Toggle)
                    .addComponent(standardViewToggle)
                    .addComponent(pher3Toggle))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "MODES"));
        jPanel2.setOpaque(false);

        ctrlButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        ctrlButton.setText("CONTROL");
        ctrlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ctrlButtonActionPerformed(evt);
            }
        });

        contestButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        contestButton.setText("CONTEST");
        contestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contestButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ctrlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contestButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctrlButton)
                    .addComponent(contestButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "CONTROLS"));
        jPanel3.setOpaque(false);

        runButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        runButton.setText("RUN");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        restartButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        restartButton.setText("RESTART");
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });

        timeSlider.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        timeSlider.setMajorTickSpacing(5);
        timeSlider.setMaximum(50);
        timeSlider.setMinorTickSpacing(5);
        timeSlider.setValue(40);
        timeSlider.setInverted(true);
        timeSlider.setSize(new java.awt.Dimension(100, 10));
        timeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                timeSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(restartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 239, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(restartButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        timeSlider.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout ctrlPanelLayout = new javax.swing.GroupLayout(ctrlPanel);
        ctrlPanel.setLayout(ctrlPanelLayout);
        ctrlPanelLayout.setHorizontalGroup(
            ctrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ctrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ctrlPanelLayout.setVerticalGroup(
            ctrlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctrlPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 207, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Run", ctrlPanel);

        bugPanel.setOpaque(false);

        setButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        setButton.setText("SET");
        setButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setButtonActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tendencies.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TENDENCIES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Condense:");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Match:");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Avoid:");

        avoidTF.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        avoidTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);


        matchTF.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        matchTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);


        condenseTF.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        condenseTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        grabCheck.setText("Can Grab");

        randomizeButton.setText("Randomize");
        randomizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tendenciesLayout = new javax.swing.GroupLayout(tendencies);
        tendencies.setLayout(tendenciesLayout);
        tendenciesLayout.setHorizontalGroup(
            tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tendenciesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tendenciesLayout.createSequentialGroup()
                        .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(condenseSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(matchSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(avoidSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(condenseTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avoidTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matchTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(tendenciesLayout.createSequentialGroup()
                        .addComponent(grabCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(randomizeButton))))
        );
        tendenciesLayout.setVerticalGroup(
            tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tendenciesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(avoidTF, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(avoidSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(matchTF)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(matchSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(condenseSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(condenseTF, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(tendenciesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tendenciesLayout.createSequentialGroup()
                        .addComponent(grabCheck)
                        .addContainerGap())
                    .addComponent(randomizeButton, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        saveButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        saveButton.setText("SAVE");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        sizeSlider.setMaximum(500);
        sizeSlider.setMinimum(1);
        sizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sizeSliderStateChanged(evt);
            }
        });

        swarmSizeTF.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        swarmSizeTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);


        swarmSizeLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        swarmSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        swarmSizeLabel.setText("Number of Bugs:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(tendencies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(swarmSizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(swarmSizeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tendencies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(swarmSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(swarmSizeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addContainerGap())
        );

        configList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        configList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                configListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(configList);

        addButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        addButton.setText("ADD");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        removeButton.setText("REMOVE");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bugPanelLayout = new javax.swing.GroupLayout(bugPanel);
        bugPanel.setLayout(bugPanelLayout);
        bugPanelLayout.setHorizontalGroup(
            bugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bugPanelLayout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setButton))
                    .addGroup(bugPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bugPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bugPanelLayout.setVerticalGroup(
            bugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(removeButton)
                    .addComponent(setButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bugs", bugPanel);

        envPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "ENVIRONMENT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 12))); // NOI18N
        envPanel.setOpaque(false);

        resetButton.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        resetButton.setText("RESET");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        lockPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lockHiveCheck.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        lockHiveCheck.setText("Lock Hive Positions");
        lockHiveCheck.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lockHiveCheckStateChanged(evt);
            }
        });

        lockResourceCheck.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        lockResourceCheck.setText("Lock Resource Positions");
        lockResourceCheck.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lockResourceCheckStateChanged(evt);
            }
        });

        lockWallCheck.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        lockWallCheck.setText("Lock Wall Positions");
        lockWallCheck.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lockWallCheckStateChanged(evt);
            }
        });

        javax.swing.GroupLayout lockPanelLayout = new javax.swing.GroupLayout(lockPanel);
        lockPanel.setLayout(lockPanelLayout);
        lockPanelLayout.setHorizontalGroup(
            lockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lockResourceCheck)
                    .addComponent(lockWallCheck)
                    .addComponent(lockHiveCheck))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lockPanelLayout.setVerticalGroup(
            lockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lockHiveCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lockResourceCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lockWallCheck)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        envObjectsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        envObjectsPanel.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N

        resourcesLabel.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        resourcesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        resourcesLabel.setText("Resources:");

        goalSlider.setMaximum(5);
        goalSlider.setSnapToTicks(true);
        goalSlider.setValue(1);
        goalSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                goalSliderStateChanged(evt);
            }
        });

        wallsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        wallsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        wallsLabel.setText("Walls:");

        wallSlider.setMaximum(20);
        wallSlider.setSnapToTicks(true);
        wallSlider.setValue(0);
        wallSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                wallSliderStateChanged(evt);
            }
        });

        manualResources.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        manualResources.setText("Manually Place");
        manualResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualResourcesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout envObjectsPanelLayout = new javax.swing.GroupLayout(envObjectsPanel);
        envObjectsPanel.setLayout(envObjectsPanelLayout);
        envObjectsPanelLayout.setHorizontalGroup(
            envObjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(envObjectsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(envObjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(envObjectsPanelLayout.createSequentialGroup()
                        .addComponent(resourcesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goalSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                    .addGroup(envObjectsPanelLayout.createSequentialGroup()
                        .addComponent(wallsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wallSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manualResources)
                .addContainerGap())
        );
        envObjectsPanelLayout.setVerticalGroup(
            envObjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(envObjectsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(envObjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resourcesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(goalSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manualResources, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(envObjectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(wallsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wallSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("PH 1 Decay");

        jLabel5.setText("PH 2 Decay");

        jLabel6.setText("PH 3 Decay");

        ph1Slider.setMinimum(1);
        ph1Slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ph1SliderStateChanged(evt);
            }
        });

        ph2Slider.setMinimum(1);
        ph2Slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ph2SliderStateChanged(evt);
            }
        });

        ph3Slider.setMinimum(1);
        ph3Slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ph3SliderStateChanged(evt);
            }
        });

        ph1Check.setSelected(true);
        ph1Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ph1CheckActionPerformed(evt);
            }
        });

        ph2Check.setSelected(true);
        ph2Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ph2CheckActionPerformed(evt);
            }
        });

        ph3Check.setSelected(true);
        ph3Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ph3CheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ph2Check))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ph1Check)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ph2Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ph1Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ph3Check)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ph3Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ph1Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ph1Check, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ph2Slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ph2Check, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ph3Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ph3Check, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout envPanelLayout = new javax.swing.GroupLayout(envPanel);
        envPanel.setLayout(envPanelLayout);
        envPanelLayout.setHorizontalGroup(
            envPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(envPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(envPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lockPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, envPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(resetButton))
                    .addComponent(envObjectsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        envPanelLayout.setVerticalGroup(
            envPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, envPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(envObjectsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Environment", envPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        reset();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void lockResourceCheckStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lockResourceCheckStateChanged
        lockResources = !lockResources;
    }//GEN-LAST:event_lockResourceCheckStateChanged

    private void lockWallCheckStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lockWallCheckStateChanged
        lockWalls = !lockWalls;
    }//GEN-LAST:event_lockWallCheckStateChanged

    private void lockHiveCheckStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lockHiveCheckStateChanged
        lockHives = !lockHives;
    }//GEN-LAST:event_lockHiveCheckStateChanged

    private void sizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sizeSliderStateChanged
//        Globals.SWARM_SIZE = sizeSlider.getValue();
        restart();
    }//GEN-LAST:event_sizeSliderStateChanged

    private void goalSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_goalSliderStateChanged
        Globals.NUM_GOALS = goalSlider.getValue();
        reset();
    }//GEN-LAST:event_goalSliderStateChanged

    private void wallSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_wallSliderStateChanged
        Globals.NUM_WALLS = wallSlider.getValue();
        reset();
    }//GEN-LAST:event_wallSliderStateChanged

    private void setButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setButtonActionPerformed
        swarmConfig = new SwarmConfiguration(configs.elements());
        theModel.swarmA.configure(swarmConfig);
        restart();
    }//GEN-LAST:event_setButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        currentConfig.setWeightAvoid(avoidSlider.getValue() / 100.0);
        currentConfig.setWeightMatch(matchSlider.getValue() / 100.0);
        currentConfig.setWeightCondense(condenseSlider.getValue() / 100.0);
        currentConfig.setCanGrab(grabCheck.isSelected());
        currentConfig.setSize(sizeSlider.getValue());
        configList.updateUI();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void contestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contestButtonActionPerformed
        Globals.toggleContest();
        reset();
    }//GEN-LAST:event_contestButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        theController.toggleRunning();
    }//GEN-LAST:event_runButtonActionPerformed

    private void timeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_timeSliderStateChanged
        theController.setDelay(timeSlider.getValue());
    }//GEN-LAST:event_timeSliderStateChanged

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        restart();
    }//GEN-LAST:event_restartButtonActionPerformed

    private void ctrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctrlButtonActionPerformed
        Globals.CONTROL = !Globals.CONTROL;
        restart();
    }//GEN-LAST:event_ctrlButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        configs.addElement(new BugConfiguration());
    }//GEN-LAST:event_addButtonActionPerformed

    private void configListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_configListValueChanged
        currentConfig = (BugConfiguration)configList.getSelectedValue();
        if (currentConfig != null) {
            avoidSlider.setValue(currentConfig.getWeightAvoidInt());
            matchSlider.setValue(currentConfig.getWeightMatchInt());
            condenseSlider.setValue(currentConfig.getWeightCondenseInt());
            grabCheck.setSelected(currentConfig.getCanGrab());
            sizeSlider.setValue(currentConfig.getSize());
        }
    }//GEN-LAST:event_configListValueChanged

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        if (currentConfig != null) {
            configs.removeElement(currentConfig);
            currentConfig = null;
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void pher1ToggleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pher1ToggleStateChanged
        Globals.PHEREMODE1 = !Globals.PHEREMODE1;
        theController.display();
    }//GEN-LAST:event_pher1ToggleStateChanged

    private void pher2ToggleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pher2ToggleStateChanged
        Globals.PHEREMODE2 = !Globals.PHEREMODE2;
        theController.display();
    }//GEN-LAST:event_pher2ToggleStateChanged

    private void standardViewToggleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_standardViewToggleStateChanged
        theController.display();
    }//GEN-LAST:event_standardViewToggleStateChanged

    private void randomizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomizeButtonActionPerformed
        avoidSlider.setValue(Globals.random(0, 100));
        matchSlider.setValue(Globals.random(0, 100));
        condenseSlider.setValue(Globals.random(0, 100));
        grabCheck.setSelected(Globals.coinFlip(.5));
    }//GEN-LAST:event_randomizeButtonActionPerformed

    private void pher3ToggleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pher3ToggleStateChanged
        Globals.PHEREMODE3 = !Globals.PHEREMODE3;
        theController.display();
    }//GEN-LAST:event_pher3ToggleStateChanged

    private void ph1SliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ph1SliderStateChanged
        Globals.PHER_1_PERSISTENCE = ph1Slider.getValue();
        reset();
    }//GEN-LAST:event_ph1SliderStateChanged

    private void ph2SliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ph2SliderStateChanged
        Globals.PHER_2_PERSISTENCE = ph2Slider.getValue();
        reset();
    }//GEN-LAST:event_ph2SliderStateChanged

    private void ph3SliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ph3SliderStateChanged
        Globals.PHER_3_PERSISTENCE = ph3Slider.getValue();
        reset();
    }//GEN-LAST:event_ph3SliderStateChanged

    private void ph1CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ph1CheckActionPerformed
        if (ph1Check.isSelected()) {
            ph1Slider.setEnabled(true);
            Globals.PHER_1_PERSISTENCE = ph1Slider.getValue();
        } else {
            Globals.PHER_1_PERSISTENCE = 0;
            ph1Slider.setEnabled(false);
        }
        reset();
    }//GEN-LAST:event_ph1CheckActionPerformed

    private void ph2CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ph2CheckActionPerformed
        if (ph2Check.isSelected()) {
            ph2Slider.setEnabled(true);
            Globals.PHER_2_PERSISTENCE = ph2Slider.getValue();
        } else {
            Globals.PHER_2_PERSISTENCE = 0;
            ph2Slider.setEnabled(false);
        }
        reset();
    }//GEN-LAST:event_ph2CheckActionPerformed

    private void ph3CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ph3CheckActionPerformed
        if (ph3Check.isSelected()) {
            ph3Slider.setEnabled(true);
            Globals.PHER_3_PERSISTENCE = ph3Slider.getValue();
        } else {
            Globals.PHER_3_PERSISTENCE = 0;
            ph3Slider.setEnabled(false);
        }
        reset();
    }//GEN-LAST:event_ph3CheckActionPerformed

    private void manualResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualResourcesActionPerformed
        if (manualResources.isSelected()) {
            Globals.NUM_GOALS = 0;
            Globals.MANUAL_RESOURCES = true;
            goalSlider.setEnabled(false);
        } else {
            goalSlider.setEnabled(true);
            Globals.NUM_GOALS = goalSlider.getValue();
            Globals.MANUAL_RESOURCES = false;
        }
        reset();
    }//GEN-LAST:event_manualResourcesActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControlsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JSlider avoidSlider;
    private javax.swing.JTextField avoidTF;
    private javax.swing.JPanel bugPanel;
    private javax.swing.JSlider condenseSlider;
    private javax.swing.JTextField condenseTF;
    private javax.swing.JList configList;
    private javax.swing.JToggleButton contestButton;
    private javax.swing.JToggleButton ctrlButton;
    private javax.swing.JPanel ctrlPanel;
    private javax.swing.JPanel envObjectsPanel;
    private javax.swing.JPanel envPanel;
    private javax.swing.JSlider goalSlider;
    private javax.swing.JCheckBox grabCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox lockHiveCheck;
    private javax.swing.JPanel lockPanel;
    private javax.swing.JCheckBox lockResourceCheck;
    private javax.swing.JCheckBox lockWallCheck;
    private javax.swing.JCheckBox manualResources;
    private javax.swing.JSlider matchSlider;
    private javax.swing.JTextField matchTF;
    private javax.swing.JCheckBox ph1Check;
    private javax.swing.JSlider ph1Slider;
    private javax.swing.JCheckBox ph2Check;
    private javax.swing.JSlider ph2Slider;
    private javax.swing.JCheckBox ph3Check;
    private javax.swing.JSlider ph3Slider;
    private javax.swing.JRadioButton pher1Toggle;
    private javax.swing.JRadioButton pher2Toggle;
    private javax.swing.JRadioButton pher3Toggle;
    private javax.swing.JButton randomizeButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel resourcesLabel;
    private javax.swing.JButton restartButton;
    private javax.swing.JToggleButton runButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton setButton;
    private javax.swing.JSlider sizeSlider;
    private javax.swing.JRadioButton standardViewToggle;
    private javax.swing.JLabel swarmSizeLabel;
    private javax.swing.JTextField swarmSizeTF;
    private javax.swing.JPanel tendencies;
    private javax.swing.JSlider timeSlider;
    private javax.swing.ButtonGroup viewButtons;
    private javax.swing.JSlider wallSlider;
    private javax.swing.JLabel wallsLabel;
    // End of variables declaration//GEN-END:variables

}
