package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.FinalResponse;
import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.entity.User;
import com.github.MeghanshBansal.myJournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @Autowired
    private UserService userService;
    @GetMapping("/ping")
    public static String ping(){
        return "pong";
    }

    @PostMapping("/create")
    public ResponseEntity<FinalResponse<Boolean>> createUser(@RequestBody User user) {
        ServiceResponse<Boolean> res = userService.createUser(user);
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
}
