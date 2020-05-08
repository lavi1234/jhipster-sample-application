package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("categories")
    private Localization name;

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Localization description;

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Category parent;

    @ManyToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<UserCategoryMapping> userCategoryMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Category createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Category createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Category updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Localization getName() {
        return name;
    }

    public Category name(Localization localization) {
        this.name = localization;
        return this;
    }

    public void setName(Localization localization) {
        this.name = localization;
    }

    public Localization getDescription() {
        return description;
    }

    public Category description(Localization localization) {
        this.description = localization;
        return this;
    }

    public void setDescription(Localization localization) {
        this.description = localization;
    }

    public Category getParent() {
        return parent;
    }

    public Category parent(Category category) {
        this.parent = category;
        return this;
    }

    public void setParent(Category category) {
        this.parent = category;
    }

    public Set<UserCategoryMapping> getUserCategoryMappings() {
        return userCategoryMappings;
    }

    public Category userCategoryMappings(Set<UserCategoryMapping> userCategoryMappings) {
        this.userCategoryMappings = userCategoryMappings;
        return this;
    }

    public Category addUserCategoryMapping(UserCategoryMapping userCategoryMapping) {
        this.userCategoryMappings.add(userCategoryMapping);
        userCategoryMapping.getCategories().add(this);
        return this;
    }

    public Category removeUserCategoryMapping(UserCategoryMapping userCategoryMapping) {
        this.userCategoryMappings.remove(userCategoryMapping);
        userCategoryMapping.getCategories().remove(this);
        return this;
    }

    public void setUserCategoryMappings(Set<UserCategoryMapping> userCategoryMappings) {
        this.userCategoryMappings = userCategoryMappings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
