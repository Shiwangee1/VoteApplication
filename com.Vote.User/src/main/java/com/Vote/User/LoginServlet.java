package com.Vote.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve user data from the login form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Database connection variables
        Connection conn = null;
        PreparedStatement pstmtInsert = null;
        ResultSet rs = null;

        String insertUserQuery = "INSERT INTO uservote (name, email, password) VALUES (?, ?, ?)";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vote", "root", "root123@");

            // Move session creation here
            HttpSession session = request.getSession();
            session.setAttribute("email", email);

            // Proceed with user registration
            pstmtInsert = conn.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtInsert.setString(1, name);
            pstmtInsert.setString(2, email);
            pstmtInsert.setString(3, password);

            // Execute the update query to insert user data
            int rowsAffected = pstmtInsert.executeUpdate();

            if (rowsAffected > 0) {
                // Registration successful

                // Retrieve the generated ID
                rs = pstmtInsert.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    // You can use the generatedId as needed (e.g., store it in session, pass it to another page)
                    session.setAttribute("userId", generatedId);

                    // Redirect to Vote.jsp
                    response.sendRedirect("Vote.jsp");
                } else {
                    // Something went wrong with ID retrieval
                    response.getWriter().println("Failed to retrieve user ID after registration!");
                }
            } else {
                // Registration failed
                response.getWriter().println("Failed to register user!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources in the finally block
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmtInsert != null) {
                    pstmtInsert.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
