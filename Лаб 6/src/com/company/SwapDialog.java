package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SwapDialog extends JDialog {

    private JList<Object> clients;
    private List<Integer> clientList;
    private String[] items;
    private Client client;
    private JRadioButton radioCat;

    SwapDialog(MFrame parent) {
        super(parent, "Client-Server", false);
        setSize(288,349);
        setLayout(new FlowLayout());
        setResizable(false);
        setLocationRelativeTo(parent);
        JPanel swapDialogPanel = new JPanel(new FlowLayout());
        clients = new JList<>();
        JScrollPane scrollPane = new JScrollPane(clients);
        swapDialogPanel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(270,250));
        //Buttons
        JButton connectButton = new JButton("Connect");
        JButton disconnectButton = new JButton("Disconnect");
        JButton swapButton = new JButton("Swap");
        swapDialogPanel.add(connectButton);
        swapDialogPanel.add(disconnectButton);
        swapDialogPanel.add(swapButton);
        radioCat = new JRadioButton("Cat");
        JRadioButton radioDog = new JRadioButton("Dog");
        ButtonGroup radioButtons = new ButtonGroup();
        radioButtons.add(radioCat);
        radioButtons.add(radioDog);
        radioButtons.setSelected(radioCat.getModel(),true);
        swapDialogPanel.add(radioCat);
        swapDialogPanel.add(radioDog);

        //Area


        //Listeners
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });
        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Singleton.getVector().createArrayToSend(radioCat.isSelected());
                swap();
            }
        });

        //Singleton.getCollection().createArrayToSend(true);
        setContentPane(swapDialogPanel);
        setVisible(true);
    }



    private void connect() {
        client = new Client(this);
        client.start();
    }

    private void disconnect() {
        client.disconnect();
        clients.setListData(new String[0]);
    }

    private void swap() {
        if (clientList == null || clientList.isEmpty()) return;
        if (clients.getSelectedIndex() == -1) return;
        client.swapWithClient(clientList.get(clients.getSelectedIndex()));
        try {
            Thread.sleep(500);
            //Singleton.getCollection().replaceTypeOfObject(radioAuto.isSelected());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void showList(List<Integer> ids, int id) {
        clientList = ids;
        items = null;
        items = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == id){
                items[i] = "Клиент №" + ids.get(i) + " <- Это вы";
            }else{
                items[i] = "Клиент №" + ids.get(i);
            }
        }
        clients.setListData(items);
    }
}
