package com.yedam.app.chating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.yedam.app.chating.service.ChatService;

@Controller
public class ChatController {
	
		  @Autowired
		  ChatService chatService;
				
	 	  @MessageMapping("/hello/{planId}") //WebSocket안로 들어오는 메시지 발행을 처리한다.
		  @SendTo("/topic/{planId}") ///topic/greetings한 유저에게 다 보내줘라
		  public ChatVO message(@PathVariable String planId, Message message) throws Exception {
			Thread.sleep(1000); // 1000이 1초 >> 지연하고 메세지를 보낼 꺼임
		    //제이슨 형식으로 보낼꺼임
			chatService.insertChatLog(message.getPlanId(), message.getUserId(), message.getContent());
		    return new ChatVO(HtmlUtils.htmlEscape(message.getUserId()) ,HtmlUtils.htmlEscape(message.getContent()));
		  }
	 	  
	 	  @RequestMapping(value="/getChatList")
	 	  @ResponseBody
	 	  public List<ChatingVO> getChatList(String planId){
	 		 List<ChatingVO> list = chatService.getChatList(planId);
	 		  
	 		  return list;
	 	  }
	 	  
	
}
