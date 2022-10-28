package com.hfad.team32_homeshare;

public class Invitation {
    public String invitationID;
    public String homeID;
    public String date;
    public int numRoommatesCapacity;
    public int numSpotsLeft;

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
