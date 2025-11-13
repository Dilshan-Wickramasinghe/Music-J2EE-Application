<%-- 
    Document   : welcome
    Created on : Jun 7, 2024, 12:10:00 PM
    Author     : Udara
--%>

<%@page import="com.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.models.Song"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%
    if (session == null || session.getAttribute("UN") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Song> songs = (List<Song>) request.getAttribute("songs");
    int size = (songs != null) ? songs.size() : 0;
    int playlistID = (int) request.getAttribute("playlistID");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Music</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />

        <style>
            body {
                background-color: #f8f9fa;
            }
            .song-card {
                margin-bottom: 20px;
            }
            .card-img-top {
                height: 40px;
                width:  40px;
                object-fit: cover;
                border-radius: 5px
            }
            .card-body {
                background-color: #fff;
                border-radius: 0 0 5px 5px;
            }
            .atag{
                text-decoration: none !important;
            }
            .banner {
                background-color: #343a40;
                color: #fff;
                padding: 10px;
                text-align: center;
                margin-bottom: 20px;
                height: 200px;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            .nav-item{
                margin-left: 20px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/MusicJ2EE">Music</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto"> <!-- Changed class to ml-auto -->
                    <li class="nav-item">
                        <a class="nav-link" href="/MusicJ2EE/home">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/MusicJ2EE/artists">Artists</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/MusicJ2EE/albums">Albums</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/MusicJ2EE/playlists">Playlists</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-danger"  href="/MusicJ2EE/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="banner">
            <h1>Welcome to Your Music App</h1>
            <p>Your personalized music experience</p>
        </div>
        <div class="container">
            <div class="row mb-3">
                <div class="col">
                    <input type="text" class="form-control" id="filterInput" placeholder="Search songs">
                </div>
            </div>
            <h2 class="my-4">All Songs (<%= size%>)</h2>
            <table class="table" id="playlistTable">
                <thead>
                    <tr>
                        <th></th>
                        <th>Title</th>
                        <th>Duration</th>
                        <th>Artist</th>
                        <th>Album</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Song song : songs) {%>
                    <tr>
                        <td><img src="<%= song.getImage()%>" class="card-img-top" alt="Song Image"></td>
                        <td><a href="singlesong/<%= song.getId()%>"><%= song.getTitle()%></a></td>
                        <td><%= song.getDuration()%></td>
                        <td><a href="artists/songs/<%= song.getArtistId()%>"><%= song.getArtist()%></a></td>
                        <td><a href="artists/albums/<%= song.getAlbumId()%>"><%= song.getAlbum()%></a></td>
                        <td>
                            <audio id="audio_<%= song.getId()%>">
                                <source src="Songs/<%= song.getName()%>.mp3" type="audio/mp3">
                                Your browser does not support the audio element.
                            </audio>
                            <a href="/MusicJ2EE/playlists/songs/attact/<%= playlistID %>/<%= song.getId()%>" class="atag">
                               <i class="fa-solid fa-folder-plus"></i>
                            </a>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        
        <script>
            $(document).ready(function() {
                $('#filterInput').on('keyup', function() {
                    var searchText = $(this).val().toLowerCase();
                    $('#playlistTable tbody tr').each(function() {
                        var title = $(this).find('td:eq(1)').text().toLowerCase(); // Index 0 for title column
                        var artist = $(this).find('td:eq(3)').text().toLowerCase(); // Index 1 for artist column
                        var album = $(this).find('td:eq(4)').text().toLowerCase(); // Index 2 for album column
                        // Add other columns as needed

                        if (title.indexOf(searchText) === -1 && 
                            artist.indexOf(searchText) === -1 && 
                            album.indexOf(searchText) === -1) {
                            $(this).hide(); // Hide rows that don't match search text
                        } else {
                            $(this).show(); // Show rows that match search text
                        }
                    });
                });
            });
        </script>
    </body>
</html>
