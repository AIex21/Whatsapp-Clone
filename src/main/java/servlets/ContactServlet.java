package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import objects.User;
import objects.Contact;

import database.jdbc.ContactOper;
import database.jdbc.UserOper;

@SuppressWarnings("serial")
@WebServlet("/contacts")
public class ContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser != null) {
            // Replace with your method to get contacts for the user
            List<Contact> contacts = ContactOper.getContacts(currentUser.getId());
            
            List<User> contactsUser = new ArrayList<User>();
            for (Contact contact : contacts) {
            	User user = UserOper.getUser(contact.getContactId());
            	contactsUser.add(user);
            }
            request.setAttribute("contacts", contactsUser);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("Main/main.jsp");
        dispatcher.forward(request, response);
    }
}
