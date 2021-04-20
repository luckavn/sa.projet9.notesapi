package com.axa.softwareacademy.notes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@CrossOrigin
public class IndexController {

    /**
     * This controller is aimed to redirect any call to localhost root /
     * @return a redirection to live documentation swagger-ui.html
     */
    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/swagger-ui.html");
    }
}
