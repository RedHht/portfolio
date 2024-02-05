package ar.com.redhht.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping(path = "/error")
    public String handle(Model model, HttpServletRequest request) {
        model.addAttribute("code", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        return "error";
    }

}
