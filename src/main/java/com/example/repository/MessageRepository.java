package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

/**
 * Message:
 *   messageId integer primary key auto_increment,
 *   postedBy integer,
 *   messageText varchar(255),
 *   timePostedEpoch long,
 *   foreign key (postedBy) references Account(accountId)
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * This query will return a list of messages posted by a specific accountId. 
     * 
     * @param postedBy The accountId to search for.
     * @return A list of messages posted by the account with the given id.
     */
    List<Message> findMessageByPostedBy(int postedBy);
}
