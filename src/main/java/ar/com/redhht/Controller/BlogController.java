package ar.com.redhht.Controller;

import ar.com.redhht.Model.Entity.Post;
import ar.com.redhht.Model.Table.PostRepository;
import ar.com.redhht.Model.Table.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path = {"/blog"})
public class BlogController {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Autowired
    BlogController(PostRepository repository, UserRepository userRepository) {
        this.postRepository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String handle(Model model, @RequestParam(required = false) Integer page) {
        if (page == null) page = 0;
        Page<Post> postPage = this.postRepository.findAll(Pageable.ofSize(10).withPage(page));
        model.addAttribute("postPage", postPage);
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog");
        return "blog/index";
    }

    @GetMapping(path = {"/view/{postId}"})
    public String view(Model model, @PathVariable Long postId) {
        Post post = this.postRepository.getPostById(postId);
        if (post == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");
        model.addAttribute("post", post);
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog - Post " + postId);
        return "blog/view";
    }

    @GetMapping(path = {"/new"})
    public String newPost(Model model, Authentication authentication) {
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog - Nuevo post ");
        if (authentication == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes crear un post sin iniciar sesion");
        return "blog/new";
    }

    @PostMapping(path = {"/new"})
    public String newPostForm(WebRequest webRequest, Authentication authentication) {
        Post post = new Post();
        if (authentication == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes crear un post sin iniciar sesion");
        String principal = authentication.getName();
        post.setBody(webRequest.getParameter("body"));
        post.setUser(this.userRepository.getByUser(principal));
        post.setTitle(webRequest.getParameter("title"));
        this.postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping(path = {"/edit/{postId}"})
    public String editPost(Model model, @PathVariable Long postId, Authentication authentication) {
        Post post = this.postRepository.getPostById(postId);
        if (post == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");
        if (!post.getUser().getUser().equals(authentication.getName()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes editar este post.");
        model.addAttribute(post);
        return "blog/edit";
    }

    @PostMapping(path = {"/edit/{postId}"})
    public String editPostForm(WebRequest webRequest, @PathVariable Long postId, Authentication authentication) {
        Post originalPost = this.postRepository.getPostById(postId);

        if (originalPost == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");

        if (!originalPost.getUser().getUser().equals(authentication.getName()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes editar este post.");

        originalPost.setBody(webRequest.getParameter("body"));
        originalPost.setTitle(webRequest.getParameter("title"));
        this.postRepository.save(originalPost);
        return "redirect:/blog";
    }

    @GetMapping(path = {"/delete/{postId}"})
    public String deletePostForm(@PathVariable Long postId, Authentication authentication) {
        Post originalPost = this.postRepository.getPostById(postId);
        if (originalPost == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El post no existe");
        if (!originalPost.getUser().getUser().equals(authentication.getName()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No puedes eliminar este post.");
        this.postRepository.delete(originalPost);
        return "redirect:/blog";
    }
}