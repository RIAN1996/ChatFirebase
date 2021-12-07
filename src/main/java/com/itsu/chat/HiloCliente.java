/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsu.chat;

import Tools.FileHelper;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;

/**
 *
 * @author draw-
 */
public class HiloCliente extends Thread {

    private Socket SocketCliente;
    private DataInputStream entrada;
    private Cliente cliente;
    private ObjectInputStream entradaObjeto;

    private final FileHelper fhelper;

    public HiloCliente(Socket SocketCliente, Cliente cliente) {
        fhelper = new FileHelper("HiloCliente");

        this.SocketCliente = SocketCliente;
        this.cliente = cliente;
    }

    public void run() {
        while (true) {
            try {
                entrada = new DataInputStream(SocketCliente.getInputStream());

                cliente.mensajeria(entrada.readUTF());
                //cliente.mensajeria2(entrada.readUTF());
                entradaObjeto = new ObjectInputStream(SocketCliente.getInputStream());
                cliente.actualizarLista((DefaultListModel) entradaObjeto.readObject());
            } catch (IOException | ClassNotFoundException ex) {
                fhelper.escribir(ex.toString());
            }
        }
    }
}
