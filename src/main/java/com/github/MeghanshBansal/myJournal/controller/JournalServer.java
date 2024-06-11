package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.FinalResponse;
import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import com.github.MeghanshBansal.myJournal.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalServer {
    @Autowired
    private JournalEntryService service;

    @GetMapping("/get-all")
    public FinalResponse<List<JournalEntry>> getEntries(){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "fetched successfully"),
                service.getAll()
        );
    }

    @PostMapping("/create")
    public FinalResponse<Boolean> createEntry(@RequestBody JournalEntry entry){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "created successfully"),
                service.saveEntry(entry)
        );
    }

    @DeleteMapping("/delete/{id}")
    public FinalResponse<Boolean> deleteEntryById(@PathVariable String id){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "deleted successfully"),
                service.deleteEntryById(id)
        );
    }

    @PutMapping("update/{id}")
    public FinalResponse<Boolean> updateEntryById(@PathVariable String id, @RequestBody JournalEntry updateEntry){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "updated successfully"),
                service.updateEntryById(id, updateEntry)
        );
    }

    @GetMapping("/get/{id}")
    public FinalResponse<JournalEntry> getEntryById(@PathVariable String id){
        return new FinalResponse<>(
                new FinalResponse.Meta(200, "fetched successfully"),
                service.getEntryById(id)
        );
    }
}
