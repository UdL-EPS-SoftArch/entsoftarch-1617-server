package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by UNI on 06/03/2017.
 */
@RepositoryRestResource
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    List<Tag> findByName(@Param("name") String name);
}
