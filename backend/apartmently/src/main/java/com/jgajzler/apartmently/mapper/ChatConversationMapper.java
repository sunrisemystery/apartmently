package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.ConversationDto;
import com.jgajzler.apartmently.entity.ChatConversation;
import com.jgajzler.apartmently.entity.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class ChatConversationMapper {
    public ConversationDto toDto(ChatConversation conversation) {
        return new ConversationDto(
                conversation.getId(),
                conversation.getUser1().getId(),
                conversation.getUser2().getId(),
                conversation.getDateCreated(),
                conversation
                        .getMessages()
                        .stream()
                        .sorted(Comparator.comparing(ChatMessage::getDateCreated))
                        .map(ChatMessageMapper::toDto)
                        .collect(Collectors.toList()));
    }

}
