package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.entity.UserResp;
import com.github.MeghanshBansal.myJournal.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class Admin {
    @Autowired
    public UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> GetAllUsers(){
        ServiceResponse<List<UserResp>> users = userService.getAll();
        if (users.getError() != null) return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(users.getValue(), HttpStatus.OK);
    }
}
