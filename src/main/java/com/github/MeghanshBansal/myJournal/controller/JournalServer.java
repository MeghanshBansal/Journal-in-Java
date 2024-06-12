package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.FinalResponse;
import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import com.github.MeghanshBansal.myJournal.entity.JournalEntryServiceResponse;
import com.github.MeghanshBansal.myJournal.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalServer {
    @Autowired
    private JournalEntryService service;

    @GetMapping("/get-all")
    public ResponseEntity<FinalResponse<List<JournalEntry>>> getEntries() {
        JournalEntryServiceResponse<List<JournalEntry>> entries = service.getAll();
        if (entries.getError() != null) {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(500, "failed to fetch journal entries"),
                    null),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(200, "entries fetched successfully"),
                    entries.getValue()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/create")
    public ResponseEntity<FinalResponse<Boolean>> createEntry(@RequestBody JournalEntry entry) {
        JournalEntryServiceResponse<Boolean> res = service.saveEntry(entry);
        if (res.getValue()) {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(200, "entry created successfully"),
                    true),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(500, "failed to create the entry"),
                    true),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FinalResponse<Boolean>> deleteEntryById(@PathVariable String id) {
        JournalEntryServiceResponse<Boolean> resp = service.deleteEntryById(id);
        if (resp.getValue()) {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(200, "entry deleted successfully"),
                    true),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(500, "failed to delete the entry"),
                    true),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<FinalResponse<Boolean>> updateEntryById(@PathVariable String id, @RequestBody JournalEntry updateEntry) {
        JournalEntryServiceResponse<Boolean> resp = service.updateEntryById(id, updateEntry);
        if (resp.getValue()) {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(200, "entry updated successfully"),
                    true),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(new FinalResponse<>(
                    new FinalResponse.Meta(500, "failed to update the entry"),
                    true),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FinalResponse<JournalEntry>> getEntryById(@PathVariable String id) {
        return new ResponseEntity<>(new FinalResponse<>(
                new FinalResponse.Meta(200, "fetched successfully"),
                service.getEntryById(id).getValue()
        ), HttpStatus.OK
        );
    }
}
