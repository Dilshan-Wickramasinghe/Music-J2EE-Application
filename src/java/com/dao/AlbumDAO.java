/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;
import com.models.Album;
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
public class AlbumDAO {
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

    public List<Album> getAllAlbums() throws SQLException {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM album";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("album_id");
                String title = resultSet.getString("title");
                int artistId = resultSet.getInt("artist_id");
                String releaseDate = resultSet.getString("release_date");
                String image = resultSet.getString("image");

                Album album = new Album(id, title, artistId, releaseDate, image);
                albums.add(album);
            }
        } finally {
            disconnect();
        }

        return albums;
    }
    
    public List<Album> getAlbumsByArtistId(int artistId) throws SQLException {
        String sql = "SELECT * FROM album WHERE artist_id = ?";
        List<Album> albums = new ArrayList<>();
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, artistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("album_id");
                String title = resultSet.getString("title");
                String releaseDate = resultSet.getString("release_date");
                String image = resultSet.getString("image");
                
                Album album = new Album(id, title, artistId, releaseDate,image);
                albums.add(album);
            }
        } finally {
            disconnect();
        }

        return albums;
    }
    
    public String getAlbumNameById(int albumId) throws SQLException {
        String sql = "SELECT title FROM album WHERE album_id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, albumId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("title");
            }
        } finally {
            disconnect();
        }

        return null; 
    }
}
