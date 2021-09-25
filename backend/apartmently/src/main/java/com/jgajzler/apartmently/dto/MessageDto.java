package com.jgajzler.apartmently.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MessageDto {
    private final Long senderId;
    private final String senderName;
    private final String content;
    private final Date dateCreated;

}
