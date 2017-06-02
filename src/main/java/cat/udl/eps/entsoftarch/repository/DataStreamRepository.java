package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.DataStream;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by SergiGrau on 31/5/17.
 */
public interface DataStreamRepository extends PagingAndSortingRepository<DataStream, Long> {
}
