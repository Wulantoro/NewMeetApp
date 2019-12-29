package com.example.meetap1.Chat;

import java.util.Date;

public class ChatMessage {
    private String text;
    private String member;
    private long time;

    public ChatMessage(String text, String member, long time) {
        this.text = text;
        this.member = member;

        time = new Date().getTime();
    }

    public ChatMessage() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
