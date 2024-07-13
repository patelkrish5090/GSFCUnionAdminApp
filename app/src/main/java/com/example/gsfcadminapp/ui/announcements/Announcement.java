package com.example.gsfcadminapp.ui.announcements;

public class Announcement {
    private String id;
    private String date;
    private String title;
    private String details;
    private String link;

    public Announcement() {
    }

    public Announcement(String id, String date, String title, String details, String link) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.details = details;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }
    public String getLink() {return link;}
}
