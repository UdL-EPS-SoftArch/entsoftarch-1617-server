package cat.udl.eps.entsoftarch.domain;

import javax.persistence.Entity;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
public abstract class License {

    public String text;

    public License(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
