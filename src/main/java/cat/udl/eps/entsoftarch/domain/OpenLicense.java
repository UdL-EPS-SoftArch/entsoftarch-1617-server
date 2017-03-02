package cat.udl.eps.entsoftarch.domain;

import javax.persistence.Entity;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
public class OpenLicense extends License {

    public OpenLicense(String text) {
        super(text);
    }
}
