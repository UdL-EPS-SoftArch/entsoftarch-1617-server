package cat.udl.eps.entsoftarch.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table( uniqueConstraints =
    @UniqueConstraint( name = "FIELD_TITLE_UNIQUE_PER_SCHEMA", columnNames = {"part_of_id", "title"} ))
@Data
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;
    private String description;

    @ManyToOne
    private Schema partOf;
}
