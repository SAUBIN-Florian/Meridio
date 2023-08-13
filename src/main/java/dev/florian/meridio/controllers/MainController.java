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

import dev.florian.meridio.models.File;
import dev.florian.meridio.models.Space;
import dev.florian.meridio.services.FileService;
import dev.florian.meridio.services.SpaceService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {
    
    private final SpaceService spaceService;
    private final FileService fileService;

    public MainController(SpaceService spaceService, FileService fileService) {
        this.spaceService = spaceService;
        this.fileService = fileService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("current_user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("activePage", "index");
        return "main/index";
    }

    @GetMapping("/spaces")
    public String allSpaces(Model model) {
        model.addAttribute("spaces", this.spaceService.findAll());
        model.addAttribute("activePage", "spaces_list");
        return "spaces/spaces_all";
    }

    @GetMapping("/spaces/{id}")
    public String detailSpace(@PathVariable Long id, Model model) {
        model.addAttribute("space", this.spaceService.findById(id));
        return "spaces/space_detail";
    }

    @GetMapping("/spaces/new")
    public String CreateSpace(Model model, Space space) {
        model.addAttribute("activePage", "space_new");
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

    @PostMapping("/spaces/{id}/delete")
    public String deleteSpace(@PathVariable Long id) {
        try {
            this.spaceService.deleteOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/spaces";
    }

    @GetMapping("/spaces/{spaceId}/file/{fileId}")
    public String detailFile(@PathVariable Long spaceId, @PathVariable Long fileId, Model model) {
        model.addAttribute("file", this.fileService.findById(fileId));
        return "files/file_detail";
    }

    @GetMapping("/spaces/{spaceId}/file/new")
    public String createFile(@PathVariable Long spaceId, File file, Model model) {
        model.addAttribute("space", this.spaceService.findById(spaceId));
        return "files/file_create";
    }

    @PostMapping("/spaces/{spaceId}/file/new")
    public String createFileValidations(@PathVariable Long spaceId, @Valid File file, 
                                        BindingResult validations, Model model, Principal principal) {
        if(validations.hasErrors()) {
            return "files/file_create";
        } else {
            model.addAttribute("space", this.spaceService.findById(spaceId));
            this.fileService.save(file, principal);
            return "files/file_create";
        }
    }
}
