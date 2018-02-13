package com.projects.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;



@EnableBinding(value ={ Source.class})
public class MessagingConfiguration {

}
