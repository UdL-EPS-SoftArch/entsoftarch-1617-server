package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.ClosedLicense;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Victor on 03/03/2017.
 */
@RepositoryRestResource
public interface ClosedLicenseRepository extends PagingAndSortingRepository <ClosedLicense, Long> {
    List <ClosedLicense> findByText(@Param("text") String text);
    List <ClosedLicense> findByOwnerId(@Param("ownerId") String ownerId);
    List <ClosedLicense> findByTextContaining(@Param("text") String text);
}