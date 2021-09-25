package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.ConversationDto;
import com.jgajzler.apartmently.dto.MessageListDto;
import com.jgajzler.apartmently.service.ChatConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/conversation")
public class ChatConversationController {
    private final ChatConversationService conversationService;

    @Autowired
    public ChatConversationController(ChatConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping(path = "user/{userId}")
    public List<MessageListDto> getAllById(@PathVariable("userId") Long id) {
        return conversationService.getConversationByUserId(id);
    }

    @GetMapping(path = "{convId}")
    public ConversationDto getConversationById(@PathVariable("convId") Long id) {
        return conversationService.getConversationById(id);
    }

}
