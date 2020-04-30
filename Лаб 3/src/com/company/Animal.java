package com.company;

public abstract class Animal {
    protected static double ttl;
    protected int X = 0;
    protected int Y = 0;
    protected int id = 0;
    protected double probability = 0;
    protected int targetX = 0;
    protected int targetY = 0;

    public Animal() {
    }
    public Animal(int _X, int _Y, double _probability) {
        this.X = _X;
        this.Y = _Y;
        this.probability = _probability;
    }



    public int getX() {
        return this.X;
    }
    public int getY() {
        return this.Y;
    }
    public int getTargetX() { return this.targetX; }
    public int getTargetY() { return this.targetY; }
    public int getID() {
        return this.id;
    }

    public void setX(int _X){
        this.X = _X;
    }
    public void setY(int _Y){
        this.Y = _Y;
    }


    public static double getTTL(){
        return ttl;
    }

    public void move(){}
}
