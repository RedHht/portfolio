package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>, CrudRepository<Post, Long> {
    Post getPostById(Long paramLong);
}
