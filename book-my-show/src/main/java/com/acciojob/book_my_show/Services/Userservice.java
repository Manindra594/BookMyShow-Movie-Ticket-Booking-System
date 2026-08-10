package com.acciojob.book_my_show.Services;

import com.acciojob.book_my_show.Dtos.Loginrequestdto;
import com.acciojob.book_my_show.Dtos.Userdto;
import com.acciojob.book_my_show.Enums.Usertype;
import com.acciojob.book_my_show.Exceptions.UnAuthorizedException;
import com.acciojob.book_my_show.Exceptions.UserNotFound;
import com.acciojob.book_my_show.Repository.Userrepository;
import com.acciojob.book_my_show.Transformer.Applicationtransformer;
import com.acciojob.book_my_show.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class Userservice {
    private Applicationtransformer transformer;
    private Userrepository userrepository;
    @Autowired
    public Userservice(Applicationtransformer transformer,Userrepository userrepository){
        this.transformer = transformer;
        this.userrepository = userrepository;
    }
    public User registercustomer(Userdto userdto){
        User user = transformer.transformuserdtotouser(userdto, Usertype.CUSTOMER.toString());
        userrepository.save(user);
        return user;
    }
    public  User registerTheaterowner(Userdto userdto){
       User user =  transformer.transformuserdtotouser(userdto,Usertype.THEATER_OWNER.toString());
       userrepository.save(user);
       return user;
    }

    public User Verifytheaterowner(UUID theaterownersysid){
        Optional<User> user = userrepository.findById(theaterownersysid);
        if(user.isEmpty()){
            throw  new UserNotFound(String.format("user with %s id does not exist",theaterownersysid));
        }
        if(!user.get().getUserType().equals(Usertype.THEATER_OWNER.toString())){
            throw new UnAuthorizedException("user is not a theaterowner");
        }
        return user.get();
    }
    public User loginverification(Loginrequestdto loginrequestdto){
       Optional<User> optionalUser = userrepository.findByEmail(loginrequestdto.getEmail());
       if(optionalUser.isEmpty()){
           throw new UserNotFound(String.format("user with %s does not exist",loginrequestdto.getEmail()));
       }
       User user = optionalUser.get();
       if(!user.getPassword().equals(loginrequestdto.getPassword())){
           throw new UnAuthorizedException("Entered Incorrect password");
       }
       return user;
    }
}
