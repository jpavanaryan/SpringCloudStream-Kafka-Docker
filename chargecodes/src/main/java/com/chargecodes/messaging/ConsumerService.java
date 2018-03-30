package com.chargecodes.messaging;

import java.io.IOException;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chargecodes.domain.Project;
import com.chargecodes.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@EnableBinding(Sink.class)
public class ConsumerService
{

	private ProjectRepository projectRepository;
	
	public ConsumerService(ProjectRepository projectRepository) 
	{
        this.projectRepository = projectRepository;
    }
	
	@StreamListener(target = Sink.INPUT, condition = "payload.messageType.toString()=='Project'")
	@Transactional
	public void consumeProject(String messageJson) throws JsonParseException, JsonMappingException, IOException
	{
		Message<Project> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<Project>>(){});
		Project project = message.getPayload();
		
		System.out.println("********************************  Project Message Received  ********************************  ");
		System.out.println("Project Name: " + project.getProjectName());
		System.out.println("Project Title: " + project.getProjectTitle());
	}
	
	
	@StreamListener(target = Sink.INPUT, condition = "payload.messageType.toString()=='Address'")
	@Transactional
	public void consumeAddress(String messageJson) throws JsonParseException, JsonMappingException, IOException
	{
		Message<Address> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<Address>>(){});
		Address address = message.getPayload();
		
		System.out.println("******************************** Address Message Received  ********************************  ");
		System.out.println("Address Street: " + address.getStreetName());
		System.out.println("Address City: " + address.getCity());
	}

}