package cat.udl.eps.entsoftarch.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
    @JsonBackReference
    private List<Dataset> owns = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Schema> owns_schemas = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<License> owns_openLicenses= new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<License> owns_closedLicenses= new ArrayList<>();

    @ManyToMany(mappedBy = "sharedWith", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Dataset> sharedDatasets;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_OWNER");
    }

    public List<Dataset> getOwns() { return owns; }

    public void setOwns(List<Dataset> owns) { this.owns = owns; }

    public List<Schema> getOwns_schemas() {
        return owns_schemas;
    }

    public void setOwns_schemas(List<Schema> owns_schemas) {
        this.owns_schemas = owns_schemas;
    }

    public List <License> getOwns_openLicenses() {
        return owns_openLicenses;
    }

    public void setOwns_openLicenses(List <License> owns_openLicenses) {
        this.owns_openLicenses=owns_openLicenses;
    }

    public List <License> getOwns_closedLicenses() {
        return owns_closedLicenses;
    }

    public void setOwns_closedLicenses(List <License> owns_closedLicenses) {
        this.owns_closedLicenses=owns_closedLicenses;
    }
}
