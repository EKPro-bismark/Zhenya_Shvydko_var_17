package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.Timer;

public class MFrame extends JFrame {
private Timer timer;
private double counter = 0;
private Habitat habitat;
private JLabel timerLabel, finalTimer, finalCats, finalDogs;


    MFrame(){
        setTitle("ok");
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setFocusable(true);

        finalTimer = new JLabel();
        finalCats = new JLabel();
        finalDogs = new JLabel();
        add(finalTimer);
        add(finalCats);
        add(finalDogs);
        finalTimer.setBounds(500,150,200,13);
        finalCats.setBounds(500,165,100,13);
        finalDogs.setBounds(500,180,100,13);

        timerLabel = new JLabel("Время: 0 сек");
        add(timerLabel);
        timerLabel.setBounds(100,120,100,13);

        habitat = new Habitat(this);
        add(habitat);
        habitat.setBounds(0,100,1100,600);


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_B){
                    start();
                }
                if(e.getKeyCode() == KeyEvent.VK_E){
                    stop();
                }
                if(e.getKeyCode() == KeyEvent.VK_T){
                    if(timerLabel.isVisible()) timerLabel.setVisible(false);
                    else timerLabel.setVisible(true);
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
        finalTimer.setText("Времени прошло: " + counter/10 + "сек");
        finalCats.setText("Кошек: " + Habitat.cats);
        finalDogs.setText("Собак: " + Habitat.dogs);
    }
}
