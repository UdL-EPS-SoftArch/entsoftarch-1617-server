package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.OpenLicense;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by victorserrate on 2/3/17.
 */

@RepositoryRestResource
public interface OpenLicenseRepository extends PagingAndSortingRepository <OpenLicense, Long> {
    List <OpenLicense> findByText(@Param("text") String text);
    List <OpenLicense> findByTextContaining(@Param("text") String text);
}
