package mobileapi.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "film_history", schema = "public", catalog = "kinoserver")
public class FilmHistoryEntity {
    private Long id;
    private FilmEntity film;
    private String method;
    private Timestamp dateTime;

    @Id
    @SequenceGenerator(name = "nextIdFilmHistory", sequenceName = "film_history_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFilmHistory")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(targetEntity = FilmEntity.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film_id", nullable = false)
    public FilmEntity getFilm() {
        return film;
    }

    public void setFilm(FilmEntity film) {
        this.film = film;
    }

    @Basic
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "date_time")
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmHistoryEntity that = (FilmHistoryEntity) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (film != null ? !film.equals(that.film) : that.film != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (film != null ? film.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
