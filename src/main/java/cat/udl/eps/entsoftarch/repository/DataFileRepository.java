package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.DataFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by ElTrioMaquinero on 4/25/17.
 */

@RepositoryRestResource
public interface DataFileRepository extends PagingAndSortingRepository<DataFile, Long> {
}
