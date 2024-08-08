package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /**
     * Posts (persists) a new message to the database.
     * 
     * @param message The new Message without a messageId
     * @return a posted Message with a generated messageId
     */
    public Message submitMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Gets a List of all messages in the database.
     * 
     * @return a List<Message> containing all messages in the database, which may be empty
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * 
     * @param accountId
     * @return
     */
    public List<Message> getAllMessages(int accountId) {
        return messageRepository.findMessageByPostedBy(accountId);
    }

    /**
     * Gets a specific message by its messageId.
     * 
     * @param id The messageId to search for.
     * @return the Message with the given messageId, or null if not found
     */
    public Message getMessage(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a specific message by its messageId.
     * 
     * @param id The messageId to search for.
     * @return 1 if the message was deleted, 0 if the message with the given messageId did not exist
     */
    public int deleteMessage(int id) {
        if (messageRepository.findById(id).isEmpty()) {
            return 0;  // message does not exist
        }

        messageRepository.deleteById(id);
        return 1;
    }

    /**
     * Updates a specific message with the given messageText.
     * 
     * @param id The messageId to search for.
     * @param messageText The text to replace the content of the message with.
     * @return 1 if the message was updated, 0 if the message with the given messageId did not exist
     */
    public int updateMessage(int id, String messageText) {
        Optional<Message> oMessage = messageRepository.findById(id);

        if (oMessage.isEmpty()) {
            return 0;  // message does not exist
        }

        Message newMessage = oMessage.get();
        newMessage.setMessageText(messageText);
        messageRepository.save(newMessage);

        return 1;
    }
}
