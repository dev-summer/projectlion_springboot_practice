package dev.summer.wschatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // spring application이 stomp기반의 websocket messaging을 지원
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 아래 두 가지 함수를 override해서 endpoint를 구성하기 위해 사용

    // websocket기준에서 엔드포인트를 지정할 수 있음
    // websocket이 하나의 엔드포인트에서 여러 클라이언트가 접속할 수 있다 에서 클라이언트들이 최초에 접속하게 되는 웹소켓 엔드포인트를 정의하는 부분
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws/chat"); // 클라이언트들이 연결하기 위해서 /ws/chat경로로 웹소켓 요청을 보내게 됨리
        registry.addEndpoint("/ws/chat").withSockJS();
        // SockJS의 경우 websocket이 가능한 브라우저에서는 websocket을 반환하는 라이브러리가 되나,
        // websocket이 모든 인터넷 브라우저에서 작동하는 것은 아니기 때문에
        // 그런 상황에서 SockJS가 websocket처럼 작동하도록 하는 객체를 제공하는 역할을 함
        // 해당 객체를 통해 제공할 상황을 대비해서 html 문서에 let socket = new SockJS('/ws/chat'); 이 있음에도 불구하고
        // registry.addEndpoint("/ws/chat").withSockJS(); 를 추가한 것

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/receive-endpoint");
        registry.setApplicationDestinationPrefixes("/send-endpoint");
        // STOMP에서는 Destination이라는 헤더를 통해 요청이 어떤 클라이언트에 가게 될 것인지에 대한 정의를 내릴 수 있음
        // 위의 두 가지 함수는 데스티네이션을 설정하기 위한 함수
        // enableSimpleBroker: 메시지를 클라이언트에게 전달하기 위한 브로커를 생성하고,
        // 클라이언트는 이 함수에 정의된 데스티네이션을 구독함으로써 해당 브로커가 전달하는 메세지 일부를 들을 수 있음
        // setApplication~: 애플리케이션에서 메시지를 받기 위한 데스티네이션 엔드포인트 설정
        // 클라이언트쪽에서 서버에 메시지를 보내게 될 때 데스티네이션이 send-endpoint로 시작할 것이라는 의미

    }
}
