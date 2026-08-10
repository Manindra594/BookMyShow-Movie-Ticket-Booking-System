package com.acciojob.book_my_show.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showrequestdto {
    private Double showPrice;
    private String movieName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
