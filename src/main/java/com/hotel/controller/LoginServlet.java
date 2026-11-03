package com.hotel.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hotel.util.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null
                || username.trim().isEmpty()
                || password.isEmpty()) {

            req.setAttribute("error", "Enter username and password.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        try {
            String passwordHash = hashPassword(password);

            String sql = "SELECT USER_ID, USERNAME, ROLE "
                       + "FROM USERS "
                       + "WHERE USERNAME = ? AND PASSWORD_HASH = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username.trim());
                ps.setString(2, passwordHash);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        HttpSession session = req.getSession(true);

                        session.setAttribute("userId", rs.getInt("USER_ID"));
                        session.setAttribute("username", rs.getString("USERNAME"));
                        session.setAttribute("role", rs.getString("ROLE"));

                        resp.sendRedirect(
                            req.getContextPath() + "/dashboard.jsp");
                    } else {
                        req.setAttribute("error",
                                "Invalid username or password.");
                        req.getRequestDispatcher("/login.jsp")
                           .forward(req, resp);
                    }
                }
            }

        } catch (Exception e) {
            throw new ServletException("Login failed. Check database connection and USERS table.", e);
        }
    }

    private String hashPassword(String password)
            throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            hex.append(String.format("%02x", b & 0xff));
        }

        return hex.toString();
    }
}