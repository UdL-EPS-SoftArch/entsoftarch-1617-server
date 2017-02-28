package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
@JsonIgnoreProperties(value = {"dateTime"}, allowGetters = true)
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime dateTime;

    private boolean isBlocked = false;

    private int flags = 0;

    private String owner;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isBlocked() { return isBlocked; }

    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    public int getFlags() { return flags; }

    public void setFlags(int flags) { this.flags = flags; }

    public String getOwner() { return owner; }

    public void setOwner(String owner) { this.owner = owner; }

    public String getTitle() {return title; }

    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "Dataset{" +
                "id=" + id +
                "title=" + title +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", isBlocked=" + isBlocked +
                ", flags=" + flags +
                ", owner='" + owner + '\'' +
                '}';
    }

}
