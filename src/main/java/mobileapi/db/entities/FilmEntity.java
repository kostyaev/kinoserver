package mobileapi.db.entities;

import javax.persistence.*;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "film", schema = "public", catalog = "kinoserver")
public class FilmEntity {
    private Integer id;
    private String name;
    private Integer year;
    private Integer img;
    private Double rating;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @SequenceGenerator(name = "nextIdFilm", sequenceName = "film_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdFilm")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "year", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "img", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
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

        FilmEntity that = (FilmEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
