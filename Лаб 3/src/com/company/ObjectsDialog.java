package com.company;

import javax.swing.*;
import java.awt.*;



public class ObjectsDialog extends JDialog {

    ObjectsDialog(MFrame parent) {
        super(parent, "Текущие объекты", true);
        setSize(300,300);
        setLayout(new FlowLayout());
        setResizable(false);
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JTextArea objs = new JTextArea(25,50);
        JScrollPane scrollPane = new JScrollPane(objs);
        scrollPane.setPreferredSize(new Dimension(270,250));
        String msg ="Класс\tИД\tВремя рождения";
        for (Animal s : Singleton.getVector().getArray()) {
            if (s instanceof Dog) {
                msg += "\nСобака\t" + ((Dog)s).getID() + "\t" + Singleton.getVector().getTTL().get(((Dog)s).getID());
            } else if (s instanceof Cat) {
                msg += "\nКошка\t" + ((Cat)s).getID() + "\t" + Singleton.getVector().getTTL().get(((Cat)s).getID());
            }
        }
        Font font = objs.getFont();
        objs.setFont(font.deriveFont(font.getSize()+3.5f));
        objs.setText(msg);
        objs.setEditable(false);

        panel.add(scrollPane);
        setContentPane(panel);
        setVisible(true);
    }
}
