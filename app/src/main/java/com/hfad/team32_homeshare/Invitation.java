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
    public Boolean userPriceRange;
    public int minPrice;
    public int maxPrice;
    public int onePrice;
    public String ownerGender;
    public int ownerClassStandingNum;

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

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return ownerGender;
    }

    public int getOwnerClassStandingNum() {
        return ownerClassStandingNum;
    }

    public float getDistance() {
        return Float.parseFloat(home.get("distanceFromCampus").toString());
    }

    public int getNumBed() {
        return Integer.parseInt(home.get("numBedrooms").toString());
    }

    public int getNumBath() {
        return Integer.parseInt(home.get("numBathrooms").toString());
    }

    public int getMaxPrice() {
        return Integer.parseInt(home.get("maxPrice").toString());
    }

    public int getNumRoommates() {
        return numRoommatesCapacity;
    }

    public String getDeadlineYear() {
        return home.get("deadlineYear").toString();
    }

    public String getYearPosted() {
        try {
            return date.split("/")[2];
        }
        catch (Exception e) {
            return "";
        }
    }
}
