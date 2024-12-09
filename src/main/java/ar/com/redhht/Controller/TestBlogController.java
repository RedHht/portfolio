package ar.com.redhht.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestBlogController {
    @RequestMapping("/testblog")
    public String testBlog() {
        return "aaa";
    }
}
