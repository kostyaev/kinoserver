package mobileapi.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "film_history", schema = "public", catalog = "kinoserver")
public class FilmHistoryEntity {
    private Integer id;
    private Integer filmId;
    private String method;
    private Timestamp dateTime;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @SequenceGenerator(name = "nextIdFilmHistory", sequenceName = "film_history_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFilmHistory")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "film_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    @Basic
    @Column(name = "method", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "date_time", nullable = true, insertable = true, updatable = true, length = 29, precision = 6)
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
        if (filmId != null ? !filmId.equals(that.filmId) : that.filmId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filmId != null ? filmId.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
