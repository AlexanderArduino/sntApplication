package com.energetik.app.sntapplication.service.impl;


import com.energetik.app.sntapplication.entity.Conversation;
import com.energetik.app.sntapplication.repository.ConversationRepository;
import com.energetik.app.sntapplication.service.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Conversation> findConversationById(Long id) {
        return conversationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsConversationById(Long id) {
        return conversationRepository.existsById(id);
    }

    @Override
    public Conversation saveConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation updateConversation(Conversation conversation) {
        if (conversation == null) {
            throw new IllegalArgumentException(
                    String.format("Updatable entity must be not null")
            );
        }
        Optional<Conversation> optionalConversation = conversationRepository.findById(conversation.getId());
        if (optionalConversation.isPresent()) {
            Conversation updateConv = optionalConversation.get();
            updateConv.setId(conversation.getId());
            updateConv.setDate(conversation.getDate());
            updateConv.setIs_in_call(conversation.isIs_in_call());
            updateConv.setIs_out_call(conversation.isIs_out_call());
            updateConv.setReasone(conversation.getReasone());
            updateConv.setAbout(conversation.getAbout());
            updateConv.setNote(conversation.getNote());
            updateConv.setGardener(conversation.getGardener());
            return conversationRepository.save(updateConv);
        } else {
            return null;
        }
    }

    @Override
    public void deleteConversation(Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Must be more than 0");
        }
        if (conversationRepository.existsById(id)) {
            conversationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(String.format("Conversation with ID:%d not found", id));
        }
    }
}
