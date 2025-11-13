/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;
import com.dao.UserDAO;
import com.models.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Udara
 */

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            if (email.isEmpty() || password.isEmpty()) {
                request.setAttribute("message", "empty");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            
            UserDAO userDAO = new UserDAO();
            User user = userDAO.validateUser(email, password);
            
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("UN", email);
                session.setAttribute("UID", user.getId());
                response.sendRedirect("/MusicJ2EE/home");
            } else {
                request.setAttribute("message", "faild");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
