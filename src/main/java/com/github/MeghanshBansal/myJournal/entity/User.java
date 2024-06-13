package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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

//    @DBRef
//    private List<JournalEntry> journalEntries = new ArrayList<>();
}
