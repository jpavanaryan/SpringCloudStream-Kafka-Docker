package com.chargecodes.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile SetAttribute<Project, ChargeCodeProject> chargeCodeProjects;
	public static volatile SingularAttribute<Project, Long> id;
	public static volatile SingularAttribute<Project, String> projectName;
	public static volatile SingularAttribute<Project, String> projectTitle;

}

