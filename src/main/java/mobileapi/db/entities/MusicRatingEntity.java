package mobileapi.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
* Created by virtuozzo on 19.02.14.
*/
@Entity
@Table(name = "music_rating", schema = "public", catalog = "kinoserver")

public class MusicRatingEntity {
    private Long id;
    private MusicEntity music;
    private UserEntity user;
    private Integer value;
    private Timestamp dateTime;

    @Id
    @SequenceGenerator(name = "nextIdMusicRating", sequenceName = "music_rating_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdMusicRating")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = MusicEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    public MusicEntity getMusic() {
        return music;
    }

    public void setMusic(MusicEntity music) {
        this.music = music;
    }

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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

        MusicRatingEntity that = (MusicRatingEntity) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (music != null ? !music.equals(that.music) : that.music != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = music != null ? music.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
