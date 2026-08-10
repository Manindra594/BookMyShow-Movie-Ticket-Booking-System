package com.acciojob.book_my_show.Controllers;

import com.acciojob.book_my_show.Dtos.Hallrequestdto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Exceptions.UserNotFound;
import com.acciojob.book_my_show.Services.Hallservice;
import com.acciojob.book_my_show.models.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/Hall")
public class Hallcontroller {
  private Hallservice hallservice;

  @Autowired
  public Hallcontroller(Hallservice hallservice){
      this.hallservice = hallservice;
  }

    @PostMapping("/createhall")
    public ResponseEntity<Hall> createhall(
            @RequestBody Hallrequestdto hallrequestdto,
            @RequestParam UUID theaterownersysid,
            @RequestParam UUID theatersysid
    ){
        HashMap<String,String> map = new HashMap<>();
    try{
        Hall hall = hallservice.registerhall(hallrequestdto,theaterownersysid,theatersysid);
        return new ResponseEntity(hall, HttpStatus.CREATED);
    }catch (UserNotFound e){
        map.put("messege",e.getMessage());
        return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
    }catch (UnAuthorizedException e){
        map.put("messege",e.getMessage());
        return new ResponseEntity(map,HttpStatus.UNAUTHORIZED);
    }catch (InvalidAttributesException e){
        map.put("messege",e.getMessage());
        return new ResponseEntity(map,HttpStatus.BAD_REQUEST);
    }catch (Exception e){
        map.put("messege",e.getMessage());
        return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
}
