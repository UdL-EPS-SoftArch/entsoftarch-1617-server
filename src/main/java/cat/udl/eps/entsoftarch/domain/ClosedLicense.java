package cat.udl.eps.entsoftarch.domain;

/**
 * Created by victorserrate on 2/3/17.
 */

import javax.persistence.Entity;

@Entity
public class ClosedLicense extends License {

    private double price;

    public ClosedLicense(String text, double price) {
        super(text);
        this.price = price;
    }
}
