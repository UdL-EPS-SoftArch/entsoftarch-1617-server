package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table( uniqueConstraints =
    @UniqueConstraint( name = "FIELD_TITLE_UNIQUE_PER_SCHEMA", columnNames = {"part_of_id", "title"} ))
@Data
public class Field extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;
    private String description;

    @ManyToOne
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private Schema partOf;
}
