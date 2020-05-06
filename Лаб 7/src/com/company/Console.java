package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Console extends JDialog {

    private JTextArea consoleArea;
    private JTextField inputTextField;
    private String msg;
    private MFrame _parent;

    Console(MFrame parent){
        super(parent,"Консоль");
        this._parent = parent;
        setSize(450,460);
        setResizable(false);
        setLocationRelativeTo(parent);
        OutputStream s = new OutputStream();
        InputStream t = new InputStream(s.getStream(), parent);
        t.start();
        JPanel panelDialog = new JPanel();
        consoleArea = new JTextArea(60,10);
        JScrollPane consoleScrollPane = new JScrollPane(consoleArea);
        setFocusable(true);
        msg = "Доступные команды:\nОстановить генерацию XXX объектов - stop dog/cat\nЗапустить генерацию XXX объектов - start dog/cat\n";
        consoleArea.setText(msg);
        consoleArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(consoleArea.getText().charAt(consoleArea.getText().length() - 1) == '\n' && consoleArea.getText().charAt(consoleArea.getText().length() - 2) == '\n') {
                        consoleArea.setText(consoleArea.getText().substring(0, consoleArea.getText().length() - 1));
                        return;
                    }
                    msg = consoleArea.getText();
                    switch (getString(msg))
                    {
                        case "stop dog": {
                            s.goWrite(1);
                            msg += "Генерация собак остановлена\n";
                            break;
                        }
                        case "stop cat": {
                            s.goWrite(2);
                            msg += "Генерация кошек остановлена\n";
                            break;
                        }
                        case "start cat": {
                            s.goWrite(3);
                            msg += "Генерация кошек запущена\n";
                            break;
                        }
                        case "start dog": {
                            s.goWrite(4);
                            msg += "Генерация собак запущена\n";
                            break;
                        }
                    }
                    consoleArea.setText(msg);
                }
            }
        });


        consoleScrollPane.setPreferredSize(new Dimension(400,387));
        panelDialog.setLayout(new FlowLayout());
        consoleArea.setPreferredSize(new Dimension(400,387));
        consoleArea.setEditable(true);

        Font font = consoleArea.getFont();
        consoleArea.setFont(font.deriveFont(font.getSize()+3.5f));


        panelDialog.add(consoleScrollPane);
        setContentPane(panelDialog);
    }

    public String getString(String string){
        char[] ch = string.toCharArray();
        int fpos = 0, lastpos = 0;
        for(int i = 0; i < ch.length; i++){
            if(ch.length - 1 == i) {
                fpos = lastpos;
                lastpos = ch.length;
            }
            if(ch[i] == '\n') lastpos = i;
        }
        return string.substring(fpos + 1, lastpos);
    }

}

