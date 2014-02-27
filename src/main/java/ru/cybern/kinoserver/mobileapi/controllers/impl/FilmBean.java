package ru.cybern.kinoserver.mobileapi.controllers.impl;


import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.db.IFilmDAO;
import ru.cybern.kinoserver.mobileapi.db.IFilmHistoryDAO;
import ru.cybern.kinoserver.mobileapi.db.IFilmMusicDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;

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
public class FilmBean implements IFilmBean {

    @Inject
    private IFilmDAO filmDAO;

    @Inject
    private IFilmHistoryDAO historyDAO;

    @Inject
    private IFilmMusicDAO filmMusicDAO;

    @Override
    public FilmEntity saveFilm(FilmEntity movie) {
        return filmDAO.save(movie);
    }

    @Override
    public void deleteFilm(FilmEntity film) {
       filmDAO.delete(film);
    }

    @Override
    public FilmEntity getFilm(int id) {
        return filmDAO.findById(id, false);
    }

    @Override
    public boolean deleteFilm(int id) {
        FilmEntity movie= filmDAO.findById(id, true);
        if (movie != null) {
            deleteFilm(movie);
            return true;
        }
        return false;
    }

    @Override
    public List<FilmEntity> getFilms() {
        return filmDAO.findAll();
    }

    @Override
    public FilmHistoryEntity saveFilmHistory(FilmHistoryEntity filmHistory) {
        return historyDAO.save(filmHistory);
    }

    @Override
    public void deleteFilmHistory(FilmHistoryEntity filmHistory) {
        historyDAO.delete(filmHistory);
    }

    @Override
    public FilmHistoryEntity getFilmHistory(int id) {
        return historyDAO.findById(id, false);
    }

    @Override
    public boolean deleteFilmHistory(int id) {
        FilmHistoryEntity history= historyDAO.findById(id, true);
        if (history != null) {
            deleteFilmHistory(history);
            return true;
        }
        return false;
    }

    @Override
    public List<FilmHistoryEntity> getFilmHistory() {
        return historyDAO.findAll();
    }

    @Override
    public FilmMusicEntity saveFilmMusic(FilmMusicEntity filmMusic) {
        return filmMusicDAO.save(filmMusic);
    }

    @Override
    public void deleteFilmMusic(FilmMusicEntity filmMusic) {
        filmMusicDAO.delete(filmMusic);
    }

    @Override
    public FilmMusicEntity getFilmMusic(int id) {
        return filmMusicDAO.findById(id, false);
    }

    @Override
    public boolean deleteFilmMusic(int id) {
        FilmMusicEntity filmMusic= filmMusicDAO.findById(id, true);
        if (filmMusic != null) {
            deleteFilmMusic(filmMusic);
            return true;
        }
        return false;
    }

    @Override
    public List<FilmMusicEntity> getFilmMusic() {
        return filmMusicDAO.findAll();
    }
}
