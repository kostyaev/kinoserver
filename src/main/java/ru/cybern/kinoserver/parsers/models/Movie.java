package ru.cybern.kinoserver.parsers.models;

import java.util.List;

public class Movie {
    private List<Soundtrack>  sounds;
    private String imgName;

    public Movie(List<Soundtrack> sounds, String imgName) {
        this.sounds = sounds;
        this.imgName = imgName;
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

