package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.License;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by victorserrate on 2/3/17.
 */

@RepositoryRestResource
public interface LicenseRepository extends PagingAndSortingRepository<License, String> {
    List<License> findByText(@Param("text")String text);
}
