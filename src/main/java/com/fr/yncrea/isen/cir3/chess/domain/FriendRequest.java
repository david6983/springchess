package com.fr.yncrea.isen.cir3.chess.domain;

import javax.persistence.*;

@Entity
public class FriendRequest {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_request_seq_gen")
    @SequenceGenerator(name = "friend_request_seq_gen", sequenceName = "friend_request_id_seq")
    private Long id;

    @ManyToOne
    private User receiver;

    @Column
    private String sender;

    @Column
    private Boolean isAccepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}
