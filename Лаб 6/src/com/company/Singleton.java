package com.company;

import java.util.*;

public class Singleton {
    private static Singleton singleton;
    private static Vector<Animal> vector = new Vector<>();
    private static TreeSet<Integer> ids = new TreeSet<>();
    private static HashMap<Integer, Double> TTL = new HashMap<>();
    private static Vector<Animal> arrayToSend = new Vector<>();
    private static Vector<Animal> arrayToArrive = new Vector<>();
    private static Habitat habitat;

    public static synchronized Singleton  getVector(){
        if(singleton == null) singleton = new Singleton();
        return singleton;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        Singleton.habitat = habitat;
    }

    public Vector<Animal> getArray(){
        return vector;
    }
    public TreeSet<Integer> getIds() {return ids;}
    public HashMap<Integer, Double> getTTL(){return TTL;}
    public static void setVector(Vector<Animal> vv) {vector = vv;}
    public static void setTS(TreeSet<Integer> ts) {ids = ts;}
    public static void setTTL(HashMap<Integer, Double> hm) {TTL = hm;}

    public void replaceTypeOfObject(){
       vector.addAll(arrayToArrive);
        for(int i=0; i < arrayToArrive.size(); i++)
        {
            ids.add(arrayToArrive.get(i).getID());
            TTL.put(arrayToArrive.get(i).getID(), Habitat.counter);
        }
    }

    public void createArrayToSend(boolean isCat) {
        arrayToSend.clear();
        for(int i = 0; i < vector.size(); i++){
            if(vector.get(i) instanceof Cat && isCat) {
                arrayToSend.add(vector.get(i));
            }
            if (vector.get(i) instanceof Dog && !isCat) {

                arrayToSend.add(vector.get(i));
            }

        }
        Iterator<Animal> it = vector.iterator();
        while(it.hasNext()) {
            Animal animal = it.next();
            if (isCat) {
                if (animal instanceof Cat) {
                    removeElem(animal);
                    it.remove();
                }
            } else {
                if (animal instanceof Dog) {
                    removeElem(animal);
                    it.remove();
                }
            }
        }
    }

    public void removeElem(Animal animal)
    {
        ids.remove(animal.getID());
        TTL.remove(animal.getID());
    }


    public Vector<Animal> getArrayForSend() {
        return arrayToSend;
    }
    public void setArrayToArrive(Vector<Animal> array) {
        arrayToArrive.clear();
        arrayToArrive = array;
    }
}
