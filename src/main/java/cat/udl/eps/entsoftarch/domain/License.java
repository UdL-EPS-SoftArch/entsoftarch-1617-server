package cat.udl.eps.entsoftarch.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by victorserrate on 2/3/17.
 */

@Entity
public abstract class License {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private DataOwner owner;

    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text=text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public DataOwner getOwner() {
        return owner;
    }

    public void setOwner(DataOwner owner) {
        this.owner=owner;
    }


    @Override
    public String toString() {
        return "License{" +
                "text='" + text + '\'' +
                '}';
    }
}
