package servlets;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

import database.jdbc.MessageOper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.User;
import objects.SimpleMessage;

@SuppressWarnings("serial")
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get current user from session and contact ID from request
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        int contactId = Integer.parseInt(request.getParameter("contactId"));

        // Fetch messages between currentUser and contactId
        List<SimpleMessage> simpleMessages = MessageOper.getSimpleMessages(currentUser.getId(), contactId);
        
        // Convert simpleMessages to JSON and write to response
        String json = new Gson().toJson(simpleMessages);
        response.getWriter().write(json);
    }
}

