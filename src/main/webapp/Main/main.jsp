<%@ page import="java.util.List" %>
<%@ page import="objects.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebChat</title>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(to right, #00b09b, #96c93d);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        overflow: hidden;
    }
    .app-container {
        width: 90%;
        height: 80vh;
        display: flex;
        background: #FFF;
        border-radius: 8px;
        box-shadow: 0 10px 40px rgba(0,0,0,0.1);
        overflow: hidden;
    }
    .sidebar {
        width: 35%;
        max-width: 350px;
        background: #f7f7f7;
        border-right: 1px solid #ddd;
        overflow-y: auto;
    }
    .chat-list-item {
        padding: 20px 15px;
        border-bottom: 1px solid #ddd;
        cursor: pointer;
        display: flex;
        align-items: center;
        transition: background-color 0.2s;
    }
    .chat-list-item:hover {
        background-color: #e9ffed;
    }
    .chat-window {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        overflow: hidden;
    }
    .menu-options {
        padding: 15px;
        background: #ededed;
        border-bottom: 1px solid #ddd;
        text-align: right;
    }
    .menu-options a {
        padding: 8px 15px;
        color: #00b09b;
        text-decoration: none;
        font-size: 16px;
        display: inline-block;
        transition: background-color 0.2s;
    }
    .menu-options a:hover {
        background-color: #e9ffed;
        border-radius: 4px;
    }
    .chat-window-header {
        background: #f7f7f7;
        padding: 15px;
        border-bottom: 1px solid #ddd;
        position: sticky;
        top: 0;
    }
    .chat-window-messages {
        flex-grow: 1;
        padding: 20px;
        overflow-y: auto;
    }
    .message {
        margin-bottom: 15px;
        line-height: 1.5;
    }
    .message.sent {
        text-align: right;
    }
    .message.received {
        text-align: left;
    }
    .message p {
        display: inline-block;
        padding: 10px;
        border-radius: 18px;
        max-width: 60%;
    }
    .message.sent p {
        background-color: #dcf8c6;
    }
    .message.received p {
        background-color: #e5e5ea;
    }
    
    .chat-window-footer {
        padding: 15px;
        background: #f7f7f7;
        border-top: 1px solid #ddd;
    }
    .chat-input-container {
        display: flex;
        gap: 10px;
    }
    .chat-input {
        flex-grow: 1;
        padding: 10px;
        border-radius: 18px;
        border: 1px solid #ddd;
    }
    .send-btn {
        padding: 10px 15px;
        border: none;
        border-radius: 18px;
        background-color: #4CAF50;
        color: white;
        cursor: pointer;
        transition: background-color 0.2s;
    }
    .send-btn:hover {
        background-color: #45a049;
    }
    .timestamp {
    font-size: 0.75rem; /* smaller font size for timestamps */
    color: #666; /* a lighter text color */
    text-align: right; /* align to the right for sent messages */
    margin-top: 5px; /* some space between the message and the timestamp */
	}
	.message.received .timestamp {
	    text-align: left; /* align to the left for received messages */
	}
	.active-chat {
	    background-color: #e9ffed; /* Or any other color you prefer */
	}
</style>
</head>
<body>

<div class="app-container">
    <div class="sidebar">
        <div class="menu-options">
            <a href="#" id="new-contact-link">New Contact</a>
            <a href="/WhatsappClone/Login/login.jsp">Log out</a>
        </div>
        <% List<User> contacts = (List<User>) request.getAttribute("contacts");
            if (contacts != null) {
                for (User user : contacts) {
        %>
		<div class="chat-list-item" data-contact-id="<%= user.getId() %>" onclick="loadMessages(<%= user.getId() %>)">
		    <%= user.getUsername() %>
		</div>
        <%  	}
            }
        %>
   	</div>
        <!-- Add more chat list items here -->
    <div class="chat-window">
        <div class="chat-window-header">
            <h3>Chat</h3>
        </div>
        <div class="chat-window-messages">
            <!-- Chat messages here -->

            <!-- More messages here -->
        </div>
        <div class="chat-window-footer">
            <form id="message-form" class="chat-input-container">
			    <input type="text" class="chat-input" id="message" name="message" placeholder="Type a message..." required>
			    <input type="hidden" id="receiverId" name="receiverId" value="" />
			    <button type="submit" class="send-btn">Send</button>
			</form>
        </div>
    </div>
</div>


