package cat.udl.eps.entsoftarch.repository;

import cat.udl.eps.entsoftarch.domain.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Chaimae
 */
@RepositoryRestResource
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    
}
