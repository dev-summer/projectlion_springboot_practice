package dev.summer.wschatting.controller;

import dev.summer.wschatting.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebSocketMapping {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketMapping.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketMapping(@Autowired SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // restController 에서 사용하던 get/post/request 같은 어노테이션으로 websocket에서는 @MessageMapping 어노테이션 사용
    @MessageMapping("/ws/chat")
    public void sendChat(ChatMessage chatMessage){
        logger.info(chatMessage.toString());
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        chatMessage.setTime(time);
        simpMessagingTemplate.convertAndSend(
                String.format("/receive-endpoint/%s", chatMessage.getRoomId()),
                chatMessage
        );
    }

//    @MessageMapping("/ws/chat")
//    @SendTo("/receive-endpoint/all") // destination을 상황에 따라 변동할 필요가 없을 경우 (위 함수의 %s 부분) 사용. 명백한 destination을 정의해줄 수 있음.
//    public ChatMessage sendChatAll(){
//
//    }
}
