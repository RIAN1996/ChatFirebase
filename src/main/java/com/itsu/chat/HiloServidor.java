/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsu.chat;

import Tools.FileHelper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.DefaultListModel;

/**
 *
 * @author draw-
 */
public class HiloServidor extends Thread {

    private DataInputStream entrada;
    private DataOutputStream salida;
    private Servidor server;
    private Socket Cliente;
    public static Vector<HiloServidor> usuarioActivo = new Vector();
    private String nombre;
    private ObjectOutputStream salidaObjeto;

    private FileHelper fhelper;

    public HiloServidor(Socket socketcliente, String nombre, Servidor serv) throws Exception {
        fhelper = new FileHelper("HiloServidor");

        this.Cliente = socketcliente;
        this.server = serv;
        for (int i = 0; i < usuarioActivo.size(); i++) {//for para recorrer la lista de usuarios 
            if (usuarioActivo.get(i).nombre.equals(nombre)) {//si el nombre existe, se le agrega numeros random
                nombre = nombre + agregar_numeros(nombre.length());
            }
            System.out.println("usuario?:" + usuarioActivo.get(i).nombre);
        }
        this.nombre = nombre;
        usuarioActivo.add(this);
        fhelper.escribir("Se conectó " + nombre);
        for (int i = 0; i < usuarioActivo.size(); i++) {
            usuarioActivo.get(i).envioMensajes(nombre + " se ha conectado.");
        }
    }

    public String agregar_numeros(int num) {
        String extra = "";
        for (int i = 0; i < num; i++) {
            int numero = (int) (Math.random() * 10 + 1);
            extra += Integer.toString(numero);
        }
        return extra;
    }

    public void run() {
        String mensaje = "";
        while (true) {
            try {
                entrada = new DataInputStream(Cliente.getInputStream());
                mensaje = entrada.readUTF();

                for (int i = 0; i < usuarioActivo.size(); i++) {
                    usuarioActivo.get(i).envioMensajes(mensaje);
                    //server.mensajeria("Mensaje enviado:"+mensaje);//aqui poner la encriptacion
                    server.mensajeria2("Mensaje enviado:" + mensaje);//aqui poner la encriptacion2
                }
            } catch (Exception e) {
                fhelper.escribir(e.toString());
                break;
            }
        }

        fhelper.escribir(nombre + " se desconectó");
        usuarioActivo.removeElement(this);
        server.mensajeria("El usuario se ha desconectado.");

        try {
            Cliente.close();
        } catch (Exception e) {
            fhelper.escribir(e.toString());
        }

    }

    private void envioMensajes(String msg) throws Exception {
        salida = new DataOutputStream(Cliente.getOutputStream());
        salida.writeUTF(msg);//Envio de mensaje
        DefaultListModel modelo = new DefaultListModel();

        for (int i = 0; i < usuarioActivo.size(); i++) {
            modelo.addElement(usuarioActivo.get(i).nombre);
        }
        salidaObjeto = new ObjectOutputStream(Cliente.getOutputStream());
        salidaObjeto.writeObject(modelo);
    }
}
