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
 * Criteria class for the {@link com.mycompany.myapp.domain.Address} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter street;

    private StringFilter city;

    private StringFilter state;

    private StringFilter country;

    private StringFilter postalCode;

    private DoubleFilter geoLatitude;

    private DoubleFilter geoLongitude;

    public AddressCriteria() {
    }

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.geoLatitude = other.geoLatitude == null ? null : other.geoLatitude.copy();
        this.geoLongitude = other.geoLongitude == null ? null : other.geoLongitude.copy();
    }

    @Override
    public AddressCriteria copy() {
        return new AddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public DoubleFilter getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(DoubleFilter geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public DoubleFilter getGeoLongitude() {
        return geoLongitude;
    }

    public void setGeoLongitude(DoubleFilter geoLongitude) {
        this.geoLongitude = geoLongitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressCriteria that = (AddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(street, that.street) &&
            Objects.equals(city, that.city) &&
            Objects.equals(state, that.state) &&
            Objects.equals(country, that.country) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(geoLatitude, that.geoLatitude) &&
            Objects.equals(geoLongitude, that.geoLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        street,
        city,
        state,
        country,
        postalCode,
        geoLatitude,
        geoLongitude
        );
    }

    @Override
    public String toString() {
        return "AddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (geoLatitude != null ? "geoLatitude=" + geoLatitude + ", " : "") +
                (geoLongitude != null ? "geoLongitude=" + geoLongitude + ", " : "") +
            "}";
    }

}
