package mobileapi.db.entities;

import javax.persistence.*;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "music", schema = "public", catalog = "kinoserver")
public class MusicEntity {
    private Integer id;
    private String name;
    private Integer performerId;
    private Double rating;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @SequenceGenerator(name = "nextIdMusic", sequenceName = "music_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdMusic")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "performer_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getPerformerId() {
        return performerId;
    }

    public void setPerformerId(Integer performerId) {
        this.performerId = performerId;
    }

    @Basic
    @Column(name = "rating", nullable = true, insertable = true, updatable = true, length = 17, precision = 17)
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicEntity that = (MusicEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (performerId != null ? !performerId.equals(that.performerId) : that.performerId != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (performerId != null ? performerId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
