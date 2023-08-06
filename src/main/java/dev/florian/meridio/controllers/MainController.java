package dev.florian.meridio.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.florian.meridio.services.SpaceService;

@Controller
@RequestMapping("/")
public class MainController {
    
    private final SpaceService spaceService;

    public MainController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping("/")
    public String index(Principal principal) {
        return "main/index";
    }

    @GetMapping("/spaces")
    public String allSpaces(Model model) {
        model.addAttribute("spaces", this.spaceService.findAll());
        return "spaces/spaces_all";
    }
}
