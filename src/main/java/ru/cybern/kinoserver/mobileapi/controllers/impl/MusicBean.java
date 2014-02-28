package ru.cybern.kinoserver.mobileapi.controllers.impl;

import ru.cybern.kinoserver.mobileapi.controllers.IMusicBean;
import ru.cybern.kinoserver.mobileapi.db.IMusicDAO;
import ru.cybern.kinoserver.mobileapi.db.IMusicRatingDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MusicBean implements IMusicBean {

    @Inject
    private IMusicDAO musicDAO;

    @Inject
    private IMusicRatingDAO ratingDAO;

    @Override
    public MusicEntity saveMusic(MusicEntity music) {
        return musicDAO.save(music);
    }

    @Override
    public void deleteMusic(MusicEntity music) {
        musicDAO.delete(music);
    }

    @Override
    public MusicEntity getMusic(int id) {
        return musicDAO.findById(id,false);
    }

    @Override
    public boolean deleteMusic(int id) {
        MusicEntity music = musicDAO.findById(id, true);
        if (music != null){
            deleteMusic(music);
            return true;
        }
        return false;
    }

    @Override
    public List<MusicEntity> getAllMusic() {
        return musicDAO.findAll();
    }

    @Override
    public MusicRatingEntity saveRating(MusicRatingEntity rating) {
        return ratingDAO.save(rating);
    }

    @Override
    public void deleteRating(MusicRatingEntity rating) {
        ratingDAO.delete(rating);
    }

    @Override
    public MusicRatingEntity getRating(int id) {
        return ratingDAO.findById(id,false);
    }

    @Override
    public boolean deleteRating(int id) {
        MusicRatingEntity rating = ratingDAO.findById(id,true);
        if (rating != null){
            deleteRating(rating);
            return true;
        }

        return false;
    }

    @Override
    public List<MusicRatingEntity> getRatings() {
        return ratingDAO.findAll();
    }
}
