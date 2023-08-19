package dev.florian.meridio.dto;

import java.util.List;

import dev.florian.meridio.models.Space;

public class FriendDto {
    
    private final Long id;
    private final String username;
    private final List<Space> spaces;

    public FriendDto(Long id, String username, List<Space> spaces) {
        this.id = id;
        this.username = username;
        this.spaces = spaces;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
