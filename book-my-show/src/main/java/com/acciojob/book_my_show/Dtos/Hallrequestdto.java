package com.acciojob.book_my_show.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hallrequestdto {
    private String hallName;
    private String rowRange; // A-G
    private Integer seatCapacityPerRow;
}
