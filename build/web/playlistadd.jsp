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
    String message = request.getParameter("message");
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
            <div class="card">
                <div class="card-body">
                    <h2 class="card-title">Add Playlist</h2>
                    <form action="savePlaylist" method="post">
                        <div class="form-group">
                            <label for="playlistName">Playlist Name:</label>
                            <input type="text" class="form-control" id="playlistName" name="playlistName" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Save Playlist</button>
                        <% if (message != null && !message.isEmpty()) { %>
                            <div class="alert alert-primary mt-3" role="alert">
                                <%= message %>
                            </div>
                        <% } %>
                    </form>
                </div>
            </div>
        </div>


        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </body>
</html>
