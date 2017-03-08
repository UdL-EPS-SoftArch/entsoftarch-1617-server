package cat.udl.eps.entsoftarch.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
public class OpenLicense extends License {

    @OneToMany(mappedBy = "openLicense", fetch = FetchType.EAGER)
    private List<Dataset> datasets = new ArrayList<>();

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "OpenLicense " + super.toString();
    }
}
