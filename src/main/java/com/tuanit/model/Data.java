package com.tuanit.model;

import java.io.Serializable;

public class Data implements Serializable {
    private String folder;
    private Long time;
    private Long h;
    private Long m;
    private Long s;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getH() {
        return h;
    }

    public void setH(Long h) {
        this.h = h;
    }

    public Long getM() {
        return m;
    }

    public void setM(Long m) {
        this.m = m;
    }

    public Long getS() {
        return s;
    }

    public void setS(Long s) {
        this.s = s;
    }

    public Data(String folder, Long time, Long h, Long m, Long s) {
        this.folder = folder;
        this.time = time;
        this.h = h;
        this.m = m;
        this.s = s;
    }
    public Data(){}

    @Override
    public String toString() {
        return "Data{" +
                "folder='" + folder + '\'' +
                ", time=" + time +
                ", h=" + h +
                ", m=" + m +
                ", s=" + s +
                '}';
    }
}
