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
 * Criteria class for the {@link com.mycompany.myapp.domain.Localization} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.LocalizationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /localizations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocalizationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter labelEn;

    private StringFilter labelDe;

    public LocalizationCriteria() {
    }

    public LocalizationCriteria(LocalizationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.labelEn = other.labelEn == null ? null : other.labelEn.copy();
        this.labelDe = other.labelDe == null ? null : other.labelDe.copy();
    }

    @Override
    public LocalizationCriteria copy() {
        return new LocalizationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLabelEn() {
        return labelEn;
    }

    public void setLabelEn(StringFilter labelEn) {
        this.labelEn = labelEn;
    }

    public StringFilter getLabelDe() {
        return labelDe;
    }

    public void setLabelDe(StringFilter labelDe) {
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
        final LocalizationCriteria that = (LocalizationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(labelEn, that.labelEn) &&
            Objects.equals(labelDe, that.labelDe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        labelEn,
        labelDe
        );
    }

    @Override
    public String toString() {
        return "LocalizationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (labelEn != null ? "labelEn=" + labelEn + ", " : "") +
                (labelDe != null ? "labelDe=" + labelDe + ", " : "") +
            "}";
    }

}
