package com.github.MeghanshBansal.myJournal.repository;

import com.github.MeghanshBansal.myJournal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByName(String name);
}
