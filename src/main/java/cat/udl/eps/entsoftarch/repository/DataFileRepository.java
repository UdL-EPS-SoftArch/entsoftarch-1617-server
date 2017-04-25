package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.DataFile;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ElTrioMaquinero on 4/25/17.
 */

public interface DataFileRepository extends PagingAndSortingRepository<DataFile, Long> {
}
