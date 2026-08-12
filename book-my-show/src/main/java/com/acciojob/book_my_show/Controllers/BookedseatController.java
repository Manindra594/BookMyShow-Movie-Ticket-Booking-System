package com.acciojob.book_my_show.Controllers;

import com.acciojob.book_my_show.Dtos.BookedseatRequestDto;
import com.acciojob.book_my_show.Dtos.BookedseatResponseDto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Services.BookedseatService;
import com.acciojob.book_my_show.models.BookedSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookseat")
public class BookedseatController {
    private BookedseatService bookedseatService;
    @Autowired
    public BookedseatController(BookedseatService bookedseatService){
        this.bookedseatService = bookedseatService;
    }


    @PostMapping("/bookingseat")
    public ResponseEntity bookseat(@RequestBody BookedseatRequestDto bookedseatRequestDto){
        try {
            BookedSeat bookedSeat = bookedseatService.bookseat(bookedseatRequestDto);
            BookedseatResponseDto response =
                    new BookedseatResponseDto(
                            "Seat booked successfully",
                            bookedSeat.getSeatId(),
                            bookedSeat.getShow().getSysId()
                    );
            return new ResponseEntity(response, HttpStatus.ACCEPTED);
        }catch (UnAuthorizedException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
