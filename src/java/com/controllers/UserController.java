/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.dao.UserDAO;
import com.models.User;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Udara
 */
@WebServlet(name = "register", urlPatterns = {"/register/*"})
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                response.sendRedirect("register.jsp?error=Please fill all fields");
                return;
            }

            UserDAO userDAO = new UserDAO();

            if (userDAO.isUserExists(username, email)) {
                request.setAttribute("message", "exists");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            String hashedPassword = PasswordHashing.hashPassword(password);

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(hashedPassword);

            boolean isRegistered = userDAO.registerUser(user);

            if (isRegistered) {
                request.setAttribute("message", "success");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "failed");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

}
