package com.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Udara
 */
public class Artist {
    private int id;
    private String name;
    private String country;
    private String image;
    private String dob;

    public Artist(int id, String name, String country, String image, String dob) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.image = image;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getImage() {
        return image;
    }

    public String getDob() {
        return dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    
    

}
