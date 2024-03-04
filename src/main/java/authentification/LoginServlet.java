package authentification;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import objects.User;
import database.jdbc.UserOper;

@SuppressWarnings("serial")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (SecurePassword.checkPassword(email, password)) {
        	// Password correct, create User object and store it in session
            User user = UserOper.getUser(email); // You need to implement this method to retrieve the User object
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            // Password correct, redirect to another JSP page
            response.sendRedirect(request.getContextPath() + "/contacts");
        } else {
            // Password incorrect, redirect back to login page with an error message
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("Login/login.jsp").forward(request, response);
        }
    }
}