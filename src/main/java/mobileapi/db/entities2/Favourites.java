package mobileapi.db.entities2;

import javax.persistence.*;

/**
 * Created by virtuozzo on 14.02.14.
 */
@Entity
@Table(name = "favorites")
public class Favourites {
    private long userId;
    private long musicId;
    private long dateTime;


    @Id
    @SequenceGenerator(name = "nextIdFavorites", sequenceName = "favorites_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdfavorites")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }




}
