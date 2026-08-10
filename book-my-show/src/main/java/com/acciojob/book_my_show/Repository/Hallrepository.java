package com.acciojob.book_my_show.Repository;

import com.acciojob.book_my_show.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Hallrepository extends JpaRepository<Hall, UUID> {
}
