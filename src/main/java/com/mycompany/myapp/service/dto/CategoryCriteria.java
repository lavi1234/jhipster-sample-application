package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Category} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter createdBy;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter nameId;

    private LongFilter descriptionId;

    private LongFilter parentId;

    private LongFilter userCategoryMappingId;

    public CategoryCriteria() {
    }

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.nameId = other.nameId == null ? null : other.nameId.copy();
        this.descriptionId = other.descriptionId == null ? null : other.descriptionId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.userCategoryMappingId = other.userCategoryMappingId == null ? null : other.userCategoryMappingId.copy();
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(IntegerFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getNameId() {
        return nameId;
    }

    public void setNameId(LongFilter nameId) {
        this.nameId = nameId;
    }

    public LongFilter getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(LongFilter descriptionId) {
        this.descriptionId = descriptionId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getUserCategoryMappingId() {
        return userCategoryMappingId;
    }

    public void setUserCategoryMappingId(LongFilter userCategoryMappingId) {
        this.userCategoryMappingId = userCategoryMappingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryCriteria that = (CategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(nameId, that.nameId) &&
            Objects.equals(descriptionId, that.descriptionId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(userCategoryMappingId, that.userCategoryMappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        createdAt,
        updatedAt,
        nameId,
        descriptionId,
        parentId,
        userCategoryMappingId
        );
    }

    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
                (descriptionId != null ? "descriptionId=" + descriptionId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (userCategoryMappingId != null ? "userCategoryMappingId=" + userCategoryMappingId + ", " : "") +
            "}";
    }

}
