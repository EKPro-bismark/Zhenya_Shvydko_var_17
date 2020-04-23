package com.company;

public class DogFactory implements ObjectFactory {
    @Override
    public Animal createObject(int _id) {
        return new Dog((int) (Math.random() * (Habitat.WIDTH-65)), (int) (Math.random() * (Habitat.HEIGHT-105)), _id);
    }
}
