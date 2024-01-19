<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.SQLException"%>

<%! 
    // Declare your Java method here
    private boolean isEmailExists(String email) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/vote";
            String username = "root";
            String password = "root123@";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL query
            String query = "SELECT COUNT(*) AS count FROM voterecord WHERE email=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0; // Return true if count is greater than 0 (email exists)
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Return false if there was an error or the email doesn't exist
        return false;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Vote Page</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card paint-card">
                    <div class="card-body">

                        <%
                        String email = (String) session.getAttribute("email");

                        // Check if the email is already registered in your database
                        boolean emailExists = isEmailExists(email);
                        %>

                        <form action="vote" method="Post">
                            <div class="mb-2">
                                <label class="form-label">Choose your candidate:</label>
                                <div class="form-check">
                                    <input type="hidden" name="email" value="<%=email%>">
                                    <input type="radio" class="form-check-input" id="candidate1" name="radiobutton" value="Ashish Pal">
                                    <label class="form-check-label" for="candidate1">Ashish Pal</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="candidate2" name="radiobutton" value="Sumit Goud">
                                    <label class="form-check-label" for="candidate2">Sumit Goud</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="candidate3" name="radiobutton" value="Vinayak More">
                                    <label class="form-check-label" for="candidate3">Vinayak More</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="candidate4" name="radiobutton" value="Shiwangee Singh">
                                    <label class="form-check-label" for="candidate4">Shiwangee Singh</label>
                                </div>
                            </div>

                            <% if (emailExists) { %>
                                <div class="alert alert-danger" role="alert">
                                    Email already exists. Cannot insert again.
                                </div>
                            <% } %>

                            <input type="submit" class="btn btn-primary d-block mx-auto">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
