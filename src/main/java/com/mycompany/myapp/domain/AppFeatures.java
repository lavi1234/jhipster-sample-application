package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A AppFeatures.
 */
@Entity
@Table(name = "app_features")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppFeatures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "menu_sort_number", nullable = false)
    private Integer menuSortNumber;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("appFeatures")
    private Localization name;

    @ManyToMany(mappedBy = "appFeatures")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<SubsriptionPlanFeature> subsriptionPlanFeatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenuSortNumber() {
        return menuSortNumber;
    }

    public AppFeatures menuSortNumber(Integer menuSortNumber) {
        this.menuSortNumber = menuSortNumber;
        return this;
    }

    public void setMenuSortNumber(Integer menuSortNumber) {
        this.menuSortNumber = menuSortNumber;
    }

    public Localization getName() {
        return name;
    }

    public AppFeatures name(Localization localization) {
        this.name = localization;
        return this;
    }

    public void setName(Localization localization) {
        this.name = localization;
    }

    public Set<SubsriptionPlanFeature> getSubsriptionPlanFeatures() {
        return subsriptionPlanFeatures;
    }

    public AppFeatures subsriptionPlanFeatures(Set<SubsriptionPlanFeature> subsriptionPlanFeatures) {
        this.subsriptionPlanFeatures = subsriptionPlanFeatures;
        return this;
    }

    public AppFeatures addSubsriptionPlanFeature(SubsriptionPlanFeature subsriptionPlanFeature) {
        this.subsriptionPlanFeatures.add(subsriptionPlanFeature);
        subsriptionPlanFeature.getAppFeatures().add(this);
        return this;
    }

    public AppFeatures removeSubsriptionPlanFeature(SubsriptionPlanFeature subsriptionPlanFeature) {
        this.subsriptionPlanFeatures.remove(subsriptionPlanFeature);
        subsriptionPlanFeature.getAppFeatures().remove(this);
        return this;
    }

    public void setSubsriptionPlanFeatures(Set<SubsriptionPlanFeature> subsriptionPlanFeatures) {
        this.subsriptionPlanFeatures = subsriptionPlanFeatures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppFeatures)) {
            return false;
        }
        return id != null && id.equals(((AppFeatures) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AppFeatures{" +
            "id=" + getId() +
            ", menuSortNumber=" + getMenuSortNumber() +
            "}";
    }
}
