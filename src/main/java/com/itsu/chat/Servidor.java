/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.itsu.chat;

import Firebase.Conexion;
import Tools.FileHelper;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author x230
 */
public class Servidor extends javax.swing.JFrame {

    /**
     * Creates new form Servidor
     */
    private ServerSocket server;
    private final int PUERTOH = 1000;
    private Conexion firebaseConexion;
    private FileHelper fhelper;

    public Servidor() {
        firebaseConexion = new Conexion();
        try {
            firebaseConexion.conectar();
            initComponents();
            fhelper = new FileHelper("Servidor JFrame");
            this.fhelper.escribir("Servidor iniciado");
            try {
                server = new ServerSocket(PUERTOH);
                mensajeria("*.:Servidor Conectado:.\n");
                super.setVisible(true);
                
                while (true) {
                    Socket cliente = server.accept();
                    mensajeria("Cliente conectado desde la dirección: " + cliente.getInetAddress().getHostAddress());
                    
                    DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                    
                    HiloServidor hilo = new HiloServidor(cliente, entrada.readUTF(), this);
                    hilo.start();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.toString());
                fhelper.escribir(e.getMessage());
            }
            fhelper.escribir("Servidor Finalizado");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null,ex);
            fhelper.escribir(ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    void mensajeria(String msg) {
        this.jTextArea1.append(" " + msg + "\n");
    }

    void mensajeria2(String msg) {
        String desencryptador = "";
        int ascii = 0;
        int code_text = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (code_text == 1) {
                ascii = msg.charAt(i);
                desencryptador = desencryptador + (char) (ascii - 3);
            } else {
                ascii = msg.charAt(i);
            }
            if (msg.charAt(i) == ':') {
                code_text = 1;
            }
        }
        this.jTextArea1.append(" " + desencryptador + "\n");
    }
}
