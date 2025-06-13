package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.*;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<List<Message>> findAllById(Long accountId);
    Optional<List<Message>> findAllByPosted(Long postedBy);
}
