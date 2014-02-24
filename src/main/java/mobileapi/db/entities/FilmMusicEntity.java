package mobileapi.db.entities;

import javax.persistence.*;

/**
* Created by virtuozzo on 19.02.14.
*/
@Entity
@Table(name = "film_music", schema = "public", catalog = "kinoserver")

public class FilmMusicEntity {
    private Long id;
    private FilmEntity film;
    private MusicEntity music;


    @Id
    @SequenceGenerator(name = "nextIdFilmMusic", sequenceName = "film_music_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFilmMusic")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = FilmEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    public FilmEntity getFilm() {
        return film;
    }

    public void setFilm(FilmEntity film) {
        this.film = film;
    }

    @ManyToOne(targetEntity = MusicEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    public MusicEntity getMusic() {
        return music;
    }

    public void setMusic(MusicEntity music) {
        this.music = music;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmMusicEntity that = (FilmMusicEntity) o;

        if (film != null ? !film.equals(that.film) : that.film != null) return false;
        if (music != null ? !music.equals(that.music) : that.music != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = film != null ? film.hashCode() : 0;
        result = 31 * result + (music != null ? music.hashCode() : 0);
        return result;
    }
}
