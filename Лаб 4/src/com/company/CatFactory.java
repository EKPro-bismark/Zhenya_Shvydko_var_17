package com.company;

public class CatFactory implements ObjectFactory {
    @Override
    public Animal createObject(int _id) {
        return new Cat((int) (Math.random() * (Habitat.WIDTH-65)), (int) (Math.random() * (Habitat.HEIGHT-105)), _id);
    }
}
