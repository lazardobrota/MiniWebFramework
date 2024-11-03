package org.example.server;

import org.example.Test;
import org.example.reflection.HttpController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 8080;

    public static void main(String[] args) {
        HttpController httpController = new HttpController();
        Test test = new Test();
        httpController.generateUrls(Test.class, test);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket, httpController)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(HttpController httpController) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket, httpController)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
