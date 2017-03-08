package cat.udl.eps.entsoftarch.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
@Data
public class OpenLicense extends License {

    @OneToMany(mappedBy = "openLicense", fetch = FetchType.EAGER)
    private List<Dataset> datasets = new ArrayList<>();

    @Override
    public String toString() {
        return "OpenLicense " + super.toString();
    }
}
