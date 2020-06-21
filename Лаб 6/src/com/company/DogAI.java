package com.company;

public class DogAI extends BaseAI {

    boolean isWait = true;


    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                try {
                    while(isWait) wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            move();
        }
    }

    @Override
    public synchronized void move() {
        for(int i = 0; i < Singleton.getVector().getArray().size(); i++) {
            Animal animal = Singleton.getVector().getArray().get(i);
            if (animal instanceof Dog) {
                ((Dog)animal).move();
            }
        }
    }


    public boolean isWait() {return isWait;}
    synchronized void mySuspend() {
        isWait = true;
    }

    synchronized void myResume() {
        isWait = false;
        notify();
    }


}
