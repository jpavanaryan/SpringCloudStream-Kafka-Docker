package com.chargecodes.messaging;

import javax.persistence.Embedded;

public class Employee
{

	 	private Long id;


	    private String firstName;


	    private String lastName;


	    private String middleName;

	    private Float salary;

	    @Embedded
		private Address address;
	    //private Address shippingAddress;

	    
	    
	    
		public Employee()
		{
			super();
			// TODO Auto-generated constructor stub
		}

		
		

		public Employee(Long id, String firstName, String lastName, String middleName, Float salary,
				Address shippingAddress)
		{
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.middleName = middleName;
			this.salary = salary;
			this.address = shippingAddress;
		}




		public Long getId()
		{
			return id;
		}


		public void setId(Long id)
		{
			this.id = id;
		}


		public String getFirstName()
		{
			return firstName;
		}


		public void setFirstName(String firstName)
		{
			this.firstName = firstName;
		}


		public String getLastName()
		{
			return lastName;
		}


		public void setLastName(String lastName)
		{
			this.lastName = lastName;
		}


		public String getMiddleName()
		{
			return middleName;
		}


		public void setMiddleName(String middleName)
		{
			this.middleName = middleName;
		}


		public Float getSalary()
		{
			return salary;
		}


		public void setSalary(Float salary)
		{
			this.salary = salary;
		}


		public Address getShippingAddress()
		{
			return address;
		}


		public void setShippingAddress(Address shippingAddress)
		{
			this.address = shippingAddress;
		}
	    
	    
		 @Override
		    public String toString() {
		        return "Employee{" +
		            "id=" + getId() +
		            ", firstName='" + getFirstName() + "'" +
		            ", lastName='" + getLastName() + "'" +
		            ", middleName='" + getMiddleName() + "'" +
		            ", salary=" + getSalary() +
		            "}";
		    }
	    
	    
	    
}
