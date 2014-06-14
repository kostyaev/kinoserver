package ru.cybern.kinoserver.mobileapi.dto;

import java.util.Date;
import java.util.List;


public class UpdateResponse {

    private List<Update> updates;

    private Date updateDate;

    public List<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
