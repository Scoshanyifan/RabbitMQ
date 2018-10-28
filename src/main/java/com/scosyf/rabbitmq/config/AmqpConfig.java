package com.scosyf.rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableRabbit   //这个注解不加不会自动生成exchange等
public class AmqpConfig {

	@Bean
	public ConnectionFactory mqConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(PropertiesUtils.getProperty("rabbitmq.host", "localhost"));
		connectionFactory.setPort(PropertiesUtils.getPropertyInt("rabbitmq.port", 5672));
		connectionFactory.setUsername(PropertiesUtils.getProperty("rabbitmq.username", ""));
		connectionFactory.setPassword(PropertiesUtils.getProperty("rabbitmq.password", ""));
		connectionFactory.setVirtualHost(PropertiesUtils.getProperty("rabbitmq.virtualhost", ""));
		connectionFactory.setPublisherConfirms(true); // 必须要设置，用于生产者确认收到消息
		return connectionFactory;
	}
	
	@Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(mqConnectionFactory());
    }
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	// 必须是prototype类型
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(mqConnectionFactory());
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	}
	
	@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(mqConnectionFactory());
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}