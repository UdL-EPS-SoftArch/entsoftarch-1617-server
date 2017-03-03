package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
public abstract class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "License{" +
                "text='" + text + '\'' +
                '}';
    }
}
