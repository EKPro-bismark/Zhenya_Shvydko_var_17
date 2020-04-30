package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dog extends Animal {
    private static BufferedImage Image;

    static {
        try {
            Image = ImageIO.read(new File("src/com/company/assets/Dog.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private double speed = 3.0;
    private boolean isLeft = true;

    public Dog() {
        super();
    }


    public Dog(int _X, int _Y, int _ID){
        this.X = _X;
        this.Y = _Y;
        this.id = _ID;
    }


    public int getID() {return id;}
    
    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setX(int X) {
        this.X = X;
    }
    @Override
    public void setY(int Y) {
        this.Y = Y;
    }

    @Override
    public void move(){
        if((X < Habitat.WIDTH - 60 && X > 0) && (Y <= Habitat.HEIGHT - 100 && Y >= 0)) {
            if(isLeft) if(X-speed < 0) X = 0; else X-=speed;
            else if(X+speed > Habitat.WIDTH-60) X = Habitat.WIDTH - 60; else X+=speed;
            return;
        }
        if(X==0 && (Y > 0 && Y <= Habitat.HEIGHT-100)){
            if(Y-speed < 0) Y = 0;
            else Y-=speed;
            return;
        }
        if(X==0 && Y == 0){
            X+=speed;
            isLeft = false;
            return;
        }
        if(Y == 0 && X == Habitat.WIDTH - 60) {
            Y+=speed;
            return;
        }
        if(X==Habitat.WIDTH-60 && (Y > 0 && Y < Habitat.HEIGHT-100)){
            if(Y+speed > Habitat.HEIGHT - 100) Y = Habitat.HEIGHT - 100;
            else Y+=speed;
            return;
        }
        if(X == Habitat.WIDTH - 60 && Y == Habitat.HEIGHT - 100){
            X-=speed;
            isLeft = true;
        }
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
       g2d.drawImage(Image, this.X, this.Y, 80, 80, null);
    }
}
