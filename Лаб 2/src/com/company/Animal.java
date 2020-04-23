package com.company;

public abstract class Animal {
    protected int X = 0;
    protected int Y = 0;
    protected int id = 0;


    public Animal() {
    }
    public Animal(int _X, int _Y) {
        this.X = _X;
        this.Y = _Y;
    }



    public int getX() {
        return this.X;
    }
    public int getY() {
        return this.Y;
    }

    public int getID() {
        return this.id;
    }

    public void setX(int _X){
        this.X = _X;
    }
    public void setY(int _Y){
        this.Y = _Y;
    }


    public void move(){}
}
