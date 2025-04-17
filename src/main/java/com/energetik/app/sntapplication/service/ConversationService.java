package com.energetik.app.sntapplication.service;


import com.energetik.app.sntapplication.entity.Conversation;

import java.util.Optional;


public interface ConversationService {

    Optional<Conversation> findConversationById(Long id);

    boolean existsConversationById(Long id);

    Conversation saveConversation(Conversation conversation);

    Conversation updateConversation(Conversation conversation);

    void deleteConversation(Long id);
}
