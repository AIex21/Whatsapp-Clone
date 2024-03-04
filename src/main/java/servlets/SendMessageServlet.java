package servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.Gson;

import database.jdbc.MessageOper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import objects.Message;
import objects.SimpleMessage;
import objects.User;

import serverEndpoints.ChatEndpoint;

@SuppressWarnings("serial")
@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current user from session
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        // Get message text and receiver ID from request
        String messageText = request.getParameter("message");
        int receiverId = Integer.parseInt(request.getParameter("receiverId")); // You need to include receiverId in your form
        
        String receiverSessionId = String.valueOf(receiverId); // Convert receiverId to String if necessary.
        Session receiverSession = ChatEndpoint.getSessions().get(receiverSessionId);
        
        // Create and save the new message
        Message message = new Message();
        message.setSenderId(currentUser.getId());
        message.setReceiverId(receiverId);
        message.setText(messageText);
        message.setTimestamp(LocalDateTime.now());

        Message saveResult = MessageOper.addMessage(message); // You need to implement this method

        if (saveResult == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save the message.");
            return;
        }
        
        SimpleMessage simpleMessage = new SimpleMessage(message.getId(), message.getSenderId(), message.getReceiverId(), message.getText(), message.getTimestampString());
        
        if (receiverSession != null && receiverSession.isOpen()) {
            try {
                receiverSession.getBasicRemote().sendText(new Gson().toJson(simpleMessage));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error, possibly by logging it or notifying the sender that the message delivery failed
            }
        }
        // Send the message back to the client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(simpleMessage));
    }
}
