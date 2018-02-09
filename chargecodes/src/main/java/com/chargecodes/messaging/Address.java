package com.chargecodes.messaging;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address
{

	@Column(nullable = false)
	    private Long id;

	@Column(nullable = false)
	    private String streetName;

	@Column(nullable = false)
	    private String apartmentOrHouseNumber;

	@Column(nullable = false)
	    private String city;

	@Column(nullable = false)
	    private Long zipcode;

	@Column(nullable = false)
	    private String state;

	@Column(nullable = false)
	    private String country;

	    
	    
	    public Address() {
			super();
			id=0l;
		}
	    
	    
		public Address(Long id, String streetName, String apartmentOrHouseNumber, String city, Long zipcode,
				String state, String country)
		{
			super();
			this.id = id;
			this.streetName = streetName;
			this.apartmentOrHouseNumber = apartmentOrHouseNumber;
			this.city = city;
			this.zipcode = zipcode;
			this.state = state;
			this.country = country;
		}

		public Long getId()
		{
			return id;
		}

		public void setId(Long id)
		{
			this.id = id;
		}

		public String getStreetName()
		{
			return streetName;
		}

		public void setStreetName(String streetName)
		{
			this.streetName = streetName;
		}

		public String getApartmentOrHouseNumber()
		{
			return apartmentOrHouseNumber;
		}

		public void setApartmentOrHouseNumber(String apartmentOrHouseNumber)
		{
			this.apartmentOrHouseNumber = apartmentOrHouseNumber;
		}

		public String getCity()
		{
			return city;
		}

		public void setCity(String city)
		{
			this.city = city;
		}

		public Long getZipcode()
		{
			return zipcode;
		}

		public void setZipcode(Long zipcode)
		{
			this.zipcode = zipcode;
		}

		public String getState()
		{
			return state;
		}

		public void setState(String state)
		{
			this.state = state;
		}

		public String getCountry()
		{
			return country;
		}

		public void setCountry(String country)
		{
			this.country = country;
		}
	    
	    
	    
	    
}
