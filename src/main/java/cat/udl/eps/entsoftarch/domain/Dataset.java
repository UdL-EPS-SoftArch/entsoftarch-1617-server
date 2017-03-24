package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by http://rhizomik.net/~roberto/
 */

@Entity
@Data
public class Dataset extends UriEntity<Long> {
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
    @LastModifiedDate
    private ZonedDateTime lastModified;

    private boolean isBlocked = false;

    private int flags = 0;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private DataOwner owner;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Schema schema;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private License license;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIdentityReference(alwaysAsId = true)
    private List<User> sharedWith = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Tag> taggedWith = new ArrayList<>();

    @Override
    public String toString() {
        return "Dataset{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", lastModified=" + lastModified +
                ", isBlocked=" + isBlocked +
                ", flags=" + flags +
                ", owner=" + owner +
                ", schema=" + schema +
                ", license=" + license +
                ", sharedWith=" + sharedWith +
                ", taggedWith=" + taggedWith +
                '}';
    }
}