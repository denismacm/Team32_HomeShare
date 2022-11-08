package com.hfad.team32_homeshare;

public class Response {
    public String responseID;
    public String senderName;
    public String recipientID;
    public Boolean accepted;
    public String senderID;
    public String date;
    public String message;
    public String address;
    public String invID;
    public String senderGender;

    public Response() {}

    public String getSenderName() {
        return senderName;
    }

    public String getSenderGender() {
        return senderGender;
    }

}