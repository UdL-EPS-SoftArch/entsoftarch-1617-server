package cat.udl.eps.entsoftarch.domain;

import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
@Data
public class OpenLicense extends License {

    @Override
    public String toString() {
        return "OpenLicense " + super.toString();
    }
}
