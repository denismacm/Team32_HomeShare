package com.hfad.team32_homeshare;

public class Response {
    public String responsesID;
    public String userName;

    public Response() {}

    public Response(String responsesID, String userName) {
        this.responsesID = responsesID;
        this.userName = userName;
    }

    public String getResponsesID() {
        return responsesID;
    }

    public String getUserName() {
        return userName;
    }
}