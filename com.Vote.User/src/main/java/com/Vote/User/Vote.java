package com.Vote.User;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/vote")
public class Vote extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vote = request.getParameter("radiobutton");
        String email = request.getParameter("email");
        System.out.println(vote);
        System.out.println(email);

        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtInsert = null;
        ResultSet rs = null;

        String checkEmailQuery = "SELECT * FROM voterecord WHERE email = ?";
        String insertVoteQuery = "INSERT INTO voterecord (Name, email) VALUES (?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vote?user=root&password=root123@");

            // Check if the email already exists in the voterecord table
            pstmtCheck = conn.prepareStatement(checkEmailQuery);
            pstmtCheck.setString(1, email);
            rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                // Email already exists, do not insert again
				 System.out.println("Email already exists. Cannot insert again."); 
				/*
				 * PrintWriter out = response.getWriter(); out.println("<html><body>");
				 * out.println("<h3>Email already exists. Cannot insert again.</h3>");
				 * out.println("</body></html>");
				 */
            } else {
                // Email does not exist, proceed with insertion
                pstmtInsert = conn.prepareStatement(insertVoteQuery);
                pstmtInsert.setString(1, vote);
                pstmtInsert.setString(2, email);

                int row = pstmtInsert.executeUpdate();

                if (row == 1) {
                    System.out.println("Insert successful");
                    PrintWriter out = response.getWriter();
                	out.println("<html><body>");
                	out.println("<h3>Insert successful.</h3>");
                	out.println("</body></html>");
                } else {
                    System.out.println("Failed to insert data");
                    PrintWriter out = response.getWriter();
					/* out.println("<html>Failed to insert data<body>"); */
                	out.println("<h3>Failed to insert data.</h3>");
                	out.println("</body></html>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmtCheck != null) {
                    pstmtCheck.close();
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
