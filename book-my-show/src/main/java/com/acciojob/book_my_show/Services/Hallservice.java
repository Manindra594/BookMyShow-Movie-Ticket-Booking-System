package com.acciojob.book_my_show.Services;

import com.acciojob.book_my_show.Dtos.Hallrequestdto;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Repository.Hallrepository;
import com.acciojob.book_my_show.Transformer.Applicationtransformer;
import com.acciojob.book_my_show.models.Hall;
import com.acciojob.book_my_show.models.Theater;
import com.acciojob.book_my_show.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;
import java.util.UUID;

@Service
public class Hallservice {
    private Userservice userservice;
    private Theaterservice theaterservice;
    private Applicationtransformer applicationtransformer;
    private Hallrepository hallrepository;

    @Autowired
    public Hallservice(Userservice userservice,Theaterservice theaterservice,Applicationtransformer applicationtransformer,
                       Hallrepository hallrepository){
        this.userservice =  userservice;
        this.theaterservice =  theaterservice;
        this.applicationtransformer = applicationtransformer;
        this.hallrepository = hallrepository;
    }

    public Hall registerhall(
            @RequestBody Hallrequestdto hallrequestdto,
            @RequestParam UUID Theaterownersysid,
            @RequestParam UUID theatersysid
    )throws Exception{
        //verifying user is exist or not if exist is a theaterowner or not
    User user = userservice.Verifytheaterowner(Theaterownersysid);
      //verifying theater
        Theater theater = theaterservice.Verifytheater(theatersysid);
        //verifying is this user is owner of given theater or not;
        if(!user.getSysId().equals(theater.getOwner().getSysId())){
            throw new UnAuthorizedException("user doest own this theater");
        }

        Hall hall = applicationtransformer.transformDtoToHallModel(hallrequestdto,theater);
        hallrepository.save(hall);
        return hall;

    }

    public Hall verifyHallSysId(UUID hallSysId) throws InvalidAttributesException {
        Optional<Hall> hall = hallrepository.findById(hallSysId);
        if(hall.isEmpty()){
            throw new InvalidAttributesException("Invalid hallId passed");
        }
        return hall.get();
    }
}
