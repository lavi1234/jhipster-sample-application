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
 * Criteria class for the {@link com.mycompany.myapp.domain.TradingHours} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TradingHoursResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trading-hours?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TradingHoursCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter day;

    private StringFilter startTime;

    private StringFilter endTime;

    private LongFilter companyId;

    public TradingHoursCriteria() {
    }

    public TradingHoursCriteria(TradingHoursCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public TradingHoursCriteria copy() {
        return new TradingHoursCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDay() {
        return day;
    }

    public void setDay(StringFilter day) {
        this.day = day;
    }

    public StringFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(StringFilter startTime) {
        this.startTime = startTime;
    }

    public StringFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(StringFilter endTime) {
        this.endTime = endTime;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TradingHoursCriteria that = (TradingHoursCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        day,
        startTime,
        endTime,
        companyId
        );
    }

    @Override
    public String toString() {
        return "TradingHoursCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (day != null ? "day=" + day + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
