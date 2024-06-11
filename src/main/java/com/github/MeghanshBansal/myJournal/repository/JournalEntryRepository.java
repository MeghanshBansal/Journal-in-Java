package com.github.MeghanshBansal.myJournal.repository;

import com.github.MeghanshBansal.myJournal.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
}
