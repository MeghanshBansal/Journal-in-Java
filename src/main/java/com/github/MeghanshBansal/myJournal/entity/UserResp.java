package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResp {
    private String id;
    private String name;
    private List<String> roles;

    public UserResp(String id, String name, List<String> roles){
        this.id = id;
        this.name = name;
        this.roles = roles;
    }
}
