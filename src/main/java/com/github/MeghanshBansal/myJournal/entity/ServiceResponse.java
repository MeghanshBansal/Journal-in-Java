package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    private Error error;
    public ServiceResponse(T value, Error error) {
        this.value = value;
        this.error = error;
    }
    private T value;
}


