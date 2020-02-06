package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            InetSocketAddress addr = new InetSocketAddress("192.168.0.4", 5555);
            serverSocket.bind(addr);

            int idSession = 0;
            while (true) {
                Socket socket;
                socket = serverSocket.accept();
                System.out.println("Nueva conexión entrante: " + socket);
                ((HiloServidor) new HiloServidor(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
