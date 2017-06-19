package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.ClosedLicense;
import cat.udl.eps.entsoftarch.domain.DataOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<ClosedLicense> findByOwner(@Param("owner") DataOwner owner, Pageable pageable);
    List <ClosedLicense> findByTextContaining(@Param("text") String text);
}