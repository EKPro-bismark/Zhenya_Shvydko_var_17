package com.company;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Server {

    public static final String GET_CLIENTS = "get clients";
    public static final String SWAP_WITH_CLIENT = "connect client";
    public static final String SEND_OBJ = "sendObj";
    public static final String DISCONNECT = "disconnect";

    static TreeMap<Integer, ConnectionThread> clients = new TreeMap<>();
    static List<Integer> ids = new ArrayList<>();
    private static int id = 0;

    public static void main(String[] args) {
        try {
            System.out.println("Server is running");
            int port = 1337;
            ServerSocket serverSocket = new ServerSocket(port);
            // Ждет клиентов и для каждого создает отдельный поток
            while (true) {
                Socket s = serverSocket.accept();
                ConnectionThread connectionThread = new ConnectionThread(s, id);
                clients.put(id, connectionThread);
                ids.add(id);
                id++;
                connectionThread.start();
                update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void update(){
        for (int i = 0; i < ids.size()-1; i++) {
            Server.clients.get(ids.get(i)).updateClients();
        }
    }
    static void updateAll(){
        for (int i = 0; i < ids.size(); i++) {
            Server.clients.get(ids.get(i)).updateClients();
        }
    }
}
