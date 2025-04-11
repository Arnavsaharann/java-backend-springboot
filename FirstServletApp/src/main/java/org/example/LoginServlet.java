package org.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "Password@123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head><title>Login Page</title></head>" +
                        "<body>" +
                        "<h1>Login</h1>" +
                        "<form action='login' method='post'>" +
                        "<label for='name'>Name:</label>" +
                        "<input type='text' id='name' name='name' placeholder='Enter your name' required><br><br>" +
                        "<label for='username'>Username:</label>" +
                        "<input type='text' id='username' name='username' placeholder='Enter your username' required><br><br>" +
                        "<label for='password'>Password:</label>" +
                        "<input type='password' id='password' name='password' placeholder='Enter your password' required><br><br>" +
                        "<input type='submit' value='Login'>" +
                        "</form>" +
                        "</body>" +
                        "</html>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/html");

        if (!isValidName(name)) {
            resp.getWriter().write(
                    "<h1>Error: Invalid Name!</h1>" +
                            "<p>Name must start with a capital letter and have at least 3 characters.</p>" +
                            "<a href='login'>Back to Login</a>");
            return;
        }

        if (!isValidPassword(password)) {
            resp.getWriter().write(
                    "<h1>Error: Invalid Password!</h1>" +
                            "<p>Password must meet the following criteria:</p>" +
                            "<ul>" +
                            "<li>Minimum 8 characters</li>" +
                            "<li>At least 1 uppercase letter</li>" +
                            "<li>At least 1 numeric character</li>" +
                            "<li>Exactly 1 special character</li>" +
                            "</ul>" +
                            "<a href='login'>Back to Login</a>");
            return;
        }

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            resp.getWriter().write(
                    "<h1>Login Successful!</h1>" +
                            "<p>Welcome, " + name + "!</p>" +
                            "<a href='login'>Back to Login</a>");
        } else {
            resp.getWriter().write(
                    "<h1>Login Failed!</h1>" +
                            "<p>Invalid username or password.</p>" +
                            "<a href='login'>Back to Login</a>");
        }
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("[A-Z][a-zA-Z]{2,}");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$");
    }
}