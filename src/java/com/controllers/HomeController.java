package com.controllers;
import com.dao.PlaylistDAO;
import com.models.Song;
import com.dao.SongDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Udara
 */

@WebServlet("/home")
public class HomeController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SongDAO songDAO = new SongDAO();
            List<Song> songs = songDAO.getAllSongs();
            
            PlaylistDAO playlistDAO = new PlaylistDAO();
            Map<Integer, Integer> trendingSongs = playlistDAO.getTrendingAudioIds();
            request.setAttribute("songs", songs);
            request.setAttribute("trendingSongs", trendingSongs);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
