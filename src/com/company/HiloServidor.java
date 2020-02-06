package com.company;

import java.io.*;
import java.net.Socket;

public class HiloServidor extends Thread {
    private int idHilo;
    private Socket sock;
    private DataOutputStream dos;
    private DataInputStream dis;

    public HiloServidor(Socket sock, int idHilo) {
        this.idHilo = idHilo;
        this.sock = sock;
        try {
            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (sock.isConnected()) {
                byte[] mensaje = new byte[25];
                dis.read(mensaje);
                String[] datos = new String(mensaje).split(",");

                double operacion = 0;
                switch (datos[2]) {
                    case "+":
                        operacion = Double.parseDouble(datos[0]) + Double.parseDouble(datos[1]);
                        break;
                    case "-":
                        operacion = Double.parseDouble(datos[0]) - Double.parseDouble(datos[1]);
                        break;
                    case "*":
                        operacion = Double.parseDouble(datos[0]) * Double.parseDouble(datos[1]);
                        break;
                    case "/":
                        exceptionDivCero(Double.parseDouble(datos[1]));
                        operacion = Double.parseDouble(datos[0]) / Double.parseDouble(datos[1]);
                        break;
                }

                String resultado = String.valueOf(operacion) + ",";
                dos.write(resultado.getBytes());
            }
        } catch (Exceptions e) {
            e.getMessage();
            try {
                dos.write(e.getMessage().getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exceptionDivCero(double op2) throws Exceptions {
        if (op2 == 0) {
            throw new Exceptions("División entre 0.");
        }
    }

    public void exceptionNegativo(double op2) throws Exceptions {
        if (op2 == 0) {
            throw new Exceptions("Número negativo..");
        }
    }
}
