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
 * Criteria class for the {@link com.mycompany.myapp.domain.StaticPages} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StaticPagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /static-pages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StaticPagesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter pageTitle;

    private StringFilter heading;

    private StringFilter description;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private BooleanFilter publish;

    public StaticPagesCriteria() {
    }

    public StaticPagesCriteria(StaticPagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pageTitle = other.pageTitle == null ? null : other.pageTitle.copy();
        this.heading = other.heading == null ? null : other.heading.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.publish = other.publish == null ? null : other.publish.copy();
    }

    @Override
    public StaticPagesCriteria copy() {
        return new StaticPagesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(StringFilter pageTitle) {
        this.pageTitle = pageTitle;
    }

    public StringFilter getHeading() {
        return heading;
    }

    public void setHeading(StringFilter heading) {
        this.heading = heading;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public BooleanFilter getPublish() {
        return publish;
    }

    public void setPublish(BooleanFilter publish) {
        this.publish = publish;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StaticPagesCriteria that = (StaticPagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(pageTitle, that.pageTitle) &&
            Objects.equals(heading, that.heading) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(publish, that.publish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        pageTitle,
        heading,
        description,
        createdAt,
        updatedAt,
        publish
        );
    }

    @Override
    public String toString() {
        return "StaticPagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (pageTitle != null ? "pageTitle=" + pageTitle + ", " : "") +
                (heading != null ? "heading=" + heading + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (publish != null ? "publish=" + publish + ", " : "") +
            "}";
    }

}
