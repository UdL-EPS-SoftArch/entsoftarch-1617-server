package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private DataOwner owner;

    @ManyToOne
    private License license;
}
