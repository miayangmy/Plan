package com.app.plan;

public class Note {
    private long id;
    private String content;

    public Note() {
    }

    public Note(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

}