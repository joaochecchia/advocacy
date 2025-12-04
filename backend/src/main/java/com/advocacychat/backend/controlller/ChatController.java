package com.advocacychat.backend.controlller;

import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public MessageResponse newMessage(MessageRequest request){
        return new MessageResponse(HtmlUtils.htmlEscape(request.nomeRemetente() + ": " + request.message()));
    }
}
