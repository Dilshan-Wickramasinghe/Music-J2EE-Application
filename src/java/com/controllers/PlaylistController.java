/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.dao.PlaylistDAO;
import com.dao.SongDAO;
import com.models.Playlist;
import com.models.Song;
import com.models.User;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Udara
 */
@WebServlet(name = "playlists", urlPatterns = {"/playlists/*"})
public class PlaylistController extends HttpServlet {

    private PlaylistDAO playlistDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        playlistDAO = new PlaylistDAO(); 
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            if (pathInfo.equals("/add")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/playlistadd.jsp");
                dispatcher.forward(request, response);
                return;
            } else if (pathInfo.startsWith("/songs/remove/")) {
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length == 5 ) {
                    int playlistId = Integer.parseInt(pathParts[3]);
                    int songId = Integer.parseInt(pathParts[4]);
                    try {
                        playlistDAO.removeSongFromPlaylist(playlistId, songId);
                        response.sendRedirect(request.getContextPath() + "/playlists/songs/"+playlistId);
                        
                        return;
                    } catch (SQLException ex) {
                        Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    return;
                }
            } else if (pathInfo.startsWith("/songs/")) {
                String[] pathParts = pathInfo.split("/");
                System.out.println(pathParts.length + " =============== " + (pathParts.length == 5 && pathParts[2].equals("attact")));
                
                if (pathParts.length == 5 && pathParts[2].equals("attact")) {
                    try {
                        int playlistId = Integer.parseInt(pathParts[3]);
                        int songId = Integer.parseInt(pathParts[4]);
                        playlistDAO.addSongToPlaylist(playlistId, songId);
                        response.sendRedirect(request.getContextPath() + "/playlists/songs/"+playlistId);
                          
                        return;
                    } catch (SQLException ex) {
                        Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (pathParts.length == 3) {
                    try {
                        int playlistId = Integer.parseInt(pathParts[2]);
                        List<Song> songs = playlistDAO.getSongsByPlaylistId(playlistId);
                        String playlistName = playlistDAO.getPlaylistNameById(playlistId);
                        request.setAttribute("songs", songs);
                        request.setAttribute("playlistName", playlistName);
                        request.setAttribute("playlistID", playlistId);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/playlist_songs.jsp");
                        dispatcher.forward(request, response);
                        System.out.println(pathParts.length + " mmmmmmmmmmmmmm ");
                        return;
                    } catch (SQLException ex) {
                        Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (pathParts.length == 4 && pathParts[2].equals("add")) {
                    try {
                        int playlistId = Integer.parseInt(pathParts[3]);
                        SongDAO songDAO = new SongDAO();
                        List<Song> songs = songDAO.getAllSongsNotInPlaylist(playlistId);
                        request.setAttribute("songs", songs);
                        request.setAttribute("playlistID", playlistId);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/playlist_songs_add.jsp");
                        dispatcher.forward(request, response);
                        System.out.println(pathParts.length + " xxxxxxxxxxxx ");
                        return;
                    } catch (SQLException ex) {
                        Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                   System.out.println(pathParts.length + " yyyyyyyyyyy ");
                   return;
                }
            }else if(pathInfo.startsWith("/delete/")){
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length == 3) {
                    int playlistId = Integer.parseInt(pathParts[2]);
                    try {
                        playlistDAO.deletePlaylist(playlistId);
                        response.sendRedirect(request.getContextPath() + "/playlists");
                        return;
                    } catch (SQLException ex) {
                        Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        
        HttpSession session = request.getSession();
        int uid = (int) session.getAttribute("UID");
        try {
            List<Playlist> plays = playlistDAO.getPlaylistsByUserId(uid);
            request.setAttribute("plays", plays);
            request.getRequestDispatcher("playlists.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            String playlistName = request.getParameter("playlistName");
            System.out.println("aaaaaaaaaaaaaaaaaa" + playlistName);
            PlaylistDAO playlistDAO = new PlaylistDAO();
            
            if (playlistDAO.playlistExists(playlistName)) {
                response.sendRedirect(request.getContextPath() + "/playlists/add?message=Playlist+Already+Exists");
                return;
            }
            HttpSession session = request.getSession();
            int uid = (int) session.getAttribute("UID");

            Playlist playlist = new Playlist();
            playlist.setName(playlistName);
            playlist.setUserId(uid);
            playlistDAO.addPlaylist(playlist);

            response.sendRedirect(request.getContextPath() + "/playlists/add?message=Playlist+added+successfully");
           
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
