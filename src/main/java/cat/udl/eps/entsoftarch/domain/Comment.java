package cat.udl.eps.entsoftarch.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by santi on 6/03/17.
 */
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 2096, message = "Comment maximum text lenght is {max} characters long")
    private String txt;

    @ManyToOne
    @NotNull
    private Dataset comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Dataset getComments() {
        return comments;
    }

    public void setComments(Dataset comments) {
        this.comments = comments;
    }
}
