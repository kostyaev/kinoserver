package ru.cybern.kinoserver.mobileapi.controllers.impl;

import org.apache.log4j.Logger;
import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.IMusicBean;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.PerformerEntity;
import ru.cybern.kinoserver.mobileapi.dto.Update;
import ru.cybern.kinoserver.parsers.models.Movie;
import ru.cybern.kinoserver.parsers.models.Soundtrack;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
public class ParserBean implements IParserBean {

    @Inject
    IFilmBean filmBean;

    @Inject
    IMusicBean musicBean;

    private static final Logger logger = Logger.getLogger(ParserBean.class);

    private void addMusic(List<Soundtrack> sounds, FilmEntity film) {
        for(Soundtrack sound : sounds) {

            PerformerEntity performerEntity = new PerformerEntity();
            performerEntity.setName(sound.getAuthor());
            musicBean.savePerformer(performerEntity);

            MusicEntity musicEntity = new MusicEntity();
            musicEntity.setName(sound.getSong());
            musicEntity.setPerformer(performerEntity);
            musicBean.saveMusic(musicEntity);

            FilmMusicEntity filmMusicEntity = new FilmMusicEntity();
            filmMusicEntity.setFilm(film);
            filmMusicEntity.setMusic(musicEntity);
            filmBean.saveFilmMusic(filmMusicEntity);

        }
    }

    @Override
    public void update(List<Movie> movieList) {
        Date updateDate = new Date();
        logger.info("Update time is: " + updateDate.getTime());
        for(Movie movie : movieList) {
            if(filmBean.isExist(movie.getName(), movie.getYear())) continue;
            FilmEntity filmEntity = new FilmEntity();
            filmEntity.setImg(movie.getImgName());
            filmEntity.setName(movie.getName());
            filmEntity.setYear(movie.getYear());
            filmEntity.setRating(0.0);

            FilmEntity addedFilm = filmBean.saveFilm(filmEntity);

            if(movie.getSounds() != null)
                addMusic(movie.getSounds(), addedFilm);

            FilmHistoryEntity filmHistoryEntity = new FilmHistoryEntity();
            filmHistoryEntity.setDateTime(updateDate);
            filmHistoryEntity.setFilm(filmEntity);
            filmHistoryEntity.setMethod(Update.Method.ADD.name());

            filmBean.saveFilmHistory(filmHistoryEntity);
        }

    }
}
