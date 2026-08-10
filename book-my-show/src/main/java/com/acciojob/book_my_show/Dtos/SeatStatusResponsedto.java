package com.acciojob.book_my_show.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatStatusResponsedto {
    private UUID showId;
    private String seatId;
    private  String seatStatus;
}
