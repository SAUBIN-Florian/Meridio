package dev.florian.meridio.dto;

import dev.florian.meridio.models.AclType;

public class AclDto {
    
    private String username;
    private Long spaceId;
    private AclType aclType;

    public AclDto(String username, Long spaceId, String type) {
        this.username = username;
        this.spaceId = spaceId;
        this.aclType = AclType.valueOf(type);
    }

    public String getUsername() {
        return this.username;
    }

    public Long getSpaceId() {
        return this.spaceId;
    }

    public AclType getAclType() {
        return this.aclType;
    }

    public void setAclType(AclType aclType) {
        this.aclType = aclType;
    }

    @Override
    public String toString() {
        return "AclDto [username=" + username + ", spaceId=" + spaceId + ", aclType=" + aclType + "]";
    }
    
}
