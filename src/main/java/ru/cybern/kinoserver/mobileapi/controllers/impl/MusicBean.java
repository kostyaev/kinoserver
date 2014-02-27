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
