package com.github.MeghanshBansal.myJournal.controller;

import com.github.MeghanshBansal.myJournal.entity.FinalResponse;
import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.service.JournalEntryService;
import org.bson.types.ObjectId;
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
        ServiceResponse<List<JournalEntry>> entries = service.getAll();
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

    @PostMapping("/create/{userName}")
    public ResponseEntity<FinalResponse<Boolean>> createEntry(@RequestBody JournalEntry entry, @PathVariable String userName) {
        ServiceResponse<Boolean> res = service.saveEntry(entry, userName);
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
    public ResponseEntity<FinalResponse<Boolean>> deleteEntryById(@PathVariable ObjectId id) {
        ServiceResponse<Boolean> resp = service.deleteEntryById(id);
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
    public ResponseEntity<FinalResponse<Boolean>> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry updateEntry) {
        ServiceResponse<Boolean> resp = service.updateEntryById(id, updateEntry);
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
    public ResponseEntity<FinalResponse<JournalEntry>> getEntryById(@PathVariable ObjectId id) {
        return new ResponseEntity<>(new FinalResponse<>(
                new FinalResponse.Meta(200, "fetched successfully"),
                service.getEntryById(id).getValue()
        ), HttpStatus.OK
        );
    }

    @GetMapping("/change-assignedto/{id}/{userName}")
    public ResponseEntity<Boolean> updateAssignedTo(@PathVariable ObjectId id, @PathVariable String userName){
        return new ResponseEntity<>(null, null);
    }
}
