package com.company;

import java.util.Vector;

public class Singleton {
    private static Singleton singleton;
    private static Vector<Animal> vector = new Vector<>();

    public static synchronized Singleton  getVector(){
        if(singleton == null) singleton = new Singleton();
        return singleton;
    }

    public Vector<Animal> getArray(){
        return vector;
    }
}
