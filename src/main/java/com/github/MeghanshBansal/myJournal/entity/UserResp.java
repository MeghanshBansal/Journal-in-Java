package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResp {
    private String id;
    private String name;

    public UserResp(String id, String name){
        this.id = id;
        this.name = name;
    }
}
