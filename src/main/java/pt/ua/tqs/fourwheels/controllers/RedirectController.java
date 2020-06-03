package pt.ua.tqs.fourwheels.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/")
public class RedirectController {

    @GetMapping("/")
    @ApiIgnore
    public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model) {
        return new ModelAndView("redirect:/swagger-ui.html", model);
    }
}