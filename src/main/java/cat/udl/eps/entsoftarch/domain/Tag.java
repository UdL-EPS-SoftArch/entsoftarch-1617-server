package cat.udl.eps.entsoftarch.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

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

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return version == null;
    }
}
