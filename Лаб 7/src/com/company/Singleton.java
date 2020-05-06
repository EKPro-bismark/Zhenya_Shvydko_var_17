package com.company;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

public class Singleton {
    private static Singleton singleton;
    private static Vector<Animal> vector = new Vector<>();
    private static TreeSet<Integer> ids = new TreeSet<>();
    private static HashMap<Integer, Double> TTL = new HashMap<>();

    public static synchronized Singleton  getVector(){
        if(singleton == null) singleton = new Singleton();
        return singleton;
    }

    public Vector<Animal> getArray(){
        return vector;
    }
    public TreeSet<Integer> getIds() {return ids;}
    public HashMap<Integer, Double> getTTL(){return TTL;}
    public static void setVector(Vector<Animal> vv) {vector = vv;}
    public static void setTS(TreeSet<Integer> ts) {ids = ts;}
    public static void setTTL(HashMap<Integer, Double> hm) {TTL = hm;}
}
