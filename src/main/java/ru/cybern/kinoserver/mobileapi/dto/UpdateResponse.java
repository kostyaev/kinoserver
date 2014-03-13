package ru.cybern.kinoserver.mobileapi.dto;

import java.util.List;


public class UpdateResponse {

    private List<Update> updates;

    public List<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }
}
