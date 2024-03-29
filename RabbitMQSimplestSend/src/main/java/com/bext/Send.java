package com.bext;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class Send {
	private final static String QUEUE_NAME = "simple-queue";
	
	public static void main(String[] args) throws Exception{
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try(Connection connection = factory.newConnection();
			   Channel    channel = connection.createChannel()) {
			boolean durable = false;
			boolean exclusive = false;
			boolean autoDelete = false;
			Map<String,Object> arguments = null;
			channel.queueDeclare(QUEUE_NAME,durable, exclusive, autoDelete, arguments);
			
			String message = "simple mensaje";
			String exchange = "";
			String routingKey = QUEUE_NAME;
			AMQP.BasicProperties props = null;
			channel.basicPublish(exchange, routingKey, props, message.getBytes());
			
			System.out.println(" [x] enviado '" + message + "'");
		}
	}

}
