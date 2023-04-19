package com.yedam.app.chating;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //Stomp를 사용하기 위해 선언
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chatserver")
				.withSockJS(); //메시지 서버 접속
				//클라이언트와의 연결은 SockJS()로 하기 때문에 withSockJs()를 사용한다.
		
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic"); //메시지를 구독하는 요청
	    config.setApplicationDestinationPrefixes("/app"); //메세지를 발행하는 요청
	}
	
	
}
