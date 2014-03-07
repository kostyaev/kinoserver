package ru.cybern.kinoserver.mobileapi.controllers.impl;

import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.IMusicBean;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.PerformerEntity;
import ru.cybern.kinoserver.mobileapi.dto.UpdateResponse;
import ru.cybern.kinoserver.parsers.kinopoisk.Parser;
import ru.cybern.kinoserver.parsers.models.Movie;
import ru.cybern.kinoserver.parsers.models.Soundtrack;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Stateless
public class ParserBean implements IParserBean{
    @Inject
    IFilmBean filmBean;

    @Inject
    IMusicBean musicBean;


    private void addMusic(List<Soundtrack> sounds, FilmEntity film) {
        for(Soundtrack sound : sounds) {
            MusicEntity musicEntity = new MusicEntity();
            musicEntity.setName(sound.getSong());
            PerformerEntity performerEntity = new PerformerEntity();
            performerEntity.setName(sound.getAuthor());
            musicEntity.setPerformer(performerEntity);

            FilmMusicEntity filmMusicEntity = new FilmMusicEntity();
            filmMusicEntity.setFilm(film);
            filmMusicEntity.setMusic(musicEntity);

            musicBean.saveMusic(musicEntity);
            filmBean.saveFilmMusic(filmMusicEntity);

        }
    }

    //TEST method
    @Override
    public void update() {
        try {
            HashMap<String,Movie> movieLib =  Parser.parse(1, 1);
            for(String movieName : movieLib.keySet()) {
                Movie movie = movieLib.get(movieName);

                FilmEntity filmEntity = new FilmEntity();
                filmEntity.setImg(movie.getImgName());
                filmEntity.setName(movieName);
                filmEntity.setYear(movie.getYear());
                filmEntity.setRating(5.0);

                FilmEntity addedFilm = filmBean.saveFilm(filmEntity);

                addMusic(movie.getSounds(), addedFilm);

                FilmHistoryEntity filmHistoryEntity = new FilmHistoryEntity();
                filmHistoryEntity.setDateTime(new Date());
                filmHistoryEntity.setFilm(filmEntity);
                filmHistoryEntity.setMethod(UpdateResponse.Method.ADD.name());

                filmBean.saveFilmHistory(filmHistoryEntity);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
