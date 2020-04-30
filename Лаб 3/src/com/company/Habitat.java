package com.company;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

public class Habitat extends JPanel {
    public static int WIDTH = 1100;
    public static int HEIGHT = 700;
    private MFrame parent;
    private ObjectFactory factory;
    public static double catProb = 0.5, dogProb = 0.5, catDelay = 1, dogDelay = 1, catTTL = 3, dogTTL = 3;
    public static int dogs = 0, cats = 0;

    public Habitat(MFrame _parent) {
        super();
        setSize(WIDTH, HEIGHT);
        this.parent = _parent;
        setDoubleBuffered(true);
    }

    public void update(double time){
            if(Math.random() < catProb) {
                if(time % catDelay == 0){
                    int _id = (int) (Math.random() * 10000000);
                    factory = createObjects(true);
                    Singleton.getVector().getArray().add(factory.createObject(_id));
                    Singleton.getVector().getIds().add(_id);
                    Singleton.getVector().getTTL().put(_id, time);
                    System.out.println("Кошка");
                    cats++;
                    repaint();
                }
            }
            else {
                if(time % dogDelay == 0) {
                    int _id = (int) (Math.random() * 10000000);
                    factory = createObjects(false);
                    Singleton.getVector().getArray().add(factory.createObject(_id));
                    Singleton.getVector().getIds().add(_id);
                    Singleton.getVector().getTTL().put(_id, time);
                    System.out.println("Собака");
                    dogs++;
                    repaint();
                }
            }

            for(int i = 0; i < Singleton.getVector().getArray().size(); i++){
                if (Singleton.getVector().getIds().contains(Singleton.getVector().getArray().get(i).getID())){
                    double timeOfBorn = Singleton.getVector().getTTL().get(Singleton.getVector().getArray().get(i).getID());
                    double ttl;
                    if(Singleton.getVector().getArray().get(i) instanceof Cat) ttl = catTTL;
                    else ttl = dogTTL;
                    if ((time - timeOfBorn) >= ttl) {
                        if (Singleton.getVector().getArray().get(i) instanceof Dog) {
                            dogs--;
                            System.out.println("-Собака");
                        }
                        else {
                            cats--;
                            System.out.println("-Кошка");
                        }
                        Singleton.getVector().getIds().remove(Singleton.getVector().getArray().get(i).getID());
                        Singleton.getVector().getTTL().remove(Singleton.getVector().getArray().get(i).getID());
                        Singleton.getVector().getArray().remove(i);
                        repaint();
                    }
                }
            }
    }

    static ObjectFactory createObjects(boolean isCat) {
        if(isCat) {
            return new CatFactory();
        }
        else return new DogFactory();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int i = 0; i < Singleton.getVector().getArray().size(); i++){
            if(Singleton.getVector().getArray().get(i) instanceof Cat) ((Cat) Singleton.getVector().getArray().get(i)).draw(g);
            else if(Singleton.getVector().getArray().get(i) instanceof Dog) ((Dog) Singleton.getVector().getArray().get(i)).draw(g);
        }
    }
}
