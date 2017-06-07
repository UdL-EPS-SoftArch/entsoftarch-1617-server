package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
@Data
public abstract class License extends UriEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String text;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private DataOwner owner;

    private String ownerId;

    @OneToMany(mappedBy = "license", fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Dataset> datasets = new ArrayList<>();
}
