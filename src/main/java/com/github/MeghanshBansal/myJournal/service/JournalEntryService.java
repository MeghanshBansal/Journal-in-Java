package com.github.MeghanshBansal.myJournal.service;

import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
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

    public List<JournalEntry> getAll(){
        try{
            return (ArrayList<JournalEntry>) repo.findAll();
        }catch(Exception e){
            log.error("failed to get all the records from database with exception: {}", e.toString());
        }
        return new ArrayList<>();
    }

    public JournalEntry getEntryById(String id){
        try{
            Optional<JournalEntry> entry = repo.findById(id);
            if (entry.isPresent()) {
                return entry.get();
            }else {
                log.info("entry not present with id: {}", id);
                return null;
            }
        }catch(Exception e){
            log.error("failed to get entry from the database with exception: {}", e.toString());
            return null;
        }
    }

    public boolean saveEntry(JournalEntry newEntry){
        try{
            repo.save(newEntry);
            return true;
        }catch (Exception e){
            log.error("failed to save entry from the database with exception: {}", e.toString());
            return false;
        }
    }

    public boolean deleteEntryById(String id){
        try{
            repo.deleteById(id);return true;
        }catch (Exception e){
            log.error("failed to delete entry from the database with exception: {}", e.toString());
            return false;
        }
    }

    public boolean updateEntryById(String id, JournalEntry updateEntry){
        JournalEntry entry = this.getEntryById(id);
        if (entry != null){
            entry.setTitle(updateEntry.getTitle());
            entry.setContent(updateEntry.getContent());
        }else{
            log.error("failed to get entry from the database");
            return false;
        }

        return this.saveEntry(entry);
    }
}
