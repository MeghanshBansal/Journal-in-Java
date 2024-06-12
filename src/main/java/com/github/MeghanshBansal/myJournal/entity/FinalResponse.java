package com.github.MeghanshBansal.myJournal.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FinalResponse<T> {
    @Data
    public static class Meta {
        public Meta(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int code;
        public String message;
    };

    public FinalResponse(Meta meta, T value) {
        this.meta = meta;
        this.value = value;
    }

    public Meta meta;
    public T value;

}

