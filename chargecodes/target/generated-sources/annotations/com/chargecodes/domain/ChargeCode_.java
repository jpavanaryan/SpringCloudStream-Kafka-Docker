package com.chargecodes.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChargeCode.class)
public abstract class ChargeCode_ {

	public static volatile SingularAttribute<ChargeCode, String> chargeCodeLocation;
	public static volatile SingularAttribute<ChargeCode, String> chargeCodeName;
	public static volatile SetAttribute<ChargeCode, ChargeCodeProject> chargeCodeProjects;
	public static volatile SingularAttribute<ChargeCode, LocalDate> chargeCodeEndDate;
	public static volatile SingularAttribute<ChargeCode, Long> id;
	public static volatile SingularAttribute<ChargeCode, LocalDate> chargeCodeStartDate;

}

