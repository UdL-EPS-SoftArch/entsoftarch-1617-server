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
 * Created by gerard on 28/02/17.
 */
@Entity
@Data
public class Schema extends UriEntity<Long> {
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

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private DataOwner owner;

    private String ownerId;


    @OneToMany(mappedBy = "schema", fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Dataset> datasets = new ArrayList<>();

    @OneToMany(mappedBy = "partOf", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Field> contains;
}
