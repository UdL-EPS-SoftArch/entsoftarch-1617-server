package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.License;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Victor on 03/03/2017.
 */
@RepositoryRestResource
public interface LicenseRepository extends PagingAndSortingRepository<License, Long> {
}
