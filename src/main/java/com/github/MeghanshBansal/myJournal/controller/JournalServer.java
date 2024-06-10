package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.FinalResponse;
import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalServer {

    private final Map<String, JournalEntry> journalEntryMap = new HashMap<>();
    @GetMapping("/get-all")
    public FinalResponse<List<JournalEntry>> sayHello(){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "fetched successfully"),
                new ArrayList<JournalEntry>(journalEntryMap.values())
        );
    }

    @PostMapping("/create")
    public FinalResponse<Boolean> createEntry(@RequestBody JournalEntry entry){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "created successfully"),
                journalEntryMap.put(entry.getId(), entry) != null
        );
    }

    @DeleteMapping("/delete/{id}")
    public FinalResponse<Boolean> deleteEntryById(@PathVariable String id){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "deleted successfully"),
                journalEntryMap.remove(id) != null
        );
    }

    @PutMapping("update/{id}")
    public FinalResponse<Boolean> updateEntryById(@PathVariable String id, @RequestBody JournalEntry updateEntry){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "updated successfully"),
                journalEntryMap.put(id, updateEntry) != null
        );
    }

    @GetMapping("/get/{id}")
    public FinalResponse<JournalEntry> getEntryById(@PathVariable String id){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "fetched successfully"),
                journalEntryMap.get(id)
        );
    }
}
