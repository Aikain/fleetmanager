package fi.eatech.fleetmanagerws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * .
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String redirect() {
        return "redirect:/swagger-ui.html";
    }
}
