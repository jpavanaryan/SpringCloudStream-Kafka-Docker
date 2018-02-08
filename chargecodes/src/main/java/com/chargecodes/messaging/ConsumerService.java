package com.chargecodes.messaging;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;



@EnableBinding(Sink.class)
public class ConsumerService
{
	
	@StreamListener(Sink.INPUT)
	public void consume(Employee employee)
	{
		System.out.println("********************************  Message Received  ********************************  \n");
		System.out.println("First Name: "+employee.getFirstName());
		System.out.println("LastName Name: "+employee.getLastName());
		System.out.println("Middle Name: "+employee.getMiddleName());
		System.out.println("Salary : "+employee.getSalary());
		System.out.println("Id: "+employee.getId());
		System.out.println("Street Name: "+employee.getShippingAddress().getStreetName());
		
	}
	
	
	
}