package com.chargecodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull
    @Column(name = "project_title", nullable = false)
    private String projectTitle;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Set<ChargeCodeProject> chargeCodeProjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public Project projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public Project projectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
        return this;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Set<ChargeCodeProject> getChargeCodeProjects() {
        return chargeCodeProjects;
    }

    public Project chargeCodeProjects(Set<ChargeCodeProject> chargeCodeProjects) {
        this.chargeCodeProjects = chargeCodeProjects;
        return this;
    }

    public Project addChargeCodeProject(ChargeCodeProject chargeCodeProject) {
        this.chargeCodeProjects.add(chargeCodeProject);
        chargeCodeProject.setProject(this);
        return this;
    }

    public Project removeChargeCodeProject(ChargeCodeProject chargeCodeProject) {
        this.chargeCodeProjects.remove(chargeCodeProject);
        chargeCodeProject.setProject(null);
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", projectTitle='" + getProjectTitle() + "'" +
            "}";
    }
}
