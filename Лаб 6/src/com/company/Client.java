package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Client extends Thread {

    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private boolean connected = true;
    private String command;
    private SwapDialog owner;
    private int targetId;
    private int id;
    private Vector<Animal> toArrive = new Vector<>(), toDepart = new Vector<>();

    Client(SwapDialog owner) {
        this.owner = owner;
    }

    @Override
    public void run() {
        try {
            String host = "localhost";
            int port = 1337;
            socket = new Socket(host, port);
            connected = true;

            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());

            List<Integer> ids = (List<Integer>) inStream.readObject();
            id = inStream.readInt();
            owner.showList(ids, id);
            System.out.println("List received");

            while (connected) {
                if (inStream.available() == 0) {
                    if (command != null) {
                        switch (command) {
                            case "swap":
                                outStream.writeUTF(Server.SWAP_WITH_CLIENT);
                                outStream.writeInt(targetId);
                                outStream.writeObject(Singleton.getVector().getArrayForSend());
                                outStream.flush();
                                command = null;
                                break;
                            case "disconnect":
                                System.out.println("Somewho disconnected");
                                outStream.writeUTF(Server.DISCONNECT);
                                outStream.flush();
                                command = null;
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    String serverCommand = inStream.readUTF();
                    System.out.println(serverCommand);
                    int senderID;
                    switch (serverCommand) {
                        case "swapObj":
                            toArrive = (Vector<Animal>) inStream.readObject();
                            Singleton.getVector().setArrayToArrive(toArrive);
                            System.out.println("Размер полученного " + toArrive.size());
                            if(!toArrive.isEmpty()){
                                if(toArrive.get(0) instanceof Cat) {
                                    Singleton.getVector().createArrayToSend(false);
                                    Singleton.getVector().replaceTypeOfObject();
                                }
                                else if(toArrive.get(0) instanceof Dog) {
                                    Singleton.getVector().createArrayToSend(true);
                                    Singleton.getVector().replaceTypeOfObject();
                                }
                            }
                            Singleton.getVector().getHabitat().repaint();
                            senderID = inStream.readInt();
                            toDepart = Singleton.getVector().getArrayForSend();
                            outStream.writeUTF(Server.SEND_OBJ);
                            outStream.writeObject(toDepart);
                            outStream.writeInt(senderID);
                            outStream.flush();
                            break;
                        case "getObj":
                            toArrive = (Vector<Animal>) inStream.readObject();
                            Singleton.getVector().setArrayToArrive(toArrive);
                            System.out.println("Размер полученного " + toArrive.size());
                            if(!toArrive.isEmpty()){
                                if(toArrive.get(0) instanceof Cat) Singleton.getVector().replaceTypeOfObject();
                            else if(toArrive.get(0) instanceof Dog) Singleton.getVector().replaceTypeOfObject();
                            }
                            Singleton.getVector().getHabitat().repaint();
                            break;
                        case "updateClients":
                            ids = (List<Integer>) inStream.readObject();
                            owner.showList(ids, id);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    public void swapWithClient(int id) {
        command = "swap";
        targetId = id;
    }

    public void disconnect() {
        command = "disconnect";
    }

    private void closeAll() {
        System.out.println("closeAll");
        connected = false;
        try {
            inStream.close();
            outStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
