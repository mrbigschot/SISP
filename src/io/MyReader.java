/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package io;

import java.io.*;
import java.awt.*;

public class MyReader {

    private BufferedReader br;
    String path;

    public String getPath() {
        return path;
    }

    public MyReader() {
        openIt(getFileName());
    }

    public MyReader(String filename) {
        openIt(filename);
    }

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

    public void close() {
        try {
            if (br != null) {
                br.close();
            }
        } catch (Exception e) {
            System.out.println("MyReader:can't close!" + e);
        }
    }

    private void openIt(String filename) {
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            System.out.println("MyReader -- can't open " + filename + "!" + e);
        }
    }

    private String getFileName() {
        FileDialog fd = new FileDialog(new Frame(), "Select Input File");
        fd.setFile("input");
        fd.setVisible(true);
        path = fd.getDirectory() + fd.getFile();
        return path;  // return the complete path

    }
}
