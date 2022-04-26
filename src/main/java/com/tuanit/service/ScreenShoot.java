package com.tuanit.service;

import com.tuanit.util.FileUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class ScreenShoot implements Runnable {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh-mm-ss a");
    LocalDate localDate = LocalDate.now();
    private Long time;
    private final String folder;
    private JTextArea jTextArea;

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    public ScreenShoot(Long time, String folder) {
        this.time = time;
        this.folder = folder;
    }

    public ScreenShoot(String folder) {
        this.folder = folder;
    }

    private void autoTake() throws Exception {
        Calendar now = Calendar.getInstance();
        //ImageIO.write(screenShot, "JPG", new File("d:\\" + formatter.format(now.getTime()) + ".jpg"));
        String folderLink = Objects.nonNull(folder) && !folder.isEmpty() ? folder + "\\ScreenShoot.jpg" : FileUtil.path() + "\\ScreenShoot.jpg";
        writeImg(folderLink);
        String outStr = "Take ScreenShot at: " + formatter.format(now.getTime()) + "\n" + folderLink;
        this.jTextArea.setText(outStr);
    }

    public void take() throws Exception {
        String folderLink = Objects.nonNull(folder) && !folder.isEmpty() ? folder + "\\ScreenShoot.jpg" : FileUtil.path() + "\\ScreenShoot.jpg";
        writeImg(folderLink);
        File file = new File(folder);
        if (!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
        {
            System.out.println("not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())         //checks file exists or not
            desktop.open(file);              //opens the specified file
    }

    private void writeImg(String folderLink) {
        try {
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenShot, "JPG", new File(folderLink));
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showConfirmDialog(null,"Error when write Img","Error", JOptionPane.DEFAULT_OPTION);
            writeImg(folderLink);
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                autoTake();
                Thread.sleep(this.time);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            JOptionPane.showConfirmDialog(null,e.getMessage(),"Error", JOptionPane.DEFAULT_OPTION);
        }
    }
}
