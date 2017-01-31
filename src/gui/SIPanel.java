package gui;

import java.awt.Graphics;
import mvc.*;
import schotswarmintelligence.SIModel;

public class SIPanel extends javax.swing.JPanel implements Viewable {

    SIFrame theFrame;
    Controller theController;
    SIModel theModel;
    
    public SIPanel() {
        initComponents();
        theController = new Controller(this, theModel);
        theModel = new SIModel();
//        theController.start();
    }
    
    public SIPanel(SIFrame f) {
        this();
        theFrame = f;
    }
    
    @Override
    public void display() {
        theFrame.repaint();
    }
    
    public SIModel getModel() {
        return theModel;
    }

    @Override
    public void paintComponent(Graphics g){
       theModel.paint(g);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
