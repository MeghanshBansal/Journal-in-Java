package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String title;
    private String content;
    @DBRef
    private User owner;

    private JournalEntry(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
