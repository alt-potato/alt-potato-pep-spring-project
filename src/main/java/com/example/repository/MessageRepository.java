package com.example.repository;

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
}
