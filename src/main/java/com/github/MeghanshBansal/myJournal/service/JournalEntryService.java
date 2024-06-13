package com.github.MeghanshBansal.myJournal.service;

import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.entity.User;
import com.github.MeghanshBansal.myJournal.repository.JournalEntryRepository;
import com.github.MeghanshBansal.myJournal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepository journalRepo;
    @Autowired
    private UserRepository userRepo;

    public ServiceResponse<List<JournalEntry>> getAll() {
        ServiceResponse<ArrayList<JournalEntry>> resp;
        try {
            return new ServiceResponse<>((ArrayList<JournalEntry>) journalRepo.findAll(), null);
        } catch (Exception e) {
            log.error("failed to get all the records from database with exception: {}", e.toString());
            return new ServiceResponse<>(new ArrayList<JournalEntry>(), new Error("failed to get entries"));
        }
    }

    public ServiceResponse<JournalEntry> getEntryById(ObjectId id) {
        try {
            Optional<JournalEntry> entry = journalRepo.findById(id);
            if (entry.isPresent()) {
                return new ServiceResponse<>(entry.get(), null);
            } else {
                log.info("entry not present with id: {}", id);
                return new ServiceResponse<>(null, new Error("entry does not exist"));
            }
        } catch (Exception e) {
            log.error("failed to get entry from the database with exception: {}", e.toString());
            return new ServiceResponse<>(null, new Error("failed to get the entry"));
        }
    }

    public ServiceResponse<Boolean> saveEntry(JournalEntry newEntry, String userName) {
        try {
            Optional<User> user = userRepo.findByUserName(userName);
            if (user.isPresent()) {
                newEntry.setOwner(user.get());
                journalRepo.save(newEntry);
                return new ServiceResponse<>(true, null);
            } else {
                log.error("user not present");
                return new ServiceResponse<>(false, new Error("user not present"));
            }
        } catch (Exception e) {
            log.error("failed to save entry from the database with exception: {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to save entry in the database"));
        }
    }

    public ServiceResponse<Boolean> deleteEntryById(ObjectId id) {
        try {
            journalRepo.deleteById(id);
            return new ServiceResponse<>(true, null);
        } catch (Exception e) {
            log.error("failed to delete entry from the database with exception: {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to delete the entry"));
        }
    }

    public ServiceResponse<Boolean> updateEntryById(ObjectId id, JournalEntry updateEntry) {
        ServiceResponse<JournalEntry> entry = this.getEntryById(id);
        JournalEntry newEntry = entry.getValue();
        if (entry.getValue() != null) {
            newEntry.setTitle(updateEntry.getTitle());
            newEntry.setContent(updateEntry.getContent());
        } else {
            log.error("failed to get entry from the database");
            return new ServiceResponse<>(false, new Error("failed to update the entry"));
        }

        ServiceResponse<Boolean> resp = this.saveEntry(newEntry, entry.getValue().getOwner().getUserName());
        return new ServiceResponse<>(resp.getValue(), resp.getError());
    }

    public ServiceResponse<Boolean> updateOwnerOfJournal(ObjectId id, String userName){
        Optional<JournalEntry> entry = journalRepo.findById(id);
        if (entry.isEmpty()) return new ServiceResponse<>(false, new Error("journal entry not found"));
        Optional<User> user = userRepo.findByUserName(userName);
        if (user.isEmpty()) return new ServiceResponse<>(false, new Error("failed to get the user details"));
        entry.get().setOwner(user.get());
        try {
            journalRepo.save(entry.get());
            return new ServiceResponse<>(true, null);
        }catch (Exception e){
            log.error("failed to save the updates with exception {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to save the updated details"));
        }
    }
}
