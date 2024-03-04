package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import objects.Contact;
import objects.User;
import database.jdbc.UserOper;
import database.jdbc.ContactOper;

@SuppressWarnings("serial")
@WebServlet("/AddContactServlet")
public class AddContactServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        
        User contactUser = UserOper.getUser(email);
        if (contactUser != null) {
        	User currentUser = (User) request.getSession().getAttribute("currentUser");
        	if (currentUser.getEmail().equals(email)) {
        		request.setAttribute("errorMessage", "You can't add yourself in your contacts.");
                request.getRequestDispatcher("Main/newContact.jsp").forward(request, response);
        	} else {
        		Contact contact = ContactOper.getContact(currentUser.getId(), contactUser.getId());
            	if (contact == null) {
            		contact = new Contact(1, currentUser.getId(), contactUser.getId());
            		ContactOper.addContact(contact);
            		response.sendRedirect(request.getContextPath() + "/contacts");
            	} else {
            		request.setAttribute("errorMessage", "You already have this user in your contacts.");
                    request.getRequestDispatcher("Main/newContact.jsp").forward(request, response);
            	}
        	}
        } else {
        	request.setAttribute("errorMessage", "There is no user with that email.");
            request.getRequestDispatcher("Main/newContact.jsp").forward(request, response);
        }
    }
}
