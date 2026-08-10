package com.acciojob.book_my_show.Repository;

import com.acciojob.book_my_show.models.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookedSeatrepository extends JpaRepository<BookedSeat, UUID> {

    @Query(value = "SELECT * FROM bookedseats WHERE seat_id = :seatId AND show_sys_id = :showsysId",nativeQuery = true)
    public BookedSeat isSeatBooked(String seatId,UUID showsysId);
}
