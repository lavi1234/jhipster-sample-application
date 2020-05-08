package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.AppFeatures} entity.
 */
public class AppFeaturesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer menuSortNumber;


    private Long nameId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenuSortNumber() {
        return menuSortNumber;
    }

    public void setMenuSortNumber(Integer menuSortNumber) {
        this.menuSortNumber = menuSortNumber;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long localizationId) {
        this.nameId = localizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppFeaturesDTO appFeaturesDTO = (AppFeaturesDTO) o;
        if (appFeaturesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appFeaturesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppFeaturesDTO{" +
            "id=" + getId() +
            ", menuSortNumber=" + getMenuSortNumber() +
            ", nameId=" + getNameId() +
            "}";
    }
}
