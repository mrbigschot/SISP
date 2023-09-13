package gui;

import java.awt.Graphics;
import mvc.*;
import swarmintelligence.Globals;
import swarmintelligence.SwarmModel;

public class SIPanel extends javax.swing.JPanel implements Viewable {

    SIFrame theFrame;
    Controller theController;
    SwarmModel theModel;
    
    public SIPanel() {
        initComponents();
        theModel = new SwarmModel();
        theController = new Controller(this, theModel);
    }
    
    public SIPanel(SIFrame f) {
        this();
        theFrame = f;
    }
    
    @Override
    public void display() {
        theFrame.repaint();
    }
    
    public SwarmModel getModel() {
        return theModel;
    }
    
    public Controller getController() {
        return theController;
    }

    @Override
    public void paintComponent(Graphics g){
       theModel.paint(g);
       int[] totals = theModel.getTotals();
       blueProgress.setValue(progress(totals[0], Globals.totalMass()));
       redProgress.setValue(progress(totals[1], Globals.totalMass()));
    }
    
    public int progress(int n, int d) {
        double result = (double)n / d;
        return (int) (result * 1000);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        blueProgress = new javax.swing.JProgressBar();
        redProgress = new javax.swing.JProgressBar();

        setBackground(new java.awt.Color(255, 255, 255));
        setToolTipText("");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        blueProgress.setMaximum(1000);
        blueProgress.setFocusable(false);
        blueProgress.setString("BLUE");
        blueProgress.setStringPainted(true);

        redProgress.setMaximum(1000);
        redProgress.setString("RED");
        redProgress.setStringPainted(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(redProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                    .addComponent(blueProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(blueProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(redProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 222, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (Globals.MANUAL_RESOURCES) {
            theModel.createResource(evt.getX(), evt.getY());
            display();
        }
    }//GEN-LAST:event_formMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar blueProgress;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar redProgress;
    // End of variables declaration//GEN-END:variables

}
