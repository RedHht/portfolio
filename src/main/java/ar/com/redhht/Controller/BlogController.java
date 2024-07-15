package ar.com.redhht.Controller;

import ar.com.redhht.Model.Entity.Post;
import ar.com.redhht.Model.Table.PostRepository;
import ar.com.redhht.Model.Table.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping(path = "/blog")
public class BlogController{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    BlogController(PostRepository repository, UserRepository userRepository) {
        this.postRepository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "")
    public String handle(Model model) {
        List<Post> posts = postRepository.getAll();

        model.addAttribute("posts", posts);
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog");
        return "blog/index";
    }

    @GetMapping(path = "/view/{postId}")
    public String view(Model model, @PathVariable int postId) {
        Post post = postRepository.getById(postId);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");
        }

        model.addAttribute("post", post);
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog - Post " + postId);
        return "blog/view";
    }

    @GetMapping(path = "/new")
    public String newPost(Model model, Authentication authentication) {
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog - Nuevo post ");

        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes crear un post sin iniciar sesión.");
        }

        return "blog/new";
    }

    @PostMapping(path = "/new")
    public String newPostForm(WebRequest webRequest, Authentication authentication) {
        Post post = new Post();

        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes crear un post sin iniciar sesión.");
        }

        String principal = authentication.getName();

        post.setBody(webRequest.getParameter("body"));
        post.setUser(this.userRepository.getByUser(principal));
        post.setTitle(webRequest.getParameter("title"));

        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping(path = "/edit/{postId}")
    public String editPost(Model model, @PathVariable int postId, Authentication authentication) {
        Post post = postRepository.getById(postId);

        if (!post.getUser().getUser().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes editar este post.");
        }

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");
        }

        model.addAttribute(post);
        return "blog/edit";
    }

    @PostMapping(path = "/edit/{postId}")
    public String editPostForm(WebRequest webRequest, @PathVariable int postId, Authentication authentication) {
        Post originalPost = postRepository.getById(postId);

        if (!originalPost.getUser().getUser().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes editar este post.");
        }

        if (originalPost == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");
        }

        originalPost.setBody(webRequest.getParameter("body"));
        originalPost.setTitle(webRequest.getParameter("title"));

        postRepository.update(originalPost);

        return "redirect:/blog";
    }

}
