package mobileapi.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
* Created by virtuozzo on 19.02.14.
*/
@Entity
@Table(name = "music_rating", schema = "public", catalog = "kinoserver")

public class MusicRatingEntity {
    private Integer musicId;
    private Integer userId;
    private Integer value;
    private Timestamp dateTime;

    @Id
    @SequenceGenerator(name = "nextIdMusicRating", sequenceName = "music_rating_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdMusicRating")
    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    @ManyToMany(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "value", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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

        MusicRatingEntity that = (MusicRatingEntity) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (musicId != null ? !musicId.equals(that.musicId) : that.musicId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = musicId != null ? musicId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
