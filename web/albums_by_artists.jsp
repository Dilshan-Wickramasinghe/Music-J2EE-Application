<%@page import="com.models.Song"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.models.Album"%>
<% 
    List<Song> songs = (List<Song>) request.getAttribute("albumslist");
    int size = (songs != null) ? songs.size() : 0;
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
                    <li class="nav-item active">
                        <a class="nav-link" href="/MusicJ2EE/albums">Albums</a>
                    </li>
                    <li class="nav-item">
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
            <h4><%= request.getAttribute("albumName") %></h4>
        </div>
        <div class="container">

            <h2 class="my-4">All Songs (<%= size%>)</h2>
            <div class="form-group d-flex justify-content-end">
                <button id="stoppBtn" class="btn btn-danger" style="margin-right: 20px">Stop Audio</button>
                <button id="skipBtn" class="btn btn-success" style="margin-right: 20px">Skip</button>
                <button id="playAllBtn" class="btn btn-success" style="margin-right: 20px">Play All</button>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th></th>
                        <th>Title</th>
                        <th>Duration</th>
                        <th>Album</th>
                        <th></th>
                    </tr>
                </thead>
                 <tbody>
                    <% for (Song song : songs) {%>
                    <tr>
                        <td><img src="<%= song.getImage()%>" class="card-img-top" alt="Song Image"></td>
                        <td><a href="/MusicJ2EE/singlesong/<%= song.getId()%>"><%= song.getTitle()%></a></td>
                        <td><%= song.getDuration()%></td>
                        <td><%= song.getAlbum()%></td>
                        <td>
                            <audio id="audio_<%= song.getId()%>">
                                <source src="/MusicJ2EE/Songs/<%= song.getName()%>.mp3" type="audio/mp3">
                                Your browser does not support the audio element.
                            </audio>
                            <a href="#" onclick="toggleAudio(this, '<%= song.getId()%>')" class="atag">
                                <i id="playIcon_<%= song.getId()%>" class="fas fa-play"></i>
                                <i id="stopIcon_<%= song.getId()%>" class="fas fa-stop" style="display: none;"></i>
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

        <script>
            
            var currentAudio = 0;
            var arr = [<%
                if (songs != null) {
                    for (int i = 0; i < songs.size(); i++) {
                        out.print(songs.get(i).getId());
                        if (i < songs.size() - 1) {
                            out.print(",");
                        }
                    }
                }
            %>];
                    
            function toggleAudio(button, songId) {
                var audio = document.getElementById('audio_' + songId);
                var playIcon = document.getElementById('playIcon_' + songId);
                var stopIcon = document.getElementById('stopIcon_' + songId);

                // Pause all audios
                var allAudios = document.getElementsByTagName('audio');
                for (var i = 0; i < allAudios.length; i++) {
                    if (allAudios[i] !== audio) {
                        allAudios[i].pause();
                        var audioId = allAudios[i].id.split('_')[1];
                        document.getElementById('playIcon_' + audioId).style.display = 'inline';
                        document.getElementById('stopIcon_' + audioId).style.display = 'none';
                    }
                }

                if (audio.paused) {
                    audio.currentTime = 0;
                    audio.play();
                    playIcon.style.display = 'none';
                    stopIcon.style.display = 'inline';
                } else {
                    audio.pause();
                    audio.currentTime = 0;
                    playIcon.style.display = 'inline';
                    stopIcon.style.display = 'none';
                }
            }
            
            function playAllAudios(songIds, currentIndex = 0) {
                if (currentIndex >= songIds.length) {
                    return; // Stop if all songs have been played
                }

                var audio = document.getElementById('audio_' + songIds[currentIndex]);
                var playIcon = document.getElementById('playIcon_' + songIds[currentIndex]);
                var stopIcon = document.getElementById('stopIcon_' + songIds[currentIndex]);
                
                audio.currentTime = 0;
                audio.play();
                playIcon.style.display = 'none';
                stopIcon.style.display = 'inline';

                audio.onended = function() {
                    audio.pause();
                    audio.currentTime = 0;

                    playIcon.style.display = 'inline';
                    stopIcon.style.display = 'none';

                    playAllAudios(songIds, currentIndex + 1);
                };
            }
            
            document.getElementById('playAllBtn').addEventListener('click', function() {
                console.log(arr);
                playAllAudios(arr);
            });
            
            document.getElementById('skipBtn').addEventListener('click', function() {
                var allAudios = document.getElementsByTagName('audio');
                for (var i = 0; i < allAudios.length; i++) {
                    if (!allAudios[i].paused) {
                        allAudios[i].pause();
                        var audioId = allAudios[i].id.split('_')[1];
                        var playIcon = document.getElementById('playIcon_' + arr[i]);
                        var stopIcon = document.getElementById('stopIcon_' + arr[i]);
                        
                        playIcon.style.display = 'inline';
                        stopIcon.style.display = 'none';
                        
                        if(currentAudio < allAudios.length-1){
                            currentAudio++;
                        }else{
                            currentAudio = 0;
                        }
                        console.log(arr , audioId  , currentAudio);
                        playAllAudios(arr, currentAudio);
                        break;
                    }
                }
            });
            
            document.getElementById('stoppBtn').addEventListener('click', function() {
                location.reload();
            });
        </script>
    </body>
</html>
