package com.example.ninja.drugstime;

/**
 * Created by ninja on 25/04/2018.
 */

public class Drug {
    private int id;
    private String name;
    private byte[] image;
    private int period;
    private int count;

    public Drug() {
    }

    public Drug(int id, String name, byte[] image, int period, int count) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.period = period;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

