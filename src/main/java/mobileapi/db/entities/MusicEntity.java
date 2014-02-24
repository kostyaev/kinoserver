package mobileapi.db.entities;

import javax.persistence.*;

/**
 * Created by virtuozzo on 19.02.14.
 */
@Entity
@Table(name = "music", schema = "public", catalog = "kinoserver")
public class MusicEntity {
    private Long id;
    private String name;
    private PerformerEntity performer;
    private Double rating;

    @Id
    @SequenceGenerator(name = "nextIdMusic", sequenceName = "music_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nextIdMusic")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = PerformerEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_id", nullable = false)
    public PerformerEntity getPerformer() {
        return performer;
    }

    public void setPerformer(PerformerEntity performer) {
        this.performer = performer;
    }

    @Basic
    @Column(name = "rating")
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
        if (performer != null ? !performer.equals(that.performer) : that.performer != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (performer != null ? performer.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
