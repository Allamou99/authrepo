package com.example.authservice.Repository;

import com.example.authservice.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    @Query("{Username:'?0'}")
    Optional<User> findByUsername(String username);

}
