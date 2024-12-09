package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IPostRepository extends PagingAndSortingRepository<Post, Long>, CrudRepository<Post, Long> {
    Optional<Post> getPostById(int id);
}
