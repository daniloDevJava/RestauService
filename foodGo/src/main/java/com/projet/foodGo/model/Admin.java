package com.projet.foodGo.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Admin extends User {


    private String EntryKey;

    public String getEntryKey() {
        return EntryKey;
    }

    public void setEntryKey(String entryKey) {
        EntryKey = entryKey;
    }
}
