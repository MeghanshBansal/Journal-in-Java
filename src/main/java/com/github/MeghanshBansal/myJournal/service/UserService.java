package com.github.MeghanshBansal.myJournal.service;

import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.entity.User;
import com.github.MeghanshBansal.myJournal.entity.UserResp;
import com.github.MeghanshBansal.myJournal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repo;

    public ServiceResponse<List<UserResp>> getAll() {
        try {
            List<User> users = repo.findAll();
            List<UserResp> resp = new ArrayList<>();
            for (User user : users) {
                resp.add(new UserResp(user.getId().toString(), user.getName()));
            }
            return new ServiceResponse<>(resp, null);
        } catch (Exception e) {
            log.error("failed to get the user list from the database with the exception {}", e.toString());
            return new ServiceResponse<>(null, new Error("failed to get the user data from database"));
        }
    }

    public ServiceResponse<UserResp> getUserById(ObjectId id) {
        try {
            Optional<User> user = repo.findById(id);
            if (user.isPresent()) {
                return new ServiceResponse<>(
                        new UserResp(
                                user.get().getId().toString(), user.get().getName()),
                        null
                );
            } else {
                new ServiceResponse<>(
                        new UserResp(), new Error("entry not present")
                );
            }
        } catch (Exception e) {
            log.error("failed to get the data from the database with exception {}", e.toString());
            return new ServiceResponse<>(
                    new UserResp(), new Error("entry not present")
            );
        }

        return new ServiceResponse<>(
                new UserResp(), new Error("entry not present")
        );
    }

    public ServiceResponse<User> getUserByUserName(String userName) {
        try {
            Optional<User> user = repo.findByUserName(userName);
            return user.map(value -> new ServiceResponse<>(
                    value,
                    null
            )).orElseGet(() -> new ServiceResponse<>(
                    null,
                    new Error("no user present")
            ));
        } catch (Exception e) {
            log.error("failed to fetch user from the database with exception {}", e.toString());
            return new ServiceResponse<>(
                    null,
                    new Error("failed to fetch the user from the database")
            );
        }
    }

    public ServiceResponse<Boolean> createUser(User user) {
        try {
            repo.save(user);
            return new ServiceResponse<>(true, null);
        } catch (Exception e) {
            log.error("failed to save the user in the database with exception {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to save the data in the database"));
        }
    }

    public ServiceResponse<Boolean> deleteUserById(ObjectId id) {
        try {
            repo.deleteById(id);
            return new ServiceResponse<>(true, null);
        } catch (Exception e) {
            log.error("failed to delete the data with exception {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to delete teh user data"));
        }
    }

    public ServiceResponse<Boolean> updateUserById(ObjectId id, User updateUser) {
        Optional<User> user = repo.findById(id);
        if (user.isPresent()) {
            user.get().setName(updateUser.getName());
            user.get().setPassword(updateUser.getPassword());
            try {
                repo.save(user.get());
                return new ServiceResponse<>(true, null);
            } catch (Exception e) {
                log.error("failed to update the entry into db with exception {}", e.toString());
                return new ServiceResponse<>(false, new Error("failed to update the entry into db"));
            }
        } else {
            log.error("failed to update the entry into db");
            return new ServiceResponse<>(false, new Error("failed to update the entry into db"));
        }
    }
}
