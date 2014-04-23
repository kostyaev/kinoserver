package ru.cybern.kinoserver.parsers.models;

import java.util.List;

public class Movie {
    private String name;
    private List<Soundtrack>  sounds;
    private String imgName;
    private int year;

    public Movie(String name, List<Soundtrack> sounds, String imgName) {
        this.name = name;
        this.sounds = sounds;
        this.imgName = imgName;
    }

    public Movie(String name, List<Soundtrack> sounds, String imgName, int year) {
        this.name = name;
        this.sounds = sounds;
        this.imgName = imgName;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Soundtrack> getSounds() {
        return sounds;
    }

    public void setSounds(List<Soundtrack> sounds) {
        this.sounds = sounds;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}

