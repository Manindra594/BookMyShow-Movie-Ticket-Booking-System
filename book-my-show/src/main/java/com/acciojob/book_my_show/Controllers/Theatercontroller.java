package com.acciojob.book_my_show.Controllers;


import com.acciojob.book_my_show.Dtos.TheaterRequestdto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Exceptions.UserNotFound;
import com.acciojob.book_my_show.Services.Theaterservice;
import com.acciojob.book_my_show.models.Theater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/Theater")
public class Theatercontroller {
    private Theaterservice theaterservice;

    public Theatercontroller(Theaterservice theaterservice){
        this.theaterservice = theaterservice;
    }

    @PostMapping("/create/Theater")
    public ResponseEntity<Theater> createtheater(@RequestBody TheaterRequestdto theaterrequestdto,
                                                 @RequestParam UUID userSysId){
        try{
            Theater theater = theaterservice.registertheater(theaterrequestdto,userSysId);
            return new ResponseEntity<>(theater, HttpStatus.CREATED);
        }catch (UserNotFound e){
            HashMap<String,String> map = new HashMap<>();
            map.put("messege",e.getMessage());
            return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
        }catch (UnAuthorizedException e){
            HashMap<String,String> map = new HashMap<>();
            map.put("messege",e.getMessage());
            return new ResponseEntity(map,HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            HashMap<String,String> map = new HashMap<>();
            map.put("messege",e.getMessage());
            return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
