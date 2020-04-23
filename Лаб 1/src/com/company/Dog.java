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
            Image = ImageIO.read(new File("src/com/company/assets/dog.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
       g2d.drawImage(Image, this.X, this.Y, 80, 80, null);
    }
}
