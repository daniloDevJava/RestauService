package com.projet.foodGo.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class AdminDto extends UserDto {

    private UUID id;

    private String EntryKey;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getEntryKey() {
        return EntryKey;
    }

    public void setEntryKey(String entryKey) {
        EntryKey = entryKey;
    }
}
