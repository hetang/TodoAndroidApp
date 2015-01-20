package com.air.todoapp.model;

/**
 * Created by hetashah on 1/20/15.
 */
public class TodoItem {
    public enum STATUS {
        COMPLETED, INCOMPLETE, INPROGRESS
    };

    private String item;
    private STATUS status;

    public TodoItem(String item) {
        this.item = item;
        this.status = STATUS.INCOMPLETE;
    }

    public String getItem() {
        return item;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
