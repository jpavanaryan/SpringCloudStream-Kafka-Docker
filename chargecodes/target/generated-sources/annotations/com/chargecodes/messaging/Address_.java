package com.chargecodes.messaging;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, Long> zipcode;
	public static volatile SingularAttribute<Address, String> country;
	public static volatile SingularAttribute<Address, String> streetName;
	public static volatile SingularAttribute<Address, String> apartmentOrHouseNumber;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> state;

}

