<%-- 
    Document   : navigation
    Created on : Jun 10, 2024, 1:20:18 AM
    Author     : Udara
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.models.Song"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%
    if (session == null || session.getAttribute("UN") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Song> songs = (List<Song>) request.getAttribute("songs");
    int size = (songs != null) ? songs.size() : 0;
%>