<script>
document.getElementById('new-contact-link').addEventListener('click', function(e) {
    e.preventDefault(); // Prevent the default action of the link (navigation)

    // Redirect the user to the desired JSP
    window.location.href = 'Main/newContact.jsp';
});

// This will store the context path in a JavaScript variable
var contextPath = "<%=request.getContextPath()%>";
var currentUserId = <%= ((User)session.getAttribute("currentUser")).getId() %>;

var socket;

function openWebSocket() {
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        return;
    }
    socket = new WebSocket('wss://' + window.location.host + contextPath + '/chat/' + currentUserId);

    socket.onclose = function(event) {
        console.log('Connection closed');
    };
}

openWebSocket();

socket.onmessage = function(event) {
    var message = JSON.parse(event.data);
    
    var activeChatListItemId = document.querySelector('.chat-list-item.active-chat').getAttribute('data-contact-id');
    
    if (activeChatListItemId == message.senderId) {
    	if (message.receiverId === currentUserId || message.senderId === currentUserId) {
            // Update the chat window with the incoming message
            var messagesContainer = document.querySelector('.chat-window-messages');
            
            var messageDiv = document.createElement('div');
            messageDiv.classList.add('message');
            messageDiv.classList.add(message.senderId === currentUserId ? 'sent' : 'received');
            
            var p = document.createElement('p');
            p.textContent = message.text;
            messageDiv.appendChild(p);

            var timestampDiv = document.createElement('div');
            timestampDiv.classList.add('timestamp');
            timestampDiv.textContent = message.timestamp; // Adjust the format as needed
            messageDiv.appendChild(timestampDiv);

            messagesContainer.appendChild(messageDiv);
            messagesContainer.scrollTop = messagesContainer.scrollHeight; // Scroll to the latest message
        }	
    }
};

function loadMessages(contactId) {
	// First, remove the active class from all chat list items
	var chatListItems = document.querySelectorAll('.chat-list-item');
    chatListItems.forEach(function(item) {
        item.classList.remove('active-chat');
    });

    // Then, find the chat list item for the contactId and add the active class
    var activeChatListItem = document.querySelector('.chat-list-item[data-contact-id="' + contactId + '"]');
    if (activeChatListItem) {
        activeChatListItem.classList.add('active-chat');
    }
	
    // Update the receiverId hidden field with the contactId
    document.getElementById('receiverId').value = contactId;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var messages = JSON.parse(xhr.responseText);
            var messagesContainer = document.querySelector('.chat-window-messages');
            messagesContainer.innerHTML = '';  // Clear previous messages

            messages.forEach(function(message) {
                var messageDiv = document.createElement('div');
                messageDiv.classList.add('message');
                // Adjust this line if the senderId does not reflect the current user's ID
                messageDiv.classList.add(message.senderId == currentUserId ? 'sent' : 'received');

                var p = document.createElement('p');
                p.textContent = message.text;  // Use 'text' field from your Message class
                messageDiv.appendChild(p);

                var timestampDiv = document.createElement('div');
                timestampDiv.classList.add('timestamp');
                timestampDiv.textContent = message.timestamp;
                messageDiv.appendChild(timestampDiv);
                
                messagesContainer.appendChild(messageDiv);
            });
        }
    };
    xhr.open('GET', contextPath + '/MessageServlet?contactId=' + contactId, true);
    xhr.send();
}

document.getElementById('message-form').addEventListener('submit', function(e) {
    e.preventDefault();
    var messageInput = document.getElementById('message');
    var receiverInput = document.getElementById('receiverId');

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var newMessage = JSON.parse(xhr.responseText);
            // Update chat with new message
            var messagesContainer = document.querySelector('.chat-window-messages');
            
            var messageDiv = document.createElement('div');
            messageDiv.classList.add('message', 'sent');
            
            var p = document.createElement('p');
            p.textContent = newMessage.text;
            messageDiv.appendChild(p);
            
            var timestampDiv = document.createElement('div');
            timestampDiv.classList.add('timestamp');
            timestampDiv.textContent = newMessage.timestamp;
            messageDiv.appendChild(timestampDiv);
            
            messagesContainer.appendChild(messageDiv);
            messagesContainer.scrollTop = messagesContainer.scrollHeight; // Scroll to the latest message
            // Clear the message input
            messageInput.value = '';
        }
    };
    xhr.open('POST', contextPath + '/SendMessageServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var data = 'message=' + encodeURIComponent(messageInput.value) + '&receiverId=' + encodeURIComponent(receiverInput.value);
    xhr.send(data);
});
</script>

</body>
</html>
