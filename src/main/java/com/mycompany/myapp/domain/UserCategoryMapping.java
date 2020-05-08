package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserCategoryMapping.
 */
@Entity
@Table(name = "user_category_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserCategoryMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "user_category_mapping_user_profile",
               joinColumns = @JoinColumn(name = "user_category_mapping_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "id"))
    private Set<UserProfile> userProfiles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "user_category_mapping_category",
               joinColumns = @JoinColumn(name = "user_category_mapping_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public UserCategoryMapping userProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    public UserCategoryMapping addUserProfile(UserProfile userProfile) {
        this.userProfiles.add(userProfile);
        userProfile.getUserCategoryMappings().add(this);
        return this;
    }

    public UserCategoryMapping removeUserProfile(UserProfile userProfile) {
        this.userProfiles.remove(userProfile);
        userProfile.getUserCategoryMappings().remove(this);
        return this;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public UserCategoryMapping categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public UserCategoryMapping addCategory(Category category) {
        this.categories.add(category);
        category.getUserCategoryMappings().add(this);
        return this;
    }

    public UserCategoryMapping removeCategory(Category category) {
        this.categories.remove(category);
        category.getUserCategoryMappings().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCategoryMapping)) {
            return false;
        }
        return id != null && id.equals(((UserCategoryMapping) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserCategoryMapping{" +
            "id=" + getId() +
            "}";
    }
}
