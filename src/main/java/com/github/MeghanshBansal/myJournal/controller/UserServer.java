package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.FinalResponse;
import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.entity.User;
import com.github.MeghanshBansal.myJournal.entity.UserResp;
import com.github.MeghanshBansal.myJournal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserServer {
    @Autowired
    private UserService service;

    @GetMapping("/get-all")
    public ResponseEntity<FinalResponse<List<UserResp>>> getAll() {
        ServiceResponse<List<UserResp>> users = service.getAll();
        if (users.getError() != null) {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(500, "failed to fetch the data"),
                            new ArrayList<>()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(200, "data fetched successfully"),
                            users.getValue()
                    ),
                    HttpStatus.OK
            );
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FinalResponse<UserResp>> getOne(@PathVariable ObjectId id) {
        ServiceResponse<UserResp> user = service.getUserById(id);
        if (user.getError() != null) {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(500, "failed to fetch the data"),
                            new UserResp()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(200, "data fetched successfully"),
                            user.getValue()
                    ),
                    HttpStatus.OK
            );
        }
    }

    @PostMapping("/create")
    public ResponseEntity<FinalResponse<Boolean>> createUser(@RequestBody User user) {
        ServiceResponse<Boolean> res = service.createUser(user);
        if (res.getError() != null) {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(500, "failed to save the data"),
                            false
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(200, "data saved successfully"),
                            res.getValue()
                    ),
                    HttpStatus.OK
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FinalResponse<Boolean>> deleteUser(@PathVariable ObjectId id) {
        ServiceResponse<Boolean> res = service.deleteUserById(id);
        if (res.getError() != null) {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(500, "failed to delete the data"),
                            false
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(200, "deleted successfully"),
                            false
                    ),
                    HttpStatus.OK
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FinalResponse<Boolean>> updateUser(@PathVariable ObjectId id, @RequestBody User newUser) {
        ServiceResponse<Boolean> res = service.updateUserById(id, newUser);
        if (res.getError() != null) {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(500, "failed to update the entry"),
                            false
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            return new ResponseEntity<>(
                    new FinalResponse<>(
                            new FinalResponse.Meta(200, "failed to update the entry"),
                            true
                    ),
                    HttpStatus.OK
            );
        }
    }
}
