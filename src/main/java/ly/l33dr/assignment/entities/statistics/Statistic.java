package ly.l33dr.assignment.entities.statistics;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import ly.l33dr.assignment.entities.users.User;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Object representation of the statistic table
 * 
 */
@Entity
@Table(name = "statistics")
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    @Null(message = "'id' must be null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "'statName' cannot be null")
    private StatName statName;

    @Column(name = "value", nullable = false)
    @NotNull(message = "'value' cannot be null")
    @Min(value = 0, message = "'value' must be greater or equal to zero")
    private int value;

    @Column(name = "created_at", nullable = false)
    @NotNull(message = "'createdAt' cannot be null")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatName getStatName() {
        return statName;
    }

    public void setStatName(StatName statName) {
        this.statName = statName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
