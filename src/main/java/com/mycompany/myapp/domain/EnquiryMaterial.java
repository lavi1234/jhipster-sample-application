package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EnquiryMaterial.
 */
@Entity
@Table(name = "enquiry_material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnquiryMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Size(max = 200)
    @Column(name = "dimension", length = 200, nullable = false)
    private String dimension;

    @NotNull
    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    @NotNull
    @Size(max = 200)
    @Column(name = "color", length = 200, nullable = false)
    private String color;

    @Size(max = 255)
    @Column(name = "comments", length = 255)
    private String comments;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryMaterials")
    private EnquiryDetails enquiryDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EnquiryMaterial name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimension() {
        return dimension;
    }

    public EnquiryMaterial dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public EnquiryMaterial materialId(Integer materialId) {
        this.materialId = materialId;
        return this;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getColor() {
        return color;
    }

    public EnquiryMaterial color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComments() {
        return comments;
    }

    public EnquiryMaterial comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public EnquiryDetails getEnquiryDetails() {
        return enquiryDetails;
    }

    public EnquiryMaterial enquiryDetails(EnquiryDetails enquiryDetails) {
        this.enquiryDetails = enquiryDetails;
        return this;
    }

    public void setEnquiryDetails(EnquiryDetails enquiryDetails) {
        this.enquiryDetails = enquiryDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnquiryMaterial)) {
            return false;
        }
        return id != null && id.equals(((EnquiryMaterial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EnquiryMaterial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dimension='" + getDimension() + "'" +
            ", materialId=" + getMaterialId() +
            ", color='" + getColor() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
