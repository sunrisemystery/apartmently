package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.ConversationDto;
import com.jgajzler.apartmently.dto.MessageListDto;
import com.jgajzler.apartmently.mapper.ChatConversationMapper;
import com.jgajzler.apartmently.repository.ChatConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ChatConversationService {
    private final ChatConversationRepository chatConversationRepository;
    private final ChatConversationMapper chatConversationMapper;

    @Autowired
    public ChatConversationService(ChatConversationRepository chatConversationRepository,
                                   ChatConversationMapper chatConversationMapper) {
        this.chatConversationRepository = chatConversationRepository;
        this.chatConversationMapper = chatConversationMapper;
    }


    public List<MessageListDto> getConversationByUserId(Long id) {
        return chatConversationRepository.findChatConversationByUserId(id);
    }

    public ConversationDto getConversationById(Long id) {
        return chatConversationMapper.toDto(chatConversationRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
}
