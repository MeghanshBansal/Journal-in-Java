package com.github.MeghanshBansal.myJournal.entity;

public class JournalEntryServiceResponse<T> {
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public T getValue() {
        return value;
    }

    public JournalEntryServiceResponse(T value, Error error) {
        this.value = value;
        this.error = error;
    }

    public void setValue(T value) {
        this.value = value;
    }

    private T value;
}


