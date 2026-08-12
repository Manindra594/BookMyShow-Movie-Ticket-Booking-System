package com.acciojob.book_my_show.Services;

import com.acciojob.book_my_show.Dtos.BookedseatRequestDto;
import com.acciojob.book_my_show.Dtos.BookedseatResponseDto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Repository.BookedSeatrepository;
import com.acciojob.book_my_show.Repository.Showrepository;
import com.acciojob.book_my_show.Repository.Userrepository;
import com.acciojob.book_my_show.Transformer.Applicationtransformer;
import com.acciojob.book_my_show.models.BookedSeat;
import com.acciojob.book_my_show.models.Hall;
import com.acciojob.book_my_show.models.Show;
import com.acciojob.book_my_show.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookedseatService {

    private BookedSeatrepository bookedSeatrepository;
    private Showrepository showrepository;
    private Userrepository userrepository;
    private Applicationtransformer applicationtransformer;


    @Autowired
    public BookedseatService(BookedSeatrepository bookedSeatrepository,
                             Showrepository showrepository,
                             Userrepository userrepository,
                             Applicationtransformer applicationtransformer){
        this.bookedSeatrepository = bookedSeatrepository;
        this.showrepository = showrepository;
        this.userrepository = userrepository;
        this.applicationtransformer = applicationtransformer;
    }

    public BookedSeat bookseat(BookedseatRequestDto bookedseatRequestDto){
        System.out.println("User ID: " + bookedseatRequestDto.getUsersysId());
        System.out.println("Show ID: " + bookedseatRequestDto.getShowsysId());
        System.out.println("Seat ID: " + bookedseatRequestDto.getSeatId());
        Show show = showrepository.findById(bookedseatRequestDto.getShowsysId()).orElse(null);
        Hall hall = show.getHall();
        User user = userrepository.findById(bookedseatRequestDto.getUsersysId()).orElse(null);
        String seatId = bookedseatRequestDto.getSeatId();
        BookedSeat bk = bookedSeatrepository.isSeatBooked(seatId,show.getSysId());
        if(bk == null){
            BookedSeat bookedSeat = applicationtransformer.transformBookedseatRequestdtotoBookedseat(show,seatId,bookedseatRequestDto.getUsersysId());
            bookedSeatrepository.save(bookedSeat);
            return bookedSeat;
        }else{
            throw new UnAuthorizedException("The seat you selected is no longer available");
        }



    }
}
