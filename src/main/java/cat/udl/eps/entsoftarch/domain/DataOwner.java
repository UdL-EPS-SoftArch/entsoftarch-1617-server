package cat.udl.eps.entsoftarch.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class DataOwner extends User {

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Dataset> owns = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_OWNER");
    }

    public List<Dataset> getOwns() { return owns; }

    public void setOwns(List<Dataset> owns) { this.owns = owns; }
}
