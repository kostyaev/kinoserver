package mobileapi.db.entities;

import javax.persistence.*;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "film_music", schema = "public", catalog = "kinoserver")

public class FilmMusicEntity {
    private Integer filmId;
    private Integer musicId;

    @Id
    @Column(name = "film_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @SequenceGenerator(name = "nextIdFilmMusic", sequenceName = "film_music_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFilmMusic")
    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    @Id
    @Column(name = "music_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmMusicEntity that = (FilmMusicEntity) o;

        if (filmId != null ? !filmId.equals(that.filmId) : that.filmId != null) return false;
        if (musicId != null ? !musicId.equals(that.musicId) : that.musicId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filmId != null ? filmId.hashCode() : 0;
        result = 31 * result + (musicId != null ? musicId.hashCode() : 0);
        return result;
    }
}
