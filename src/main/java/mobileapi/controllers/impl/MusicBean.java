package mobileapi.controllers.impl;

import mobileapi.controllers.*;
import mobileapi.db.entities.MusicEntity;
import mobileapi.db.entities.MusicRatingEntity;

import java.util.List;

public class MusicBean implements IMusicBean {

    @Override
    public MusicEntity saveMusic(MusicEntity music) {
        return null;
    }

    @Override
    public void deleteMusic(MusicEntity music) {

    }

    @Override
    public MusicEntity getMusic(int id) {
        return null;
    }

    @Override
    public boolean deleteMusic(int id) {
        return false;
    }

    @Override
    public List<MusicEntity> getAllMusic() {
        return null;
    }

    @Override
    public MusicRatingEntity saveRating(MusicRatingEntity rating) {
        return null;
    }

    @Override
    public void deleteRating(MusicRatingEntity rating) {

    }

    @Override
    public MusicRatingEntity getRating(int id) {
        return null;
    }

    @Override
    public boolean deleteRating(int id) {
        return false;
    }

    @Override
    public List<MusicRatingEntity> getRatings() {
        return null;
    }
}
