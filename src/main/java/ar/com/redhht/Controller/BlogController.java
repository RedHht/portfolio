package ar.com.redhht.Controller;

import ar.com.redhht.Model.Entity.Post;
import ar.com.redhht.Model.Table.PostRepository;
import ar.com.redhht.Model.Table.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

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

    @GetMapping(path = "/view/{post}")
    public String view(Model model, @PathVariable(value="post") int numerito) {
        Post post = postRepository.getById(numerito);

        model.addAttribute("post", post);
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog - Post " + numerito);
        return "blog/view";
    }

    @GetMapping(path = "/new")
    public String newPost(Model model) {
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog - Nuevo post ");
        return "blog/new";
    }

    @PostMapping(path = "/new")
    public String newPostForm(WebRequest webRequest, UserRepository userRepository) {
        Post post = new Post();
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();

        post.setBody(webRequest.getParameter("body"));
        post.setUser(this.userRepository.getByUser(principal));
        post.setTitle(webRequest.getParameter("title"));

        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping(path = "/edit/{id}")
    public String editPost(Model model, @PathVariable String id) {
        Post post = postRepository.getById(Integer.parseInt(id));

        model.addAttribute(post);
        return "blog/edit";
    }

    @PostMapping(path = "/edit/{id}")
    public String editPostForm(WebRequest webRequest, @PathVariable String id) {
        Post post = new Post();

        post.setId(Integer.parseInt(id));
        post.setBody(webRequest.getParameter("body"));
        //post.setAuthor(webRequest.getParameter("author"));
        post.setTitle(webRequest.getParameter("title"));

        postRepository.update(post);

        return "redirect:/blog";
    }

}
