package com.chargecodes.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import com.chargecodes.domain.Project;
import com.chargecodes.repository.ProjectRepository;

@Service
@EnableBinding(Sink.class)
public class ConsumerService
{

	private ProjectRepository projectRepository;
	
	public ConsumerService(ProjectRepository projectRepository) 
	{
        this.projectRepository = projectRepository;
    }
	
	@StreamListener(Sink.INPUT)
	public void consume(Project project)
	{
		System.out.println("********************************  Message Received  ********************************  \n");
		System.out.println("Project Name: " + project.getProjectName());
		System.out.println("Project Title: " + project.getProjectName());

		projectRepository.save(project);
		
		if (project == null)
			{
				System.out.println("Project is  null ");
			}

		/*
		 * @StreamListener(Sink.INPUT) public void consume(Employee employee) {
		 * System.out.
		 * println("********************************  Message Received  ********************************  \n"
		 * ); System.out.println("First Name: "+employee.getFirstName());
		 * System.out.println("LastName Name: "+employee.getLastName());
		 * System.out.println("Middle Name: "+employee.getMiddleName());
		 * System.out.println("Salary : "+employee.getSalary());
		 * System.out.println("Id: "+employee.getId());
		 * 
		 * if(employee.getAddress() == null) {
		 * System.out.println("Address Object is  --> "+employee.getAddress());
		 * } else
		 * System.out.println("Inside ChargeCodes --> Street Name: "+employee.
		 * getAddress().getStreetName());
		 * 
		 */}

}