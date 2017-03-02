package cat.udl.eps.entsoftarch.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */

@Entity
@Data
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ReadOnlyProperty
    private ZonedDateTime dateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime lastModified;
    private boolean isBlocked = false;
    private int flags = 0;
    @ManyToOne
    private DataOwner owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataOwner getOwner() {
        return owner;
    }

    public void setOwner(DataOwner owner) {
        this.owner = owner;
    }

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
        this.lastModified = dateTime;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", isBlocked=" + isBlocked +
                ", flags=" + flags +
                ", owner='" + owner.toString() + '\'' +
                '}';
    }
}
