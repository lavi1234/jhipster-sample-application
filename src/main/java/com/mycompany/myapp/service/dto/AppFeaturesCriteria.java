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

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.AppFeatures} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AppFeaturesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /app-features?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppFeaturesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter menuSortNumber;

    private LongFilter nameId;

    private LongFilter subsriptionPlanFeatureId;

    public AppFeaturesCriteria() {
    }

    public AppFeaturesCriteria(AppFeaturesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.menuSortNumber = other.menuSortNumber == null ? null : other.menuSortNumber.copy();
        this.nameId = other.nameId == null ? null : other.nameId.copy();
        this.subsriptionPlanFeatureId = other.subsriptionPlanFeatureId == null ? null : other.subsriptionPlanFeatureId.copy();
    }

    @Override
    public AppFeaturesCriteria copy() {
        return new AppFeaturesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getMenuSortNumber() {
        return menuSortNumber;
    }

    public void setMenuSortNumber(IntegerFilter menuSortNumber) {
        this.menuSortNumber = menuSortNumber;
    }

    public LongFilter getNameId() {
        return nameId;
    }

    public void setNameId(LongFilter nameId) {
        this.nameId = nameId;
    }

    public LongFilter getSubsriptionPlanFeatureId() {
        return subsriptionPlanFeatureId;
    }

    public void setSubsriptionPlanFeatureId(LongFilter subsriptionPlanFeatureId) {
        this.subsriptionPlanFeatureId = subsriptionPlanFeatureId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppFeaturesCriteria that = (AppFeaturesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(menuSortNumber, that.menuSortNumber) &&
            Objects.equals(nameId, that.nameId) &&
            Objects.equals(subsriptionPlanFeatureId, that.subsriptionPlanFeatureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        menuSortNumber,
        nameId,
        subsriptionPlanFeatureId
        );
    }

    @Override
    public String toString() {
        return "AppFeaturesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (menuSortNumber != null ? "menuSortNumber=" + menuSortNumber + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
                (subsriptionPlanFeatureId != null ? "subsriptionPlanFeatureId=" + subsriptionPlanFeatureId + ", " : "") +
            "}";
    }

}
