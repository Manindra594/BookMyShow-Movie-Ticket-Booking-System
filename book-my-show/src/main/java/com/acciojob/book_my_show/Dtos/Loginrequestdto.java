package com.acciojob.book_my_show.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loginrequestdto {
    private String email;
    private String password;
}
