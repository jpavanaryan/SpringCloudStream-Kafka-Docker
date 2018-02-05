package com.projects.messaging;

import com.codahale.metrics.annotation.Timed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProducerResource
{
	private MessageChannel channel;

	@Autowired
	public ProducerResource(@Qualifier("customChannel") MessageChannel channel)
	{
		this.channel = channel;
	}

	@GetMapping("/greetings/{count}")
	@Timed
	public void produce(@PathVariable int count)
	{
		System.out.println("Inside produce method");
		while (count > 0)
		{
			Boolean flag=channel.send(MessageBuilder.withPayload(new Greeting().setMessage("Message Sequence Number: " + count)).build());
			System.out.println("Message sent successfully? "+flag);
			count--;
		}
	}

}