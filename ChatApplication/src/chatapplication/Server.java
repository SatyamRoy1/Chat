/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatapplication;

import java.io.*;
import java.net.*;

final class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // constructor
    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (IOException e) {
        }
    }

    public void startReading() {
        // for reading/providing thread
        Runnable r1 = () -> {

            System.out.println("reader started....");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {

                        System.out.println("Client terminateed the chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Client : " + msg);

                }

            } catch (IOException e) {
                System.out.println("connection closed");
            }

        };

        new Thread(r1).start();

    }

    public void startWriting() {
        // thread-taking data from the user and sending to client
        Runnable r2;
        r2 = () -> {
            System.out.println("writer started..");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("connection closed");
            }
            System.out.println("connection closed");
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {

        System.out.println("this is server....going to start server");
        new Server();

    }

}
