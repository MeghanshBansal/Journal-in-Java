package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class JournalEntry {
    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String title;
    private String content;
}
