/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;
import com.dao.AlbumDAO;
import com.dao.SongDAO;
import com.models.Album;
import com.models.Artist;
import com.models.Song;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Udara
 */
@WebServlet(name = "albums", urlPatterns = {"/albums/*"})
public class AlbumsController extends HttpServlet {
    private AlbumDAO albumDAO;

    public void init() {
        albumDAO = new AlbumDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            displayAllAlbums(request, response);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 3) {
                int albumId  = Integer.parseInt(pathParts[2]);
                if(pathParts[1].equals("songs")) {
                    displayAlbumSongs(albumId, request, response);
                }
            }
        }
    }
    
    private void displayAllAlbums(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Album> albums = albumDAO.getAllAlbums();
            request.setAttribute("albums", albums);
            RequestDispatcher dispatcher = request.getRequestDispatcher("albums.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void displayAlbumSongs(int albumId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    
    
   
}
