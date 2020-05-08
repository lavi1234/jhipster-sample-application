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
 * Criteria class for the {@link com.mycompany.myapp.domain.UserProfile} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.UserProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter salutation;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter profilePicture;

    private StringFilter phoneNumber;

    private StringFilter userType;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter userId;

    private LongFilter companyId;

    private LongFilter userCategoryMappingId;

    public UserProfileCriteria() {
    }

    public UserProfileCriteria(UserProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.salutation = other.salutation == null ? null : other.salutation.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.profilePicture = other.profilePicture == null ? null : other.profilePicture.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.userCategoryMappingId = other.userCategoryMappingId == null ? null : other.userCategoryMappingId.copy();
    }

    @Override
    public UserProfileCriteria copy() {
        return new UserProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSalutation() {
        return salutation;
    }

    public void setSalutation(StringFilter salutation) {
        this.salutation = salutation;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(StringFilter profilePicture) {
        this.profilePicture = profilePicture;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getUserType() {
        return userType;
    }

    public void setUserType(StringFilter userType) {
        this.userType = userType;
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

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final UserProfileCriteria that = (UserProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(salutation, that.salutation) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(profilePicture, that.profilePicture) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(userType, that.userType) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(userCategoryMappingId, that.userCategoryMappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        salutation,
        firstName,
        lastName,
        profilePicture,
        phoneNumber,
        userType,
        createdAt,
        updatedAt,
        userId,
        companyId,
        userCategoryMappingId
        );
    }

    @Override
    public String toString() {
        return "UserProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (salutation != null ? "salutation=" + salutation + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (profilePicture != null ? "profilePicture=" + profilePicture + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (userType != null ? "userType=" + userType + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (userCategoryMappingId != null ? "userCategoryMappingId=" + userCategoryMappingId + ", " : "") +
            "}";
    }

}
