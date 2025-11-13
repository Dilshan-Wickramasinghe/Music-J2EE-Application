/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.dao.AlbumDAO;
import com.dao.ArtistDAO;
import com.models.Song;
import com.dao.SongDAO;
import com.models.Album;
import com.models.Artist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Udara
 */
@WebServlet(name = "artists", urlPatterns = {"/artists/*"})
public class ArtistController extends HttpServlet {

    private ArtistDAO artistDAO;
    private AlbumDAO albumDAO;
    private SongDAO songDAO;

    @Override
    public void init() throws ServletException {
        artistDAO = new ArtistDAO();
        albumDAO = new AlbumDAO();
        songDAO = new SongDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            displayAllArtists(request, response);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 3) {
                int id = Integer.parseInt(pathParts[2]);
                if (pathParts[1].equals("albums")) {
                    displayArtistAlbums(id , request, response);
                } else if (pathParts[1].equals("songs")) {
                    displayArtistSongs(id , request, response);
                }
            }
        }
    }

    private void displayAllArtists(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Artist> artists = artistDAO.getAllArtists();
            request.setAttribute("artistslist", artists);
            request.getRequestDispatcher("/artists.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayArtistAlbums(int albumId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            SongDAO songDAO = new SongDAO();
            List<Song> albums = songDAO.getSongsByAlbum(albumId);
            String albumName = albumDAO.getAlbumNameById(albumId);
            request.setAttribute("albumslist", albums);
            request.setAttribute("albumName", albumName);
            request.getRequestDispatcher("/albums_by_artists.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayArtistSongs(int artistId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Song> songs = songDAO.getSongsByArtist(artistId);
            String artistName = artistDAO.getArtistNameById(artistId);
            
            request.setAttribute("songslist", songs);
            request.setAttribute("artistName", artistName);
            
            request.getRequestDispatcher("/songs_by_artists.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
