package com.tuanit.service;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread implements Runnable {
    private JFrame jFrame;

    public SocketThread(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    private void checkExist(JFrame jFrame) {
        try {
            ServerSocket serverSocket = new ServerSocket(8356);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    if (dis.read() == 1) {
                        JOptionPane.showMessageDialog(null, "Application started!!!");
                        jFrame.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleSocket();
            System.exit(0);
        }
    }

    private void handleSocket() {
        try {
            Socket s = new Socket("localhost", 8356);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.write(1);
            dout.flush();
            dout.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        checkExist(this.jFrame);
    }
}
