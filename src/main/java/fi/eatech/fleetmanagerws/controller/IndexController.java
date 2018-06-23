package fi.eatech.fleetmanagerws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Index controller, redirect index / to swagger-ui.html
 *
 * @author Ville Nupponen
 * @since 1.0.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String redirect() {
        return "redirect:/swagger-ui.html";
    }
}
