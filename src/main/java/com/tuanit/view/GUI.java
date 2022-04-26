package com.tuanit.view;

import com.tuanit.service.ScreenShoot;
import com.tuanit.model.Data;
import com.tuanit.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.util.Objects;

public class GUI {

    JTextField textFieldFolder;
    JTextField textFieldH;
    JTextField textFieldM;
    JTextField textFieldS;
    Data data = new Data();
    Data dataRead = new Data();
    JButton b;
    JButton bs;
    JButton btnTest;
    Thread thread;
    JLabel jLabelFolder;
    JLabel jLabelTime;
    JTextArea jTextArea;

    public void show() {
        JFrame frame = new JFrame("Auto Capture 1.0.1");
        checkExist(frame);
        init();
        dataRead = FileUtil.getObject(Data.class);
        addChooseFolder(frame);
        addTime(frame);
        addButtonStart(frame);
        addButtonStop(frame);
        addStatus(frame);
        frame.setSize(320, 280);
        frame.setLayout(null);
        frame.setVisible(true);
        hide(frame);
    }

    private void init() {
        if (Objects.isNull(FileUtil.getObject(Data.class)))
            FileUtil.writeFile(data);
    }

    private void addChooseFolder(JFrame frame) {
        textFieldFolder = new JTextField(Objects.nonNull(dataRead.getFolder()) ? dataRead.getFolder() : "");
        jLabelFolder = new JLabel("Folder: ");
        textFieldFolder.setBounds(50, 30, 150, 30);
        jLabelFolder.setBounds(50, 0, 200, 30);
        btnTest = new JButton("X");
        btnTest.setBounds(200, 30, 50, 30);

        btnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data.setFolder(textFieldFolder.getText() != null && !textFieldFolder.getText().isEmpty() ? textFieldFolder.getText() : FileUtil.path());
                ScreenShoot screenShoot = new ScreenShoot(data.getFolder());
                try {
                    screenShoot.take();
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Wrong path folder or system exception !!!");
                }

            }
        });

        frame.add(btnTest);
        frame.add(textFieldFolder);
        frame.add(jLabelFolder);
    }

    private void addTime(JFrame frame) {
        textFieldH = new JTextField(Objects.nonNull(dataRead.getH()) ? dataRead.getH().toString() : "");
        jLabelTime = new JLabel("Time: ");
        textFieldH.setBounds(50, 80, 50, 30);
        jLabelTime.setBounds(50, 55, 200, 30);
        frame.add(textFieldH);
        frame.add(jLabelTime);

        textFieldM = new JTextField(Objects.nonNull(dataRead.getM()) ? dataRead.getM().toString() : "");
        textFieldM.setBounds(125, 80, 50, 30);
        frame.add(textFieldM);

        textFieldS = new JTextField(Objects.nonNull(dataRead.getS()) ? dataRead.getS().toString() : "");
        textFieldS.setBounds(200, 80, 50, 30);
        frame.add(textFieldS);

    }

    private void addButtonStart(JFrame frame) {
        b = new JButton("Start");
        b.setBounds(50, 120, 100, 30);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long h = textFieldH.getText() != null && !textFieldH.getText().isEmpty() ? Long.parseLong(textFieldH.getText()) : 0;
                long m = textFieldM.getText() != null && !textFieldM.getText().isEmpty() ? Long.parseLong(textFieldM.getText()) : 0;
                long s = textFieldS.getText() != null && !textFieldS.getText().isEmpty() ? Long.parseLong(textFieldS.getText()) : 0;
                long t = (h * 3600 + m * 60 + s) * 1000;
                data.setFolder(textFieldFolder.getText());
                data.setTime(t);
                data.setH(h);
                data = new Data(textFieldFolder.getText(), t, h, m, s);
                FileUtil.writeFile(data);
                ScreenShoot screenShoot = new ScreenShoot(data.getTime(), data.getFolder());
                screenShoot.setjTextArea(jTextArea);
                thread = new Thread(screenShoot);
                textFieldFolder.setEnabled(false);
                textFieldH.setEnabled(false);
                textFieldM.setEnabled(false);
                textFieldS.setEnabled(false);
                b.setEnabled(false);
                bs.setEnabled(true);
                thread.start();
                JOptionPane.showMessageDialog(null, "Started!!!" +
                        "\nTime(ms): " + data.getTime() +
                        "\nDirectory: " + data.getFolder() + "\t(Default path resource)");
            }
        });
        frame.add(b);
    }

    private void addButtonStop(JFrame frame) {
        bs = new JButton("Stop");
        bs.setBounds(150, 120, 100, 30);
        bs.setEnabled(false);
        bs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldFolder.setEnabled(true);
                textFieldH.setEnabled(true);
                textFieldM.setEnabled(true);
                textFieldS.setEnabled(true);
                b.setEnabled(true);
                bs.setEnabled(false);
                thread.interrupt();
            }
        });
        frame.add(bs);
    }

    private void addStatus(JFrame frame) {
        jTextArea = new JTextArea(50, 10);
        jTextArea.setBounds(20, 170, 350, 40);
        jTextArea.setEditable(false);
        frame.add(jTextArea);
    }

    private void hide(JFrame frame) {
        if (SystemTray.isSupported()) {
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Object[] options = {"Yes", "Minimize"};
                    if (JOptionPane.showOptionDialog(frame,
                            "Are you sure you want to close this window?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
        }
        SystemTray systemTray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/link.png")));

        PopupMenu popupMenu = new PopupMenu();

//        MenuItem show = new MenuItem("Show");
//        show.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.setVisible(true);
//            }
//        });

        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

//        popupMenu.add(show);
        popupMenu.add(exit);

        trayIcon.setPopupMenu(popupMenu);

        try {
            systemTray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkExist(JFrame jFrame){
        try {
            ServerSocket socket = new ServerSocket(8356);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Application started!!!");
            System.exit(0);
        }

    }
}
