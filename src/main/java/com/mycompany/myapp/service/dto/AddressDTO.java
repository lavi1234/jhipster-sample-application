package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Address} entity.
 */
public class AddressDTO implements Serializable {
    
    private Long id;

    @Size(max = 200)
    private String street;

    @Size(max = 200)
    private String city;

    @Size(max = 200)
    private String state;

    @Size(max = 200)
    private String country;

    @Size(max = 200)
    private String postalCode;

    @NotNull
    private Double geoLatitude;

    @NotNull
    private Double geoLongitude;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(Double geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public Double getGeoLongitude() {
        return geoLongitude;
    }

    public void setGeoLongitude(Double geoLongitude) {
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

        AddressDTO addressDTO = (AddressDTO) o;
        if (addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", geoLatitude=" + getGeoLatitude() +
            ", geoLongitude=" + getGeoLongitude() +
            "}";
    }
}
