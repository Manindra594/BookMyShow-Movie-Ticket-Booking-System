package com.acciojob.book_my_show.Controllers;

import com.acciojob.book_my_show.Dtos.Loginrequestdto;
import com.acciojob.book_my_show.Dtos.Userdto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Exceptions.UserNotFound;
import com.acciojob.book_my_show.Services.Userservice;
import com.acciojob.book_my_show.models.User;
import jdk.jshell.Snippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class Usercontroller {
    private Userservice userservice;
    @Autowired
    public  Usercontroller(Userservice userservice){
        this.userservice = userservice;
    }



    @PostMapping("/customer/register")
    public ResponseEntity<User> createcustomer(@RequestBody Userdto userdto){
        log.info(String.format("recived customer registration request %s",userdto.toString()));
      User user = userservice.registercustomer(userdto);
      return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/Theater-ownwer/register")
    public ResponseEntity<User> createTheaterowner(@RequestBody Userdto userdto){
        log.info(String.format("recived owner registration request %s",userdto.toString()));
        User user =  userservice.registerTheaterowner(userdto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        return "Controller Working";
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String,String>> login(@RequestBody Loginrequestdto loginrequestdto){
        HashMap<String,String> map = new HashMap<>();
        try {
            User user = userservice.loginverification(loginrequestdto);
            map.put(user.getEmail(),"Login successful");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (UserNotFound e) {
            map.put("messege",e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } catch (UnAuthorizedException e) {
            map.put("messege",e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

    }

}
