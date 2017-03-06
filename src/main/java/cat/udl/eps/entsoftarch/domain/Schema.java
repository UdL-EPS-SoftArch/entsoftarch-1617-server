package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * Created by gerard on 28/02/17.
 */
@Entity
@Data
public class Schema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime lastModified;

    @ManyToOne
    @JsonBackReference
    private DataOwner owner;

}
