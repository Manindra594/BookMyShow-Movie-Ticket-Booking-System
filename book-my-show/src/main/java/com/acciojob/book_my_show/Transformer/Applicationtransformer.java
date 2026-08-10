package com.acciojob.book_my_show.Transformer;

import com.acciojob.book_my_show.Dtos.Hallrequestdto;
import com.acciojob.book_my_show.Dtos.Showrequestdto;
import com.acciojob.book_my_show.Dtos.TheaterRequestdto;
import com.acciojob.book_my_show.Dtos.Userdto;
import com.acciojob.book_my_show.models.Hall;
import com.acciojob.book_my_show.models.Show;
import com.acciojob.book_my_show.models.Theater;
import com.acciojob.book_my_show.models.User;
import org.springframework.stereotype.Component;
import com.acciojobs.book_my_show.utilitis.SystemUtility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component

public class Applicationtransformer {

    public User transformuserdtotouser(Userdto userdto,String usertype){
      return User.builder()
              .userId(SystemUtility.generate("User"))
              .userType(usertype)
              .fullName(userdto.getFullname())
              .email(userdto.getEmail())
              .address(userdto.getAddress())
              .password(userdto.getPassword())
              .PhoneNumber(userdto.getPhoneNumber())
              .createdAt(LocalDateTime.now())
              .updatedAt(LocalDateTime.now())
              .createdBy("system")
              .updatedBy("system")
              .build();

    }
    public Theater transformtheaterrequestdtototheater(TheaterRequestdto theaterRequestdto,User user){
        return Theater.builder()
                .TheaterId(SystemUtility.generate("Theater"))
                .theatername(theaterRequestdto.getTheatername())
                .city(theaterRequestdto.getCity())
                .state(theaterRequestdto.getState())
                .owner(user)
                .country(theaterRequestdto.getCountry())
                .address(theaterRequestdto.getAddress())
                .halls(new ArrayList<>())
                .createdBy(user.getFullName())
                .updatedBy(user.getFullName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Hall transformDtoToHallModel(
            Hallrequestdto hallRequestDto,
            Theater theater
    ){
        return Hall.builder()
                .hallId(SystemUtility.generate("HALL"))
                .hallName(hallRequestDto.getHallName())
                .theater(theater)
                .seatCapacity(hallRequestDto.getSeatCapacityPerRow())
                .rowRange(hallRequestDto.getRowRange())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(theater.getOwner().getEmail())
                .updatedBy(theater.getOwner().getEmail())
                .build();
    }

    public Show transformShowDtoToShow(
            Showrequestdto showRequestDto,
            Hall hall,
            User user,
            Long startTimeInSeconds,
            Long endTimeInSeconds
    ){
        return Show.builder()
                .hall(hall)
                .showId(SystemUtility.generate("SHOW"))
                .endTimeInSeconds(endTimeInSeconds)
                .startTimeInSeconds(startTimeInSeconds)
                .showPrice(showRequestDto.getShowPrice())
                .startTime(showRequestDto.getStartTime())
                .endTime(showRequestDto.getEndTime())
                .createdBy(user.getEmail())
                .updatedBy(user.getEmail())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .movieName(showRequestDto.getMovieName())
                .build();
    }
}
