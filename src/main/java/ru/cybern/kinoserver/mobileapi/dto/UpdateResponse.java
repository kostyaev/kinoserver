package ru.cybern.kinoserver.mobileapi.dto;

import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.PerformerEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by virtuozzo on 06.03.14.
 */
public class UpdateResponse {
    public static enum Method {ADD, DELETE, REPLACE}
    private Method method;
    private List<FavoritesEntity> favorites;
    private List<FilmEntity> films;
    private List<MusicEntity> music;
    private List<MusicRatingEntity> ratings;
    private List<PerformerEntity> performers;
    private Date updateDate;


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<FavoritesEntity> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoritesEntity> favorites) {
        this.favorites = favorites;
    }

    public List<FilmEntity> getFilms() {
        return films;
    }

    public void setFilms(List<FilmEntity> films) {
        this.films = films;
    }

    public List<MusicEntity> getMusic() {
        return music;
    }

    public void setMusic(List<MusicEntity> music) {
        this.music = music;
    }

    public List<MusicRatingEntity> getRatings() {
        return ratings;
    }

    public void setRatings(List<MusicRatingEntity> ratings) {
        this.ratings = ratings;
    }

    public List<PerformerEntity> getPerformers() {
        return performers;
    }

    public void setPerformers(List<PerformerEntity> performers) {
        this.performers = performers;
    }
}
