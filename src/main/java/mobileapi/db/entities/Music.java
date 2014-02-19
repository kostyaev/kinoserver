package mobileapi.db.entities;

/**
 * Created by virtuozzo on 14.02.14.
 */
public class Music {
    private long id;
    private String name;
    private long perfomerId;
    private double rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPerfomerId() {
        return perfomerId;
    }

    public void setPerfomerId(long perfomerId) {
        this.perfomerId = perfomerId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
