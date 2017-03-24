package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francofriz on 6/3/17.
 */
@Entity
@Data
@ToString(exclude = "tags")
@EqualsAndHashCode(exclude = "tags", callSuper = false)
public class Tag extends UriEntity<String>{
    @Id
    private String name;

    @ManyToMany(mappedBy = "taggedWith", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Dataset> tags = new ArrayList<>();

    @Override
    public String getId() { return name; }
}
