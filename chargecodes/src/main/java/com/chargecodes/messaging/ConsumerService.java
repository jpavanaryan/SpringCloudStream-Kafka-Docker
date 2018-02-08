package com.chargecodes.messaging;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;



@EnableBinding(Sink.class)
public class ConsumerService
{
	
	@StreamListener(Sink.INPUT)
	public void consume(Address address)
	{
		System.out.println("********************************  Message Received  ********************************  ");
		System.out.println("Street Name: "+address.getStreetName());
	}
	
	
	
}