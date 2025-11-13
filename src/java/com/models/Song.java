/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author Udara
 */
public class Song {

    private int id;
    private String title;
    private String name;
    private String image;
    private int artistId;
    private int albumId;
    private String duration;
    
    private String artist;
    private String album;

    public Song(int id, String title, String name, String image, int artistId, int albumId, String duration, String artist, String album) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.image = image;
        this.artistId = artistId;
        this.albumId = albumId;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
    
    
}
