package com.chargecodes.messaging;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address
{

		@Column
	    private Long id;

	    @NotNull
	    @Column(nullable = false)
	    private String streetName;

	    @Column
	    private String apartmentOrHouseNumber;

	    @NotNull
	    @Column(nullable = false)
	    private String city;

	    @NotNull
	    @Column(nullable = false)
	    private Long zipcode;

	    @Column(name = "state")
	    private String state;

	    @Column(name = "country")
	    private String country;

	    
	    
	    public Address() {
			super();
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
