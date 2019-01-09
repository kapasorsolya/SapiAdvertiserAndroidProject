package com.example.orsolya.sapiadveriserandroidproject.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Advertisement implements Serializable {
    private String Identifier;
    private String  Location;
    private String LongDescription;
    private String ShortDescription;
    private String PhoneNumber;
    private boolean Reported;
    private String Title;
    private String Image;
    private String Uploader;
    private String UploaderPhoneNumber;
    private int counter;


    public Advertisement(String title, String image, String shortDescription) {
        this.Title = title;
        this.Image=image;
        this.ShortDescription=shortDescription;
    }

    public Advertisement() {
    }

    public Advertisement(String identifier, String location, String longDescription, String shortDescription,
                         String phoneNumber, boolean reported, String title, String image, String uploader,
                         String uploaderPhoneNumber) {
        Identifier = identifier;
        Location = location;
        LongDescription = longDescription;
        ShortDescription = shortDescription;
        PhoneNumber = phoneNumber;
        Reported = reported;
        Title = title;
        Image = image;
        Uploader = uploader;
        UploaderPhoneNumber = uploaderPhoneNumber;
    }

    public Advertisement(String identifier, String location, String longDescription, String shortDescription, String phoneNumber, boolean reported, String title, String image, String uploader) {
        Identifier = identifier;
        Location = location;
        LongDescription = longDescription;
        ShortDescription = shortDescription;
        PhoneNumber = phoneNumber;
        Reported = reported;
        Title = title;
        Image = image;
        Uploader = uploader;
    }


    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
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

    public Advertisement(String title, String image) {
        Title = title;
        Image = image;
    }

    public Advertisement(String identifier, String location, String longDescription, String shortDescription, String phoneNumber, boolean reported, String title, String image, String uploader, String uploaderPhoneNumber, int counter) {
        Identifier = identifier;
        Location = location;
        LongDescription = longDescription;
        ShortDescription = shortDescription;
        PhoneNumber = phoneNumber;
        Reported = reported;
        Title = title;
        Image = image;
        Uploader = uploader;
        UploaderPhoneNumber = uploaderPhoneNumber;
        this.counter = counter;
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

    public String getUploader() {
        return Uploader;
    }

    public void setUploader(String uploader) {
        Uploader = uploader;
    }

    public String getUploaderPhoneNumber() {
        return UploaderPhoneNumber;
    }

    public void setUploaderPhoneNumber(String uploaderPhoneNumber) {
        UploaderPhoneNumber = uploaderPhoneNumber;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "Identifier='" + Identifier + '\'' +
                ", Location='" + Location + '\'' +
                ", LongDescription='" + LongDescription + '\'' +
                ", ShortDescription='" + ShortDescription + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Reported=" + Reported +
                ", Title='" + Title + '\'' +
                ", Image='" + Image + '\'' +
                ", Uploader='" + Uploader + '\'' +
                ", Counter='" + counter + '\'' +
                '}';
    }
}
