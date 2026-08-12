package com.acciojob.book_my_show.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedseatRequestDto {
    private UUID UsersysId;
    private UUID ShowsysId;
    private String SeatId;
}
