package ar.com.redhht.Controller;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Table.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(path = "/")
    public String index(Model model) {
        model.addAttribute("page", "home");
        model.addAttribute("title", "Inicio");
        return "home";
    }

    @RequestMapping(path = "/about-me")
    public String handle(Model model) {
        model.addAttribute("page", "about-me");
        model.addAttribute("title", "Acerca de mi");
        return "about-me";
    }

}
