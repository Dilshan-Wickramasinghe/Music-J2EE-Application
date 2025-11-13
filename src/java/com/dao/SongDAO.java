package com.dao;

import com.controllers.PasswordHashing;
import com.models.Song;
import com.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

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

    public List<Song> getAllSongs() throws SQLException {

        String sql = "SELECT s.* , a.title as atitle , ar.name as aname  FROM song as s , album as a , artist as ar WHERE s.artist_id  = ar.artist_id and s.album_id = a.album_id";
        List<Song> songs = new ArrayList<>();
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int artistId = rs.getInt("artist_id");
                int albumId = rs.getInt("album_id");
                String duration = rs.getString("duration");
                String album = rs.getString("atitle");
                String artist = rs.getString("aname");

                Song song = new Song(id, title, name, image, artistId, albumId, duration, artist, album);
                System.out.println("title" + title);
                songs.add(song);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return songs;
    }

    public List<Song> getSongsByArtist(int artistId) throws SQLException {
        String sql = "SELECT s.* , a.title as atitle , ar.name as aname  FROM song as s , album as a , artist as ar WHERE s.artist_id  = ar.artist_id and s.album_id = a.album_id and s.artist_id = ?";
        List<Song> songs = new ArrayList<>();
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, artistId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int albumId = rs.getInt("album_id");
                String duration = rs.getString("duration");
                String album = rs.getString("atitle");
                String artist = rs.getString("aname");

                Song song = new Song(id, title, name, image, artistId, albumId, duration, artist, album);
                songs.add(song);
            }
        } finally {
            disconnect();
        }

        return songs;
    }

    public List<Song> getSongsByAlbum(int artistId) throws SQLException {
        String sql = "SELECT s.* , a.title as atitle , ar.name as aname  FROM song as s , album as a , artist as ar WHERE s.artist_id  = ar.artist_id and s.album_id = a.album_id and s.album_id = ?";
        List<Song> songs = new ArrayList<>();
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, artistId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int albumId = rs.getInt("album_id");
                String duration = rs.getString("duration");
                String album = rs.getString("atitle");
                String artist = rs.getString("aname");

                Song song = new Song(id, title, name, image, artistId, albumId, duration, artist, album);
                songs.add(song);
            }
        } finally {
            disconnect();
        }

        return songs;
    }

    public Song getSongById(int id) throws SQLException {
        String sql = "SELECT s.* , a.title as atitle , ar.name as aname  FROM song as s , album as a , artist as ar WHERE s.artist_id  = ar.artist_id and s.album_id = a.album_id and s.song_id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                int artistId = resultSet.getInt("artist_id");
                int albumId = resultSet.getInt("album_id");
                String name = resultSet.getString("name");
                String duration = resultSet.getString("duration");
                String image = resultSet.getString("image");
                String album = resultSet.getString("atitle");
                String artist = resultSet.getString("aname");

                Song song = new Song(id, title, name, image, artistId, albumId, duration, artist, album);
                return song;
            }
        } finally {
            disconnect();
        }

        return null;
    }

    public List<Song> getAllSongsNotInPlaylist(int playlistId) throws SQLException {
        String sql = "SELECT s.* , a.title as atitle , ar.name as aname  FROM song as s "
                + "JOIN album as a ON s.album_id = a.album_id "
                + "JOIN artist as ar ON s.artist_id = ar.artist_id "
                + "LEFT JOIN playlist_song as ps ON s.song_id = ps.song_id AND ps.playlist_id = ? "
                + "WHERE ps.song_id IS NULL";

        List<Song> songs = new ArrayList<>();
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int artistId = rs.getInt("artist_id");
                int albumId = rs.getInt("album_id");
                String duration = rs.getString("duration");
                String album = rs.getString("atitle");
                String artist = rs.getString("aname");

                Song song = new Song(id, title, name, image, artistId, albumId, duration, artist, album);
                System.out.println("title" + title);
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return songs;
    }
}
