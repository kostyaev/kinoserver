package mobileapi.dto;

/**
 * Created by virtuozzo on 14.02.14.
 */
public class FilmRequest extends UserIdentity {
    private long dateTime;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
