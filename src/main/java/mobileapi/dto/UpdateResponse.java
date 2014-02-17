package mobileapi.dto;

import java.util.List;

/**
 * Created by virtuozzo on 17.02.14.
 */
public class UpdateResponse {
    private List<Film> movies;
    private List<Music> music;
    private List<FilmMusic> filmMusic;
    private List<MusicRating> ratings;

    public List<Film> getMovies() {
        return movies;
    }

    public void setMovies(List<Film> movies) {
        this.movies = movies;
    }

    public List<Music> getMusic() {
        return music;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }

    public List<FilmMusic> getFilmMusic() {
        return filmMusic;
    }

    public void setFilmMusic(List<FilmMusic> filmMusic) {
        this.filmMusic = filmMusic;
    }

    public List<MusicRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<MusicRating> ratings) {
        this.ratings = ratings;
    }
}
