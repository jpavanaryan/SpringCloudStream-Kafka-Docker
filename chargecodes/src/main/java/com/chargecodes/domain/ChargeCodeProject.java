package com.chargecodes.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ChargeCodeProject.
 */
@Entity
@Table(name = "charge_code_project")
public class ChargeCodeProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChargeCode chargecode;

    @ManyToOne
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChargeCode getChargecode() {
        return chargecode;
    }

    public ChargeCodeProject chargecode(ChargeCode chargeCode) {
        this.chargecode = chargeCode;
        return this;
    }

    public void setChargecode(ChargeCode chargeCode) {
        this.chargecode = chargeCode;
    }

    public Project getProject() {
        return project;
    }

    public ChargeCodeProject project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
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
        ChargeCodeProject chargeCodeProject = (ChargeCodeProject) o;
        if (chargeCodeProject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chargeCodeProject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChargeCodeProject{" +
            "id=" + getId() +
            "}";
    }
}
