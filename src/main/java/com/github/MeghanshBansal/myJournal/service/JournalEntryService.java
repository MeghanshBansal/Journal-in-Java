package com.github.MeghanshBansal.myJournal.service;

import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import com.github.MeghanshBansal.myJournal.entity.ServiceResponse;
import com.github.MeghanshBansal.myJournal.repository.JournalEntryRepository;
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
    private JournalEntryRepository repo;

    public ServiceResponse<List<JournalEntry>> getAll(){
        ServiceResponse<ArrayList<JournalEntry>> resp;
        try{
             return new ServiceResponse<>((ArrayList<JournalEntry>) repo.findAll(), null);
        }catch(Exception e){
            log.error("failed to get all the records from database with exception: {}", e.toString());
            return new ServiceResponse<>(new ArrayList<JournalEntry>(), new Error("failed to get entries"));
        }
    }

    public ServiceResponse<JournalEntry> getEntryById(String id){
        try{
            Optional<JournalEntry> entry = repo.findById(id);
            if (entry.isPresent()) {
                return new ServiceResponse<>(entry.get(), null);
            }else {
                log.info("entry not present with id: {}", id);
                return new ServiceResponse<>(null, new Error("entry does not exist"));
            }
        }catch(Exception e){
            log.error("failed to get entry from the database with exception: {}", e.toString());
            return new ServiceResponse<>(null, new Error("failed to get the entry"));
        }
    }

    public ServiceResponse<Boolean> saveEntry(JournalEntry newEntry){
        try{
            repo.save(newEntry);
            return new ServiceResponse<>(true, null);
        }catch (Exception e){
            log.error("failed to save entry from the database with exception: {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to save entry in the database"));
        }
    }

    public ServiceResponse<Boolean> deleteEntryById(String id){
        try{
            repo.deleteById(id);
            return new ServiceResponse<>(true, null);
        }catch (Exception e){
            log.error("failed to delete entry from the database with exception: {}", e.toString());
            return new ServiceResponse<>(false, new Error("failed to delete the entry"));
        }
    }

    public ServiceResponse<Boolean> updateEntryById(String id, JournalEntry updateEntry){
        ServiceResponse<JournalEntry> entry = this.getEntryById(id);
        JournalEntry newEntry = entry.getValue();
        if (entry.getValue() != null){
            newEntry.setTitle(updateEntry.getTitle());
            newEntry.setContent(updateEntry.getContent());
        }else{
            log.error("failed to get entry from the database");
            return new ServiceResponse<>(false, new Error("failed to update the entry"));
        }

        ServiceResponse<Boolean> resp = this.saveEntry(newEntry);
        return new ServiceResponse<>(resp.getValue(), resp.getError());
    }
}
