package com.company;

import java.io.IOException;
import java.io.PipedWriter;

public class OutputStream  {
    private PipedWriter pw;
    OutputStream() { pw = new PipedWriter(); }
    PipedWriter getStream() { return pw;}
    public void goWrite(int a){
        try {
            pw.write(a);
        } catch (IOException e){
            System.err.println(e);
        }
    }
}
