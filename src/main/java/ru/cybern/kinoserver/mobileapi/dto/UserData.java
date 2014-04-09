package ru.cybern.kinoserver.mobileapi.dto;

import java.util.List;

public class UserData {

    private List<Favorites> favorites;

    private List<MusicRating> musicRating;

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public List<MusicRating> getMusicRating() {
        return musicRating;
    }

    public void setMusicRating(List<MusicRating> musicRating) {
        this.musicRating = musicRating;
    }

    @Override
    public String toString() {
        return "JsonUpdate [favorites=" + favorites + ", musicrating=" + musicRating
                + "]";
    }

}
