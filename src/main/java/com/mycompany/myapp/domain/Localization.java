package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Localization.
 */
@Entity
@Table(name = "localization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Localization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "label_en", length = 255, nullable = false)
    private String labelEn;

    @NotNull
    @Size(max = 255)
    @Column(name = "label_de", length = 255, nullable = false)
    private String labelDe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelEn() {
        return labelEn;
    }

    public Localization labelEn(String labelEn) {
        this.labelEn = labelEn;
        return this;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelDe() {
        return labelDe;
    }

    public Localization labelDe(String labelDe) {
        this.labelDe = labelDe;
        return this;
    }

    public void setLabelDe(String labelDe) {
        this.labelDe = labelDe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localization)) {
            return false;
        }
        return id != null && id.equals(((Localization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Localization{" +
            "id=" + getId() +
            ", labelEn='" + getLabelEn() + "'" +
            ", labelDe='" + getLabelDe() + "'" +
            "}";
    }
}
