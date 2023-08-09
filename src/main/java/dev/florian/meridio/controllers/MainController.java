package dev.florian.meridio.controllers;


import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.florian.meridio.models.Space;
import dev.florian.meridio.services.SpaceService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {
    
    private final SpaceService spaceService;

    public MainController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("current_user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping("/")
    public String index() {
        return "main/index";
    }

    @GetMapping("/spaces")
    public String allSpaces(Model model) {
        model.addAttribute("spaces", this.spaceService.findAll());
        return "spaces/spaces_all";
    }

    @GetMapping("/spaces/{id}")
    public String findOneSpace(@PathVariable Long id, Model model) {
        model.addAttribute("space", this.spaceService.findById(id));
        return "spaces/space_detail";
    }

    @GetMapping("/spaces/new")
    public String CreateSpace(Space space) {
        return "spaces/space_create";
    }

    @PostMapping("/spaces/new")
    public String CreateSpaceValidations(@Valid Space space, BindingResult validations, Principal principal) {
        if(validations.hasErrors()) {
            return "spaces/space_create";
        }
        this.spaceService.save(space, principal);
        return "redirect:/spaces";
    }
}
