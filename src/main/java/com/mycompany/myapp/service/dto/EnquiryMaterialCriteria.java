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
 * Criteria class for the {@link com.mycompany.myapp.domain.EnquiryMaterial} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EnquiryMaterialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enquiry-materials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnquiryMaterialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter dimension;

    private IntegerFilter materialId;

    private StringFilter color;

    private StringFilter comments;

    private LongFilter enquiryDetailsId;

    public EnquiryMaterialCriteria() {
    }

    public EnquiryMaterialCriteria(EnquiryMaterialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.dimension = other.dimension == null ? null : other.dimension.copy();
        this.materialId = other.materialId == null ? null : other.materialId.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.enquiryDetailsId = other.enquiryDetailsId == null ? null : other.enquiryDetailsId.copy();
    }

    @Override
    public EnquiryMaterialCriteria copy() {
        return new EnquiryMaterialCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDimension() {
        return dimension;
    }

    public void setDimension(StringFilter dimension) {
        this.dimension = dimension;
    }

    public IntegerFilter getMaterialId() {
        return materialId;
    }

    public void setMaterialId(IntegerFilter materialId) {
        this.materialId = materialId;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public LongFilter getEnquiryDetailsId() {
        return enquiryDetailsId;
    }

    public void setEnquiryDetailsId(LongFilter enquiryDetailsId) {
        this.enquiryDetailsId = enquiryDetailsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnquiryMaterialCriteria that = (EnquiryMaterialCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(dimension, that.dimension) &&
            Objects.equals(materialId, that.materialId) &&
            Objects.equals(color, that.color) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(enquiryDetailsId, that.enquiryDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        dimension,
        materialId,
        color,
        comments,
        enquiryDetailsId
        );
    }

    @Override
    public String toString() {
        return "EnquiryMaterialCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (dimension != null ? "dimension=" + dimension + ", " : "") +
                (materialId != null ? "materialId=" + materialId + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (enquiryDetailsId != null ? "enquiryDetailsId=" + enquiryDetailsId + ", " : "") +
            "}";
    }

}
