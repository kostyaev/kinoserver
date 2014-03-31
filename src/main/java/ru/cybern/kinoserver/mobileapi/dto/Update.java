package ru.cybern.kinoserver.mobileapi.dto;

import java.util.Date;
import java.util.List;


public class Update {

    public static enum Method {ADD, DELETE, UPDATE}
    private Method method;
    private List<Favorites> favorites;
    //private List<Film> films;
    //private List<Music> music;
    private List<FilmMusic> filmMusic;
    private List<Performer> performers;
    private Date updateDate;


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public List<FilmMusic> getFilmMusic() {
        return filmMusic;
    }

    public void setFilmMusic(List<FilmMusic> filmMusic) {
        this.filmMusic = filmMusic;
    }

    public List<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
