package cat.udl.eps.entsoftarch.domain;

/**
 * Created by victorserrate on 2/3/17.
 */

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClosedLicense extends License {

    private double price;

    @OneToMany(mappedBy = "closedLicense", fetch = FetchType.EAGER)
    private List<Dataset> datasets = new ArrayList<>();

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price=price;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }
}
