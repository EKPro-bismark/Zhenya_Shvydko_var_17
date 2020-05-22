package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Timer;

public class MFrame extends JFrame {
        private Timer timer;
        private double counter = 0;
        private Habitat habitat;
        private JLabel timerLabel, catDelayLabel, dogDelayLabel, dogLifeLabel, catLifeLabel, catProbLabel, dogProbLabel;
        private JButton startButton, stopButton, showTimeButton, objDialogButton;
        private JCheckBox showObjects;
        private JRadioButton showTimeSim, hideTimeSim;
        private JDialog iDialog;
        private JTextArea iTextArea;
        private JTextField catDelayField, dogDelayField, dogLifeField, catLifeField;
        private JComboBox catComboProb;
        private JList dogListProb;
        private DogAI dogAI;
        private CatAI catAI;
        private Console console;


    MFrame(){
        setTitle("ok");
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setFocusable(true);
        load();
        dogAI = new DogAI();
        catAI = new CatAI();
        Thread catThread = new Thread(catAI);
        Thread dogThread = new Thread(dogAI);
        catThread.start();
        dogThread.start();
        console = new Console(this);

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
        timerLabel.setBounds(150,45,100,13);

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
        JMenuItem showConsole = new JMenuItem("Показать консоль");
        JMenuItem saveItem = new JMenuItem("Сохранить объекты");
        JMenuItem loadItem = new JMenuItem("Загрузить объекты");
        mainMenu.add(menuStart);
        mainMenu.add(menuStop);
        mainMenu.add(menuShowTime);
        mainMenu.add(showConsole);
        mainMenu.add(saveItem);
        mainMenu.add(loadItem);
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
        showConsole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                console.setVisible(true);
            }
        });
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stopButton.doClick();
                Singleton.getVector().getArray().clear();
                Singleton.getVector().getIds().clear();
                Singleton.getVector().getTTL().clear();
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(MFrame.this);
                if(result == JFileChooser.APPROVE_OPTION) {
                    try {
                        loadObjects(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(MFrame.this, "Файл не выбран.");
                }
            }
        });
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stopButton.doClick();
                saveObjects();
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

        dogLifeLabel = new JLabel("Время жизни соб.");
        add(dogLifeLabel);
        dogLifeLabel.setBounds(790,10,120,13);
        catLifeLabel = new JLabel("Время жизни кош.");
        add(catLifeLabel);
        catLifeLabel.setBounds(915,10,120,13);
        dogLifeField = new JTextField(10);
        add(dogLifeField);
        dogLifeField.setBounds(790,25,120,20);
        dogLifeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double kk = Double.parseDouble(dogLifeField.getText());
                if(kk > 0) Habitat.dogTTL = kk;
                else {
                    Habitat.dogTTL = 1.0;
                    dogLifeField.setText("1.0");
                }
            }
        });
        catLifeField = new JTextField(10);
        add(catLifeField);
        catLifeField.setBounds(915,25,120,20);
        catLifeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double kk = Double.parseDouble(catLifeField.getText());
                if(kk > 0) Habitat.catTTL = kk;
                else {
                    Habitat.catTTL = 1.0;
                    catLifeField.setText("1.0");
                }
            }
        });

        objDialogButton = new JButton("Текущие объекты");
        add(objDialogButton);
        objDialogButton.setBounds(1040,10,140,30);
        objDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new ObjectsDialog(MFrame.this);
            }
        });

        JLabel catAIPrLabel = new JLabel("Приоритет кош.");
        JLabel dogAIPrLabel = new JLabel("Приоритет соб.");
        add(catAIPrLabel);
        add(dogAIPrLabel);
        catAIPrLabel.setBounds(250,45,130,13);
        dogAIPrLabel.setBounds(385,45,130,13);
        String[] catAIValues = {"1","2", "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox catAIPrComboBox = new JComboBox(catAIValues);
        JComboBox dogAIPrComboBox = new JComboBox(catAIValues);
        catAIPrComboBox.setSelectedIndex(4);
        dogAIPrComboBox.setSelectedIndex(4);
        add(catAIPrComboBox);
        add(dogAIPrComboBox);
        catAIPrComboBox.setBounds(250,60,130,20);
        dogAIPrComboBox.setBounds(385,60,130,20);
        catAIPrComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int kk = Integer.parseInt((String) Objects.requireNonNull(catAIPrComboBox.getSelectedItem()));
                catThread.setPriority(kk);
            }
        });

        dogAIPrComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int kk = Integer.parseInt((String) Objects.requireNonNull(dogAIPrComboBox.getSelectedItem()));
                dogThread.setPriority(kk);
            }
        });
        JButton catAIButton = new JButton("Кошки AI");
        JButton dogAIButton = new JButton("Собаки AI");
        add(catAIButton);
        add(dogAIButton);
        catAIButton.setBounds(250,85,130,18);
        dogAIButton.setBounds(385,85,130,18);
        catAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(catAI.isWait()) catAI.myResume();
                else catAI.mySuspend();
            }
        });
        dogAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(dogAI.isWait()) dogAI.myResume();
                else dogAI.mySuspend();
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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
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

    public void save(){
        try {
            File file = new File("Configs.txt");
            if (!file.exists()) file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.println(Habitat.dogTTL);
            pw.println(Habitat.catTTL);
            pw.println(Habitat.dogDelay);
            pw.println(Habitat.catDelay);
            pw.println(Habitat.dogProb);
            pw.println(Habitat.catProb);
            pw.close();
        } catch (IOException e) {
            System.out.print("Error: " + e);
        }
    }

    public void load(){
        BufferedReader br = null;
        File file = new File("Configs.txt");
        if (!file.exists()) return;
        try {
            br = new BufferedReader(new FileReader(file));
            Habitat.dogTTL = (Double.parseDouble(br.readLine()));
            Habitat.catTTL = (Double.parseDouble(br.readLine()));
            Habitat.dogDelay = Double.parseDouble(br.readLine());
            Habitat.catDelay = (Double.parseDouble(br.readLine()));
            Habitat.dogProb = (Double.parseDouble(br.readLine()));
            Habitat.catProb = (Double.parseDouble(br.readLine()));
        } catch (IOException e) {
            System.out.print("Error: " + e);
        }
        finally {
            try {
                assert br != null;
                br.close();
            }
            catch(IOException e) {
                System.out.print("Error: " + e);
            }
        }
    }

    public void saveObjects()  {
        File file = new File("SaveObjects.obj");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(oos != null) {
                oos.writeObject(Singleton.getVector().getArray());
                oos.writeObject(Singleton.getVector().getIds());
                oos.writeObject(Singleton.getVector().getTTL());
                oos.close();
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void loadObjects(File _file) throws IOException, ClassNotFoundException {
        if(!(_file.exists()) || !(_file.getName().endsWith(".obj"))) {
            JOptionPane.showMessageDialog(this, "Choose '.obj' file");
            System.out.println("Choose '.obj' file");
            return;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Singleton.setVector((Vector<Animal>) ois.readObject());
        Singleton.setTS((TreeSet<Integer>)ois.readObject());
        Singleton.setTTL((HashMap<Integer, Double>) ois.readObject());
        ois.close();
        for(HashMap.Entry<Integer, Double> carsTTL : Singleton.getVector().getTTL().entrySet()) carsTTL.setValue(counter/10);
        habitat.repaint();
    }
}
