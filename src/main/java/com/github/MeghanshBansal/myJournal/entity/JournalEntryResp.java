package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class JournalEntryResp {
    private String id;
    private String title;
    private String content;
    private String name;

    public JournalEntryResp(String id, String title, String content, String name) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.name = name;
    }
}
