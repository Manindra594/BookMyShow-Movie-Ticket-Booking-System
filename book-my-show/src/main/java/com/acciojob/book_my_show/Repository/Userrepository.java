package com.acciojob.book_my_show.Repository;

import com.acciojob.book_my_show.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface Userrepository extends JpaRepository<User,UUID> {
    Optional<User> findByEmail(String email);
}
