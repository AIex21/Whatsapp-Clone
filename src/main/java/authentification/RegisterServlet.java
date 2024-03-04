package authentification;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import database.jdbc.UserOper;
import objects.User;

@SuppressWarnings("serial")
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (UserOper.checkEmail(email)) {
            // Email does not exist, create account, redirect to login page
            response.sendRedirect("Login/login.jsp");
            byte[] salt = null;
			try {
				salt = SecurePassword.getSalt();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String passwordHash = SecurePassword.getSecurePassword(password, salt);
            User user = new User(1, username, passwordHash, salt, email);
            UserOper.addUser(user);
;        } else {
            // Password incorrect, redirect back to login page with an error message
            request.setAttribute("errorMessage", "An account with this email already exists.");
            request.getRequestDispatcher("Register/register.jsp").forward(request, response);
        }
    }
}
