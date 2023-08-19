package dev.florian.meridio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.florian.meridio.dto.AclDto;
import dev.florian.meridio.dto.FriendDto;
import dev.florian.meridio.models.Profile;
import dev.florian.meridio.models.Space;
import dev.florian.meridio.services.AclService;
import dev.florian.meridio.services.ProfileService;
import dev.florian.meridio.services.SpaceService;

@Controller
public class SearchController {
    
    private ProfileService profileService;
    private SpaceService spaceService;
    private AclService aclService;

    @Autowired
    public SearchController(ProfileService profileService, SpaceService spaceService, AclService aclService) {
        this.profileService = profileService;
        this.spaceService = spaceService;
        this.aclService = aclService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("current_user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping("spaces/{spaceId}/add_users")
    public String searchForFriends(@PathVariable Long spaceId, Model model) {
        return "spaces/add_friends_to_space";
    }

    @PostMapping("spaces/{spaceId}/add_users")
    public ResponseEntity<Void> addFriendToSpace(@PathVariable Long spaceId, @RequestBody AclDto aclDto) {
        Profile friend = this.profileService.findByUsername(aclDto.getUsername());
        Space currentSpace = this.spaceService.findById(spaceId);
        this.aclService.save(aclDto, friend, currentSpace);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/search")
    @ResponseBody
    public List<FriendDto> searchProfiles(@RequestParam String query) {
        List<Profile> profiles = profileService.searchProfiles(query);
        List<FriendDto> friends = profiles.stream().map(profile -> new FriendDto(profile.getId(), profile.getUsername(), profile.getSpaces())).toList();
        return friends;
    }
}
