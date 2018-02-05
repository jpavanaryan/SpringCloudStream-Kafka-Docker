package com.chargecodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ChargeCode.
 */
@Entity
@Table(name = "charge_code")
public class ChargeCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "charge_code_name", nullable = false)
    private String chargeCodeName;

    @NotNull
    @Column(name = "charge_code_location", nullable = false)
    private String chargeCodeLocation;

    @Column(name = "charge_code_start_date")
    private LocalDate chargeCodeStartDate;

    @Column(name = "charge_code_end_date")
    private LocalDate chargeCodeEndDate;

    @OneToMany(mappedBy = "chargecode")
    @JsonIgnore
    private Set<ChargeCodeProject> chargeCodeProjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargeCodeName() {
        return chargeCodeName;
    }

    public ChargeCode chargeCodeName(String chargeCodeName) {
        this.chargeCodeName = chargeCodeName;
        return this;
    }

    public void setChargeCodeName(String chargeCodeName) {
        this.chargeCodeName = chargeCodeName;
    }

    public String getChargeCodeLocation() {
        return chargeCodeLocation;
    }

    public ChargeCode chargeCodeLocation(String chargeCodeLocation) {
        this.chargeCodeLocation = chargeCodeLocation;
        return this;
    }

    public void setChargeCodeLocation(String chargeCodeLocation) {
        this.chargeCodeLocation = chargeCodeLocation;
    }

    public LocalDate getChargeCodeStartDate() {
        return chargeCodeStartDate;
    }

    public ChargeCode chargeCodeStartDate(LocalDate chargeCodeStartDate) {
        this.chargeCodeStartDate = chargeCodeStartDate;
        return this;
    }

    public void setChargeCodeStartDate(LocalDate chargeCodeStartDate) {
        this.chargeCodeStartDate = chargeCodeStartDate;
    }

    public LocalDate getChargeCodeEndDate() {
        return chargeCodeEndDate;
    }

    public ChargeCode chargeCodeEndDate(LocalDate chargeCodeEndDate) {
        this.chargeCodeEndDate = chargeCodeEndDate;
        return this;
    }

    public void setChargeCodeEndDate(LocalDate chargeCodeEndDate) {
        this.chargeCodeEndDate = chargeCodeEndDate;
    }

    public Set<ChargeCodeProject> getChargeCodeProjects() {
        return chargeCodeProjects;
    }

    public ChargeCode chargeCodeProjects(Set<ChargeCodeProject> chargeCodeProjects) {
        this.chargeCodeProjects = chargeCodeProjects;
        return this;
    }

    public ChargeCode addChargeCodeProject(ChargeCodeProject chargeCodeProject) {
        this.chargeCodeProjects.add(chargeCodeProject);
        chargeCodeProject.setChargecode(this);
        return this;
    }

    public ChargeCode removeChargeCodeProject(ChargeCodeProject chargeCodeProject) {
        this.chargeCodeProjects.remove(chargeCodeProject);
        chargeCodeProject.setChargecode(null);
        return this;
    }

    public void setChargeCodeProjects(Set<ChargeCodeProject> chargeCodeProjects) {
        this.chargeCodeProjects = chargeCodeProjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChargeCode chargeCode = (ChargeCode) o;
        if (chargeCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chargeCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChargeCode{" +
            "id=" + getId() +
            ", chargeCodeName='" + getChargeCodeName() + "'" +
            ", chargeCodeLocation='" + getChargeCodeLocation() + "'" +
            ", chargeCodeStartDate='" + getChargeCodeStartDate() + "'" +
            ", chargeCodeEndDate='" + getChargeCodeEndDate() + "'" +
            "}";
    }
}
