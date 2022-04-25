package com.tuanit.util;

import com.tuanit.Main;

import java.io.*;
import java.security.CodeSource;

public class FileUtil {
    public static String path() {
        try {
            CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            return jarFile.getParentFile().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeFile(Object o) {
        try (FileOutputStream fos = new FileOutputStream(path() + "\\data.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObject(Class<T> tClass) {
        try (FileInputStream fos = new FileInputStream(path() + "\\data.bin");
             ObjectInputStream oos = new ObjectInputStream(fos);) {
            return (T) oos.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
