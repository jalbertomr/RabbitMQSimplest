package com.bext;

import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receive {
	public final static String QUEUE_NAME = "simple-queue";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		boolean durable = false;
		boolean exclusive = false;
		boolean autoDelete = false;
		Map<String,Object> arguments = null;
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, arguments);
		
		System.out.println("Esperando por mensajes... CTRL+C para salir.");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[x] recibido: '" + message +"'");
		};
		boolean autoAck = true;
		channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag->{});
	}
}
