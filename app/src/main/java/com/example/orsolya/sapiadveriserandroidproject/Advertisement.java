package com.example.orsolya.sapiadveriserandroidproject;

import java.io.Serializable;

public class Advertisement implements Serializable {
    private Integer Identifier;
    private String EmailAddress;
    private String  Location;
    private String LongDescription;
    private String ShortDescription;
    private String PhoneNumber;
    private boolean Reported;
    private String Title;
    private String Image;

    public Advertisement(Integer identifier, String emailAddress, String location, String longDescription, String shortDescription, String phoneNumber, boolean reported, String title, String image) {
        Identifier = identifier;
        EmailAddress = emailAddress;
        Location = location;
        LongDescription = longDescription;
        ShortDescription = shortDescription;
        PhoneNumber = phoneNumber;
        Reported = reported;
        Title = title;
        Image = image;
    }

    public Integer getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(Integer identifier) {
        Identifier = identifier;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isReported() {
        return Reported;
    }

    public void setReported(boolean reported) {
        Reported = reported;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "Identifier=" + Identifier +
                ", EmailAddress='" + EmailAddress + '\'' +
                ", Location='" + Location + '\'' +
                ", LongDescription='" + LongDescription + '\'' +
                ", ShortDescription='" + ShortDescription + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Reported=" + Reported +
                ", Title='" + Title + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
