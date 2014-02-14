package mobileapi.dto;

/**
 * Created by virtuozzo on 14.02.14.
 */
public class Favourites {
    private long userId;
    private long musicId;
    private long dateTime;


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
