package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorserrate on 2/3/17.
 */

@Data
@Entity
public abstract class License {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private DataOwner owner;

    @NotBlank
    private String text;

    @OneToMany(mappedBy = "license", fetch = FetchType.EAGER)
    private List<Dataset> datasets = new ArrayList<>();

    @OneToMany(mappedBy = "partOf", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Field> contains;

    @Override
    public String toString() {
        return "License{" +
                "text='" + text + '\'' +
                '}';
    }
}
