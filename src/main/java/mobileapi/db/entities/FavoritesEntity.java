package mobileapi.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "favorites", schema = "public", catalog = "kinoserver")
public class FavoritesEntity {
    private Integer userId;
    private Integer musicId;
    private Timestamp dateTime;

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @SequenceGenerator(name = "nextIdFavorites", sequenceName = "favorites_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFavorites")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "music_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
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

        FavoritesEntity that = (FavoritesEntity) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (musicId != null ? !musicId.equals(that.musicId) : that.musicId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (musicId != null ? musicId.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
