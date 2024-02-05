package ar.com.redhht.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/blog")
public class BlogController {
    
    @RequestMapping(path = "")
    public String handle(Model model) {
        model.addAttribute("page", "blog");
        model.addAttribute("title", "Blog");
        return "blog";
    }

}
