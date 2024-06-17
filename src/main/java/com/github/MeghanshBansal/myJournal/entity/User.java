package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private List<String> roles;

    public User(String id, String name, String password, String[] roles){
        this.id = new ObjectId(id);
        this.name = name;
        this.password = password;
        if (roles != null && roles.length == 2){
            this.roles = List.of("USER", "ADMIN");
        }else{
            this.roles = List.of("USER");
        }
    }
}
