package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

public class MFrame extends JFrame {
        private Timer timer;
        private double counter = 0;
        private Habitat habitat;
        private JLabel timerLabel, catDelayLabel, dogDelayLabel, dogLifeLabel, catLifeLabel, catProbLabel, dogProbLabel;
        private JButton startButton, stopButton, showTimeButton;
        private JCheckBox showObjects;
        private JRadioButton showTimeSim, hideTimeSim;
        private JDialog iDialog;
        private JTextArea iTextArea;
        private JTextField catDelayField, dogDelayField, dogLifeField, catLifeField;
        private JComboBox catComboProb;
        private JList dogListProb;


    MFrame(){
        setTitle("ok");
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setFocusable(true);

        iDialog = new JDialog(this, "Текущие объекты", true);
        iDialog.setSize(400,100);
        iDialog.setResizable(false);
        iDialog.setLocationRelativeTo(null);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new FlowLayout());
        iTextArea = new JTextArea(2,10);
        iTextArea.setEditable(false);
        Font font = iTextArea.getFont();
        iTextArea.setFont(font.deriveFont(font.getSize()+3.5f));
        JButton okDialogButton = new JButton("Ок");
        JButton cancelDialogButton = new JButton("Отмена");
        dialogPanel.add(iTextArea);
        dialogPanel.add(okDialogButton);
        dialogPanel.add(cancelDialogButton);
        iDialog.setContentPane(dialogPanel);
        okDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Singleton.getVector().getArray().clear();
                habitat.repaint();
                iDialog.dispose();
            }
        });
        cancelDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startButton.doClick();
                iDialog.setVisible(false);
            }
        });

        timerLabel = new JLabel("Время: 0 сек");
        add(timerLabel);
        timerLabel.setBounds(100,120,100,13);

        habitat = new Habitat(this);
        add(habitat);
        habitat.setBounds(50,100,1100,650);

        startButton = new JButton("Старт");
        add(startButton);
        startButton.setBounds(10,10,75,30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });

        stopButton = new JButton("Стоп");
        add(stopButton);
        stopButton.setBounds(90,10,75,30);
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                iTextArea.setText("Всего объектов: " + (Habitat.cats + Habitat.dogs) + "\nКошек: " + Habitat.cats + "\nСобак: " + Habitat.dogs);
                if(showObjects.isSelected()) iDialog.setVisible(true);
            }
        });


        showTimeButton = new JButton("Время");
        add(showTimeButton);
        showTimeButton.setBounds(170,10,75,30);
        showTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(timerLabel.isVisible()) {
                    timerLabel.setVisible(false);
                    hideTimeSim.setSelected(true);
                }
                else {
                    timerLabel.setVisible(true);
                    showTimeSim.setSelected(true);
                }
            }
        });

        showObjects = new JCheckBox("Показать диалог", false);
        add(showObjects);
        showObjects.setBounds(5, 45,140,13);

        ButtonGroup group = new ButtonGroup();
        showTimeSim = new JRadioButton("Показать время", true);
        group.add(showTimeSim);
        add(showTimeSim);
        showTimeSim.setBounds(5,60,120,13);
        hideTimeSim = new JRadioButton("Скрыть время", false);
        group.add(hideTimeSim);
        add(hideTimeSim);
        hideTimeSim.setBounds(5,75,120,13);
        showTimeSim.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                timerLabel.setVisible(true);
            }
        });
        hideTimeSim.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                timerLabel.setVisible(false);
            }
        });

        JMenuBar Bar = new JMenuBar();
        JMenu mainMenu = new JMenu("Главная");
        JMenuItem menuStart = new JMenuItem("Старт");
        JMenuItem menuStop = new JMenuItem("Стоп");
        JMenuItem menuShowTime = new JMenuItem("Показать время");
        mainMenu.add(menuStart);
        mainMenu.add(menuStop);
        mainMenu.add(menuShowTime);
        Bar.add(mainMenu);
        setJMenuBar(Bar);

        menuStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startButton.doClick();
            }
        });
        menuStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stopButton.doClick();
            }
        });
        menuShowTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showTimeButton.doClick();
            }
        });

        catDelayLabel = new JLabel("Пер. рождения кошек");
        add(catDelayLabel);
        catDelayLabel.setBounds(250,10,130,13);
        dogDelayLabel = new JLabel("Пер. рождения собак");
        add(dogDelayLabel);
        dogDelayLabel.setBounds(385,10,130,13);
        catDelayField = new JTextField(10);
        add(catDelayField);
        catDelayField.setBounds(250,27,130,16);
        catDelayField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double kk = Double.parseDouble(catDelayField.getText());
                if(kk >= 1.0) Habitat.catDelay = kk;
                else {
                    Habitat.catDelay = 1.0;
                    catDelayField.setText("1.0");
                }
            }
        });
        dogDelayField = new JTextField(10);
        add(dogDelayField);
        dogDelayField.setBounds(385,27,130,16);
        dogDelayField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double kk = Double.parseDouble(dogDelayField.getText());
                if(kk >= 1.0) Habitat.dogDelay = kk;
                else {
                    Habitat.dogDelay = 1.0;
                    dogDelayField.setText("1.0");
                }
            }
        });

        catProbLabel = new JLabel("Вер. рождения кошек");
        add(catProbLabel);
        catProbLabel.setBounds(520,10,130,13);
        dogProbLabel = new JLabel("Вер. рождения собак");
        add(dogProbLabel);
        dogProbLabel.setBounds(655,10,130,13);

        String[] dogValues = {"0","10","20","30","40","50","60","70","80","90","100"};
        dogListProb = new JList(dogValues);
        dogListProb.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane scrollList = new JScrollPane(dogListProb);
        add(scrollList);
        scrollList.setBounds(655,25,130,50);
        dogListProb.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(dogListProb.getValueIsAdjusting()) return;
                double kk = Double.parseDouble(dogListProb.getSelectedValue().toString());
                Habitat.dogProb = kk/100;
                Habitat.catProb = 1 - kk/100;
            }
        });



        String[] catValues = {"0","10","20","30","40","50","60","70","80","90","100"};
        catComboProb = new JComboBox(catValues);
        catComboProb.setSelectedIndex(5);
        add(catComboProb);
        catComboProb.setBounds(520,25,130,15);
        catComboProb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double kk = Double.parseDouble((String)catComboProb.getSelectedItem());
                Habitat.catProb = kk/100;
                Habitat.dogProb = 1 - kk/100;
            }
        });




        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_B){
                    startButton.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_E){
                    stopButton.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_T){
                   showTimeButton.doClick();
            }
            }
        });

        setVisible(true);
    }

    public void start(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter++;
                habitat.update(counter/10);
                timerLabel.setText("Время: " + counter/10 + " сек");
            }
        }, 0,100);
    }

    public void stop(){
        timer.cancel();
    }
}
