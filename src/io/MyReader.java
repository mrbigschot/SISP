package io;

import java.io.*;
import java.awt.*;

public class MyReader {

    private BufferedReader br;
    private String path;


    public MyReader() {
        openIt(getFileName());
    }
    public MyReader(String filename) {
        openIt(filename);
    }

    public String getPath() { return path; }

    public String giveMeTheNextLine() {
        try {
            return br.readLine();
        } catch (Exception e) {
            System.out.println("MyReader -- eof?!" + e);
        }
        return "";
    }

    public boolean hasMoreData() {
        try {
            return br.ready();
        } catch (Exception e) {
            System.out.println("MyReader -- disaster!" + e);
        }
        return false;
    }

    private void openIt(String filename) {
        try {
            this.br = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            System.out.println("MyReader -- can't open " + filename + "!" + e);
        }
    }

    public void close() {
        try {
            if (this.br != null) this.br.close();
        } catch (Exception e) {
            System.out.println("MyReader:can't close!" + e);
        }
    }

    private String getFileName() {
        FileDialog fd = new FileDialog(new Frame(), "Select Input File");
        fd.setFile("input");
        fd.setVisible(true);
        this.path = fd.getDirectory() + fd.getFile();
        return this.path; // return the complete path
    }
}
