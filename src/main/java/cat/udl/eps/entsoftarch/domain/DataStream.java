package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by SergiGrau on 29/5/17.
 */
@Data
@Entity
public class DataStream extends Dataset{
    private int subscribers;

    @NotBlank
    private String streamname;

    private String content;

    private String separator;

    @OneToMany
    @JsonIdentityReference(alwaysAsId = true)
    private List<DataUser> subscribed;
}

