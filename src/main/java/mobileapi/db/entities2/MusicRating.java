package mobileapi.db.entities2;

/**
 * Created by virtuozzo on 14.02.14.
 */
public class MusicRating {

    private long userId;
    private long musicId;
    private int value;
    private long dateTime;


    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }



}
