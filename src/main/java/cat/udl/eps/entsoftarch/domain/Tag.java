package cat.udl.eps.entsoftarch.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francofriz on 6/3/17.
 */
@Entity
@Data
public class Tag implements Persistable<String>{
    @Id
    private String name;

    @Version
    private Long version;

    @ManyToMany(mappedBy = "taggedWith", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Dataset> tags = new ArrayList<>();

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return version == null;
    }
}
