package ru.cybern.kinoserver.mobileapi.dto;

import java.util.Date;
import java.util.List;


public class Update {

    public static enum Method {ADD, DELETE, UPDATE}
    private Method method;
    private List<FilmMusic> filmMusic;
    private Date updateDate;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<FilmMusic> getFilmMusic() {
        return filmMusic;
    }

    public void setFilmMusic(List<FilmMusic> filmMusic) {
        this.filmMusic = filmMusic;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
