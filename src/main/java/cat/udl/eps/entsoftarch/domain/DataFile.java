package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by ElTrioMaquinero on 4/25/17.
 */


@Data
@Entity
public class DataFile extends Dataset {
    private int downloads;

    @NotBlank
    private String filename;

    private String content;

    private String separator;

    @OneToMany
    @JsonIdentityReference(alwaysAsId = true)
    private List<DataUser> downloaded;
}
