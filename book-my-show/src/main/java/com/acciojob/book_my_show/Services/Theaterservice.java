package com.acciojob.book_my_show.Services;

import com.acciojob.book_my_show.Dtos.TheaterRequestdto;
import com.acciojob.book_my_show.Enums.Usertype;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Exceptions.UserNotFound;
import com.acciojob.book_my_show.Repository.Theaterrepository;
import com.acciojob.book_my_show.Repository.Userrepository;
import com.acciojob.book_my_show.Transformer.Applicationtransformer;
import com.acciojob.book_my_show.models.Theater;
import com.acciojob.book_my_show.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;
import java.util.UUID;

@Service
public class Theaterservice {
    private Userrepository userrepository;
    private Applicationtransformer applicationtransformer;
    private Theaterrepository theaterrepository;
    @Autowired
    public Theaterservice(Userrepository userrepository,Applicationtransformer applicationtransformer,Theaterrepository theaterrepository){
        this.userrepository = userrepository;
        this.applicationtransformer = applicationtransformer;
        this.theaterrepository = theaterrepository;

    }

  public Theater registertheater(TheaterRequestdto theaterRequestdto, UUID userSysId){
      User user = userrepository.findById(userSysId).orElse(null);
      if(user == null){
       throw new UserNotFound(String.format("user with id %s does not exist",userSysId.toString()));
      }
      if(!user.getUserType().equals(Usertype.THEATER_OWNER.toString())){
          throw new UnAuthorizedException("user is not allowed to create theater");
      }
      Theater theater = applicationtransformer.transformtheaterrequestdtototheater(theaterRequestdto,user);
      theaterrepository.save(theater);
      return theater;

  }

  public Theater Verifytheater(UUID Theatersysid) throws InvalidAttributesException{
      Optional<Theater> theater = theaterrepository.findById(Theatersysid);
      if(theater.isEmpty()){
          throw new InvalidAttributesException("Invalid theaterid passed");
      }
      return theater.get();
  }

}
