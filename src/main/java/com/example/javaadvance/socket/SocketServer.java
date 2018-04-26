package com.example.javaadvance.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 网络编程
 * https://www.jianshu.com/p/ae5e1cee5b04
 */
public class SocketServer {

    public static void main(String[] args) throws Exception {
        int port = 7000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        DataInputStream inputStream = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        do {
            double length = inputStream.readDouble();
            System.out.println("接收到 length = " + length);
            double result = length*length;
            outputStream.writeDouble(result);
            outputStream.flush();

        } while (inputStream.readInt() != 0);

        socket.close();
        serverSocket.close();
    }
}
