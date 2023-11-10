package io;

import java.io.*;
import java.awt.*;

public class MyWriter {

    private PrintWriter pw;

    public MyWriter() {
        openIt(getFileName());
    }
    public MyWriter(String filename) {
        openIt(filename);
    }

    void openIt(String filename) {
        try {
            this.pw = new PrintWriter(new FileWriter(filename));
        } catch (Exception e) {
            System.out.println("MyWriter -- can't open " + filename + "!" + e);
        }
    }

    public void print(String s) {
        pw.print(s);
    }
    public void printLine(String s) {
        print(s + "\n");
    }

    public void close() {
        pw.close();
    }

    private String getFileName() {
        FileDialog fd = new FileDialog(new Frame(), "Select Output File", FileDialog.SAVE);
        fd.setFile("output");
        fd.setVisible(true);
        return fd.getDirectory() + fd.getFile(); // return the complete path
    }

}
