/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.models.Artist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Udara
 */
public class ArtistDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/music_project";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private Connection jdbcConnection;

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public List<Artist> getAllArtists() throws SQLException {

        String sql = "SELECT * FROM artist";
        List<Artist> artists = new ArrayList<>();
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("artist_id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                String image = rs.getString("image");
                String dob = rs.getString("dob");

                Artist artist = new Artist(id, name, country, image, dob);
                artists.add(artist);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return artists;
    }
    
    public String getArtistNameById(int artistId) throws SQLException {
        String sql = "SELECT name FROM artist WHERE artist_id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, artistId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } finally {
            disconnect();
        }

        return null; 
    }
}
