package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.ConversationDto;
import com.jgajzler.apartmently.dto.MessageListDto;
import com.jgajzler.apartmently.service.ChatConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @PostMapping(path = "user/{user1Id}/{user2Id}")
    public ResponseEntity<Map<String, Long>> create(@PathVariable("user1Id") Long user1Id, @PathVariable("user2Id") Long user2Id) {
        Long createdId = conversationService.createChat(user1Id, user2Id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri();
        return ResponseEntity.created(location).body(Collections.singletonMap("id", createdId));
    }

    @PostMapping(path = "message/{conversationId}/{senderId}")
    public ResponseEntity<Map<String, String>> sendMessage(@PathVariable("conversationId") Long conversationId,
                                                           @PathVariable("senderId") Long senderId,
                                                           @RequestBody Map<String, String> content) {
        conversationService.sendMessage(conversationId, senderId, content.get("content"));
        return ResponseEntity.ok().body(Collections.singletonMap("message", "sent"));
    }

}
