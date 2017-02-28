package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.Dataset;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@RepositoryRestResource
public interface DatasetRepository extends PagingAndSortingRepository<Dataset, Long> {
    List<Dataset> findByDescription(@Param("description") String description);
    List<Dataset> findByDescriptionContaining(@Param("description") String description);
}
