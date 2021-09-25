package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.MessageDto;
import com.jgajzler.apartmently.entity.ChatMessage;

public class ChatMessageMapper {
    public static MessageDto toDto(ChatMessage message) {
        return new MessageDto(
                message.getSender().getId(),
                message.getSender().getUserDetails().getName(),
                message.getContent(),
                message.getDateCreated()
        );
    }
}
