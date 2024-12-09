package ar.com.redhht.Controller;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Entity.Post;
import ar.com.redhht.Model.Table.IPostRepository;
import ar.com.redhht.Model.Table.IUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
public class BlogRestController {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public BlogRestController(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private Optional<List<Post>> getPage(Long pageSize, Long page) {
        page -= 1; // Adjust the page to start from index 0

        Page<Post> postPage = postRepository.findAll(Pageable.ofSize(pageSize.intValue()).withPage(page.intValue()));
        if (postPage.isEmpty()) return Optional.empty();

        return Optional.of(postPage.getContent());
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @Operation(summary = "Get a page of posts.")
    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "A page of pageSize or less posts was found.")
    @ApiResponse(responseCode = "404", description = "An empty/invalid page was found.")
    public ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "10") Long pageSize,
                                               @RequestParam(defaultValue = "1") Long page) {
        return ResponseEntity.of(getPage(pageSize, page));
    }

    @Operation(summary = "Get a specific post.")
    @GetMapping("/get")
    @ApiResponse(responseCode = "200", description = "A post with that ID was found.")
    @ApiResponse(responseCode = "404", description = "No post with that ID found.")
    public ResponseEntity<Post> getPost(@RequestParam Long id) {
        Optional<Post> optionalPost = postRepository.getPostById(id.intValue());
        return ResponseEntity.of(optionalPost);
    }

    @Operation(summary = "Save (Create or Edit) a post.")
    @PostMapping("/save")
    @ApiResponse(responseCode = "200", description = "Post saved.")
    public ResponseEntity<Post> savePost(@RequestParam String title,
                                         @RequestParam String body,
                                         @RequestParam(required = false) Long id,
                                         Authentication authentication) {
        DbUser user = userRepository.getDbUserByUser(authentication.getName()).orElse(null);
        Post post = new Post();

        if (id != null) {
            Optional<Post> optionalPost = postRepository.getPostById(id.intValue());
            post = optionalPost.orElse(post);
        }

        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);

        return ResponseEntity.ok(postRepository.save(post));
    }
}
