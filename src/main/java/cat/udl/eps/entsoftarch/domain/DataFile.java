package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ElTrioMaquinero on 4/25/17.
 */


@Entity
@Data
public class DataFile extends Dataset {
    private int downloads;

    @OneToMany
    @JsonIdentityReference(alwaysAsId = true)
    private List<DataUser> downloaded;
}
