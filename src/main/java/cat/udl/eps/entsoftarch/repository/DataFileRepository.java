package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.DataFile;
import cat.udl.eps.entsoftarch.domain.Dataset;
import cat.udl.eps.entsoftarch.domain.Schema;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by ElTrioMaquinero on 4/25/17.
 */

@RepositoryRestResource
public interface DataFileRepository extends PagingAndSortingRepository<DataFile, Long> {
    List<DataFile> findByDescriptionContaining(@Param("description") String description);
    List<DataFile> findByDescriptionContainingAndSchema(@Param("description") String description,
                                                        @Param("schema") Schema schema);
}
