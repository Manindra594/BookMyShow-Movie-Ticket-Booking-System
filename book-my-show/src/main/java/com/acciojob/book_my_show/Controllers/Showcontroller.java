package com.acciojob.book_my_show.Controllers;

import com.acciojob.book_my_show.Dtos.Showrequestdto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Services.Showservice;
import com.acciojob.book_my_show.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/show")
public class Showcontroller {

    private Showservice showService;

    @Autowired
    public Showcontroller(Showservice showService){
        this.showService = showService;
    }

    @PostMapping("/create-show")
    public ResponseEntity<Show> createshow(
            @RequestParam UUID hallSysId,
            @RequestParam UUID userSysId,
            @RequestBody Showrequestdto showRequestDto
    ){
        HashMap<String, String> exceptionMessage = new HashMap<>();
        try{
            return new ResponseEntity(showService.registershow(showRequestDto, hallSysId, userSysId), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
        }catch (InvalidAttributesException e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
        }catch (UnAuthorizedException e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findshows")
    public ResponseEntity Findshows(@RequestParam String city,
                                @RequestParam String movieName){
      List<Show> shows = showService.getshowsbycityandmoviename(city,movieName);
      return new ResponseEntity(shows,HttpStatus.OK);
    }

    @GetMapping("/seat-status")
        public ResponseEntity getShowSeatStatus(@RequestParam UUID showsysid){
         return new ResponseEntity(showService.findShowSeatStatus(showsysid),HttpStatus.OK);
        }

}
