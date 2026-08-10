package com.acciojob.book_my_show.Services;

import com.acciojob.book_my_show.Dtos.SeatStatusResponsedto;
import com.acciojob.book_my_show.Dtos.Showrequestdto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Repository.BookedSeatrepository;
import com.acciojob.book_my_show.Repository.Showrepository;
import com.acciojob.book_my_show.Transformer.Applicationtransformer;
import com.acciojob.book_my_show.models.BookedSeat;
import com.acciojob.book_my_show.models.Hall;
import com.acciojob.book_my_show.models.Show;
import com.acciojobs.book_my_show.utilitis.SystemUtility;
import com.acciojob.book_my_show.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class Showservice {
    private Userservice userService;
    private Hallservice hallService;
    private Showrepository showrepository;
    private Applicationtransformer applicationTransformer;
    private BookedSeatrepository bookedSeatrepository;

    @Autowired
    public Showservice(Userservice userService,
                       Hallservice hallService,
                       Showrepository showrepository,
                       Applicationtransformer applicationTransformer,BookedSeatrepository bookedSeatrepository){
        this.userService = userService;
        this.hallService = hallService;
        this.showrepository = showrepository;
        this.applicationTransformer = applicationTransformer;
        this.bookedSeatrepository = bookedSeatrepository;
    }

    public boolean isOverLappingShow(List<Show> shows, Long startTime, Long endTime){
        Collections.sort(shows);
        for(Show show : shows){
            if(show.getEndTimeInSeconds() >= startTime){
                return true;
            }
        }
        return false;
    }

    public Show registershow(
            Showrequestdto showRequestDto,
            UUID hallSysId,
            UUID userSysId
    ) throws InvalidAttributesException {
        User user  = userService.Verifytheaterowner(userSysId);
        Hall hall =  hallService.verifyHallSysId(hallSysId);

        if(!hall.getTheater().getOwner().getSysId().equals(user.getSysId())){
            throw new UnAuthorizedException("user is not allowed to create show in hall");
        }
        LocalDateTime starttime = showRequestDto.getStartTime();
        Long starttimeinseconds = SystemUtility.convertShowTimeInSeconds(starttime);
        LocalDateTime endtime = showRequestDto.getEndTime();
        Long endtimeinseconds = SystemUtility.convertShowTimeInSeconds(endtime);

        List<Show> shows = showrepository.findByHall(hall);
        boolean isOverLapping = this.isOverLappingShow(shows, starttimeinseconds, endtimeinseconds);
        if(isOverLapping){
            throw new IllegalArgumentException("Overlapping timings");
        }
        Show show = applicationTransformer.transformShowDtoToShow(showRequestDto,
                hall,
                user,
                starttimeinseconds,
                endtimeinseconds);

        showrepository.save(show);
        return show;

    }

    public List<Show> getshowsbycityandmoviename(String city, String moviename){
        List<Show> shows = showrepository.findByMovieName(moviename);
        List<Show> filterdbycity = new ArrayList<>();
        for(Show show : shows){
            if(show.getHall().getTheater().getCity().equals(city)){
                filterdbycity.add(show);
            }
        }
        return filterdbycity;
    }

    public List<SeatStatusResponsedto> findShowSeatStatus(UUID showSysId){
        Show show = showrepository.findById(showSysId).orElse(null);
        Hall hall = show.getHall();
        String rowrange = hall.getRowRange();
        int seatcapacity = hall.getSeatCapacity();

        String[] rowArr = rowrange.split("-");
        char startRange = rowArr[0].charAt(0);
        char endRange = rowArr[1].charAt(0);
        List<SeatStatusResponsedto> SeatstatusList = new ArrayList<>();
        for(char ch = startRange; ch <= endRange; ch++){
            for(int i = 1; i <= seatcapacity; i++){
                String seatid = ch + " " + i;
                SeatStatusResponsedto seatStatusResponsedto = new SeatStatusResponsedto();
                BookedSeat bookedSeat = bookedSeatrepository.isSeatBooked(seatid,show.getSysId());
                seatStatusResponsedto.setSeatId(seatid);
                seatStatusResponsedto.setShowId(show.getSysId());
                if(bookedSeat == null){
                 seatStatusResponsedto.setSeatStatus("UNBOOKED");
                }else {
                 seatStatusResponsedto.setSeatStatus("BOOKED");
                }
               SeatstatusList.add(seatStatusResponsedto);
            }
        }
        return SeatstatusList;

    }
}
