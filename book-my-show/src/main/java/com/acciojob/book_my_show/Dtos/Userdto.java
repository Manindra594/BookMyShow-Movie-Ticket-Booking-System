package com.acciojob.book_my_show.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userdto {
    private String fullname;
    private String email;
    private String password;
    private  String userType;
    private  String PhoneNumber;
    private  String address;
}
