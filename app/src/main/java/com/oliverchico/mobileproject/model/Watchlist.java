package com.oliverchico.mobileproject.model;

public class Watchlist {
    private int id;
    private String title;
    private Status status;

    public Watchlist(int id, String title, Status status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Watchlist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
