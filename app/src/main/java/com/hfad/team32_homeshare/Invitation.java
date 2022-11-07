package com.hfad.team32_homeshare;

import java.util.Map;

public class Invitation {
    public String invitationID;
    public String ownerID;
    public String homeID;
    public String date;
    public int numRoommatesCapacity;
    public int numSpotsLeft;
    public String fullName;
    public Map<String, Object> home;
    public String expectation;

    public Invitation() {}

    public Invitation(String invitationID, String date) {
        this.invitationID = invitationID;
        this.date = date;
    }

    public String getInvitationID() {
        return invitationID;
    }


    public String getDate() {
        return date;
    }
}
