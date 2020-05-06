package com.company;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class InputStream extends Thread {
    private PipedReader pr;
    private MFrame parent;
    InputStream(PipedWriter pw, MFrame parent) {
        try { pr = new PipedReader(pw); }
        catch(IOException e) {
            System.err.println(e);
        }
        this.parent = parent;
    }
    public void run() {
        while(true) {
            try {
                int s;
                while ((s = pr.read()) != 0) {
                    switch (s) {
                        case 1: {
                            if(Habitat.isDog) Habitat.isDog = false;
                            break; }
                        case 2: {
                            if(Habitat.isCat) Habitat.isCat = false;
                        break; }
                        case 3: {
                            if(!Habitat.isCat) Habitat.isCat = true;
                            break; }
                        case 4: {
                            if(!Habitat.isDog) Habitat.isDog = true;
                            break; }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}