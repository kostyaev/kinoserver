package ru.cybern.kinoserver.mobileapi.db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
* Created by virtuozzo on 19.02.14.
*/
@Entity
@Table(name = "favorites", schema = "public", catalog = "kinoserver")
public class FavoritesEntity {
    private Long id;
    private UserEntity user;
    private MusicEntity music;
    private Timestamp dateTime;

    @Id
    @SequenceGenerator(name = "nextIdFavorites", sequenceName = "favorites_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFavorites")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    @ManyToOne(targetEntity = MusicEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    public MusicEntity getMusic() {
        return music;
    }

    public void setMusic(MusicEntity music) {
        this.music = music;
    }

    @Basic
    @Column(name = "date_time", nullable = false)
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
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (music != null ? !music.equals(that.music) : that.music != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (music != null ? music.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
