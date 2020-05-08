package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Localization} entity.
 */
public class LocalizationDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String labelEn;

    @NotNull
    @Size(max = 255)
    private String labelDe;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelEn() {
        return labelEn;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelDe() {
        return labelDe;
    }

    public void setLabelDe(String labelDe) {
        this.labelDe = labelDe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalizationDTO localizationDTO = (LocalizationDTO) o;
        if (localizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalizationDTO{" +
            "id=" + getId() +
            ", labelEn='" + getLabelEn() + "'" +
            ", labelDe='" + getLabelDe() + "'" +
            "}";
    }
}
