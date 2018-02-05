package com.projects.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

import com.projects.messaging.ConsumerChannel;
import com.projects.messaging.ProducerChannel;

/**
 * Configures Spring Cloud Stream support.
 *
 * See
 * http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/
 * for more information.
 */
@EnableBinding(value ={ Source.class, ProducerChannel.class, ConsumerChannel.class })
public class MessagingConfiguration {

}
