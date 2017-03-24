package cat.udl.eps.entsoftarch.domain;

/**
 * Created by victorserrate on 2/3/17.
 */

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class ClosedLicense extends License {

    private double price;
}
