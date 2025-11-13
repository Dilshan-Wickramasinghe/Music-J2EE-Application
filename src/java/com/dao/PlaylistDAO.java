package com.dao;

import com.models.Playlist;
import com.models.Song;
import com.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistDAO {
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

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public void addPlaylist(Playlist playlist) throws SQLException {
        String sql = "INSERT INTO playlist (name,user_id ) VALUES (?,?)";
        connect();
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getUserId());
            statement.executeUpdate();
        } finally {
            disconnect();
        }
    }
    
    public boolean playlistExists(String playlistName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM playlist WHERE name = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, playlistName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; 
            }
        } finally {
            disconnect();
        }

        return false; 
    }
    
    public List<Playlist> getAllPlaylist() throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM album";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("playlist_id");
                String name = resultSet.getString("name");
                int userId  = resultSet.getInt("user_id");

                Playlist play = new Playlist(id, name, userId);
                playlists.add(play);
            }
        } finally {
            disconnect();
        }

        return playlists;
    }
    
    public List<Playlist> getPlaylistsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM playlist WHERE user_id = ?";
         List<Playlist> playlists = new ArrayList<>();
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("playlist_id");
                String name = rs.getString("name");

                Playlist play = new Playlist(id, name, 8);
                playlists.add(play);
            }
        } finally {
            disconnect();
        }

        return playlists;
    }
   
    public List<Song> getSongsByPlaylistId(int playlistId) throws SQLException {
        String sql = "SELECT s.*, a.title AS atitle, ar.name AS aname FROM song s JOIN playlist_song ps ON s.song_id = ps.song_id JOIN album a ON s.album_id = a.album_id JOIN artist ar ON s.artist_id = ar.artist_id  WHERE ps.playlist_id = ?";
        
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
                songs.add(song);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return songs;
    }
    
    public String getPlaylistNameById(int playlistId) throws SQLException {
        String sql = "SELECT name FROM playlist WHERE playlist_id  = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } finally {
            disconnect();
        }

        return null; 
    }

    public void removeSongFromPlaylist(int playlistId, int songId) throws SQLException {
        String sql = "DELETE FROM playlist_song WHERE playlist_id = ? AND song_id = ?";
        connect();
        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, songId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
    
    public void addSongToPlaylist(int playlistId, int songId) throws SQLException {
        String sql = "INSERT INTO playlist_song (playlist_id, song_id) VALUES (?, ?)";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, playlistId);
            statement.setInt(2, songId);
            statement.executeUpdate();
        } finally {
            disconnect();
        }
    }
    
    public void deletePlaylist(int playlistId) throws SQLException {
        String deletePlaylistSql = "DELETE FROM playlist WHERE playlist_id = ?";
        String deletePlaylistSongsSql = "DELETE FROM playlist_song WHERE playlist_id = ?";
        connect();

        try (PreparedStatement deletePlaylistStatement = jdbcConnection.prepareStatement(deletePlaylistSql);
             PreparedStatement deletePlaylistSongsStatement = jdbcConnection.prepareStatement(deletePlaylistSongsSql)) {

            deletePlaylistSongsStatement.setInt(1, playlistId);
            deletePlaylistSongsStatement.executeUpdate();

            deletePlaylistStatement.setInt(1, playlistId);
            deletePlaylistStatement.executeUpdate();
        } finally {
            disconnect();
        }
    }
    
    public Map<Integer, Integer> getTrendingAudioIds() throws SQLException {
        Map<Integer, Integer> trendingAudioIds = new HashMap<>();
        String sql = "SELECT song_id, COUNT(DISTINCT playlist_id) AS playlist_count FROM playlist_song GROUP BY song_id ORDER BY playlist_count DESC LIMIT 2";
        
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int songId = resultSet.getInt("song_id");
                int playlistCount = resultSet.getInt("playlist_count");
                trendingAudioIds.put(songId, playlistCount);
            }
        }

        return trendingAudioIds;
    }
}
