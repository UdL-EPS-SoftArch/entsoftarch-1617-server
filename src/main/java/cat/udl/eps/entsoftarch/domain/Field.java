package cat.udl.eps.entsoftarch.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=@UniqueConstraint(columnNames={"partOf", "title"})
)
@Data
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;
    private String description;

    @ManyToOne
    @Column (name="partOf")
    private Schema partOf;
}
