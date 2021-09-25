package com.jgajzler.apartmently.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "conversations")
@Getter
@Setter
public class ChatConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date dateUpdated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation")
    private Set<ChatMessage> messages;

}
