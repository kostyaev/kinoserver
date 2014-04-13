package ru.cybern.kinoserver.mobileapi.controllers.impl;

import ru.cybern.kinoserver.mobileapi.controllers.IUserBean;
import ru.cybern.kinoserver.mobileapi.db.IFavoritesDAO;
import ru.cybern.kinoserver.mobileapi.db.IMusicRatingDAO;
import ru.cybern.kinoserver.mobileapi.db.IUserDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserBean implements IUserBean {

    @Inject
    private IUserDAO userDAO;

    @Inject
    private IFavoritesDAO favoritesDAO;

    @Inject
    private IMusicRatingDAO musicRatingDAO;

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userDAO.save(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
        userDAO.delete(user);
    }

    @Override
    public UserEntity getUser(int id) {
        return userDAO.findById(id,false);
    }

    @Override
    public boolean deleteUser(int id) {
        UserEntity user = userDAO.findById(id,false);
        if (user != null){
            deleteUser(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserEntity> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public List<FavoritesEntity> getFavoritesByUser(int userId, Date date) {
        return favoritesDAO.getFavoritesByUser(userId, date);
    }

    @Override
    public List<MusicRatingEntity> getRatingsByUser(int userId, Date date) {
        return  musicRatingDAO.getMusicRatingsByUser(userId, date);
    }

    @Override
    public FavoritesEntity saveFavorites(FavoritesEntity favorites) {
        return favoritesDAO.save(favorites);
    }


    @Override
    public MusicRatingEntity saveMusicRating(MusicRatingEntity musicRating) {
        return musicRatingDAO.save(musicRating);
    }

}
