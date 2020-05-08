package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.UserCategoryMapping} entity.
 */
public class UserCategoryMappingDTO implements Serializable {
    
    private Long id;

    private Set<UserProfileDTO> userProfiles = new HashSet<>();
    private Set<CategoryDTO> categories = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserProfileDTO> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfileDTO> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserCategoryMappingDTO userCategoryMappingDTO = (UserCategoryMappingDTO) o;
        if (userCategoryMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userCategoryMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserCategoryMappingDTO{" +
            "id=" + getId() +
            ", userProfiles='" + getUserProfiles() + "'" +
            ", categories='" + getCategories() + "'" +
            "}";
    }
}
