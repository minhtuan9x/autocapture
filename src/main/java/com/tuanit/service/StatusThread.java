package com.tuanit.service;

import javax.swing.*;

public class StatusThread implements Runnable {
    private final Thread thread;
    private final JLabel jLabel;

    public StatusThread(Thread thread, JLabel jLabel) {
        this.thread = thread;
        this.jLabel = jLabel;
    }

    @Override
    public void run() {
        while (true) {
            if (thread.isAlive() && !thread.isInterrupted()) {

                try {
                    this.jLabel.setText("");
                    Thread.sleep(500);
                    this.jLabel.setText(". ");
                    Thread.sleep(500);
                    this.jLabel.setText(". . ");
                    Thread.sleep(500);
                    this.jLabel.setText(". . . ");
                    Thread.sleep(500);
                    this.jLabel.setText(". . . . ");
                    Thread.sleep(500);
                    this.jLabel.setText(". . . . . ");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                this.jLabel.setText("x");
                return;
            }
        }
    }
}
