package ru.cybern.kinoserver.parsers.models;

import java.util.List;

public class Movie {
    private List<Soundtrack>  sounds;
    private int imgName;
    private int year;

    public Movie(List<Soundtrack> sounds, int imgName) {
        this.sounds = sounds;
        this.imgName = imgName;

    }

    public Movie(List<Soundtrack> sounds, int imgName, int year) {
        this.sounds = sounds;
        this.imgName = imgName;
        this.year = year;

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

    public int getImgName() {
        return imgName;
    }

    public void setImgName(int imgName) {
        this.imgName = imgName;
    }
}

