package com.chat.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

}
