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
 * Criteria class for the {@link com.mycompany.myapp.domain.EnquiryNote} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EnquiryNoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enquiry-notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnquiryNoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter note;

    private InstantFilter createdAt;

    private LongFilter enquiryDetailsId;

    public EnquiryNoteCriteria() {
    }

    public EnquiryNoteCriteria(EnquiryNoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.enquiryDetailsId = other.enquiryDetailsId == null ? null : other.enquiryDetailsId.copy();
    }

    @Override
    public EnquiryNoteCriteria copy() {
        return new EnquiryNoteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
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
        final EnquiryNoteCriteria that = (EnquiryNoteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(note, that.note) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(enquiryDetailsId, that.enquiryDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        note,
        createdAt,
        enquiryDetailsId
        );
    }

    @Override
    public String toString() {
        return "EnquiryNoteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (enquiryDetailsId != null ? "enquiryDetailsId=" + enquiryDetailsId + ", " : "") +
            "}";
    }

}
