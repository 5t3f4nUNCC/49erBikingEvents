package edu.uncc.Events;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Stefan Dybka
 */
public class Event implements Serializable {
    private String eventID;
    private String title;
    private String type;
    private String message;
    private String description;
    private LocalDateTime date;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private int countGoing;
    private String userID;

    public Event() {
        eventID = "";
        title = "";
        type = "";
        message = "";
        description = "";
        date = LocalDateTime.now();
        address = "";
        city = "";
        state = "";
        country = "";
        zip = "";
        countGoing = 0;
        userID = "";
    }

    //Constructor if just a location is entered (No city, state, country, zip needed in this case)
    public Event(String eventID, String title, String type, String message, String description, LocalDateTime date, String address, int countGoing, String userID) {
        this.eventID = eventID;
        this.title = title;
        this.type = type;
        this.message = message;
        this.description = description;
        this.date = date;
        this.address = address;
        this.city = "";
        this.state = "";
        this.country = "";
        this.zip = "";
        this.countGoing = countGoing;
        this.userID = userID;
    }
    
    //Constructor if full address information is needed
    public Event(String eventID, String title, String type, String message, String description, LocalDateTime date, String address, String city, String state, String country, String zip, int countGoing, String userID) {
        this.eventID = eventID;
        this.title = title;
        this.type = type;
        this.message = message;
        this.description = description;
        this.date = date;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.countGoing = countGoing;
        this.userID = userID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getCountGoing() {
        return countGoing;
    }

    public void setCountGoing(int countGoing) {
        this.countGoing = countGoing;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
