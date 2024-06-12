package com.github.MeghanshBansal.myJournal.service;

import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import com.github.MeghanshBansal.myJournal.entity.JournalEntryServiceResponse;
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

    public JournalEntryServiceResponse<List<JournalEntry>> getAll(){
        JournalEntryServiceResponse<ArrayList<JournalEntry>> resp;
        try{
             return new JournalEntryServiceResponse<>((ArrayList<JournalEntry>) repo.findAll(), null);
        }catch(Exception e){
            log.error("failed to get all the records from database with exception: {}", e.toString());
            return new JournalEntryServiceResponse<>(new ArrayList<JournalEntry>(), new Error("failed to get entries"));
        }
    }

    public JournalEntryServiceResponse<JournalEntry> getEntryById(String id){
        try{
            Optional<JournalEntry> entry = repo.findById(id);
            if (entry.isPresent()) {
                return new JournalEntryServiceResponse<>(entry.get(), null);
            }else {
                log.info("entry not present with id: {}", id);
                return new JournalEntryServiceResponse<>(null, new Error("entry does not exist"));
            }
        }catch(Exception e){
            log.error("failed to get entry from the database with exception: {}", e.toString());
            return new JournalEntryServiceResponse<>(null, new Error("failed to get the entry"));
        }
    }

    public JournalEntryServiceResponse<Boolean> saveEntry(JournalEntry newEntry){
        try{
            repo.save(newEntry);
            return new JournalEntryServiceResponse<>(true, null);
        }catch (Exception e){
            log.error("failed to save entry from the database with exception: {}", e.toString());
            return new JournalEntryServiceResponse<>(false, new Error("failed to save entry in the database"));
        }
    }

    public JournalEntryServiceResponse<Boolean> deleteEntryById(String id){
        try{
            repo.deleteById(id);
            return new JournalEntryServiceResponse<>(true, null);
        }catch (Exception e){
            log.error("failed to delete entry from the database with exception: {}", e.toString());
            return new JournalEntryServiceResponse<>(false, new Error("failed to delete the entry"));
        }
    }

    public JournalEntryServiceResponse<Boolean> updateEntryById(String id, JournalEntry updateEntry){
        JournalEntryServiceResponse<JournalEntry> entry = this.getEntryById(id);
        JournalEntry newEntry = entry.getValue();
        if (entry.getValue() != null){
            newEntry.setTitle(updateEntry.getTitle());
            newEntry.setContent(updateEntry.getContent());
        }else{
            log.error("failed to get entry from the database");
            return new JournalEntryServiceResponse<>(false, new Error("failed to update the entry"));
        }

        JournalEntryServiceResponse<Boolean> resp = this.saveEntry(newEntry);
        return new JournalEntryServiceResponse<>(resp.getValue(), resp.getError());
    }
}
