package ar.com.redhht.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorrController implements ErrorController {
    @RequestMapping(path = {"/error"})
    public String handle(Model model, HttpServletRequest request) {
        model.addAttribute("code", request.getAttribute("jakarta.servlet.error.status_code"));
        return "error";
    }
}
