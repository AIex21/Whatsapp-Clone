<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - WebChat</title>
<style>
    body {
        font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
        margin: 0;
        padding: 0;
        background: linear-gradient(to right, #00b09b, #96c93d);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .login-container {
        background: white;
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        width: 300px;
    }
    .login-container h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }
    .form-input {
        margin-bottom: 20px;
    }
    .form-input label {
        display: block;
        margin-bottom: 5px;
        color: #666;
    }
    .form-input input {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
        box-sizing: border-box;
    }
    .submit-btn {
        width: 100%;
        padding: 10px;
        border: none;
        border-radius: 5px;
        background-color: #4CAF50;
        color: white;
        cursor: pointer;
        font-size: 16px;
    }
    .submit-btn:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>

<div class="login-container">
    <h2>Login to WebChat</h2>
    
    <% String errorMessage = (String) request.getAttribute("errorMessage");
       if(errorMessage != null) { %>
       <p style="color:red;"><%= errorMessage %></p>
    <% } %>
    
    <form action="${pageContext.request.contextPath}/LoginServlet" method="post"> <!-- Notice the action now points to the LoginServlet -->
        <div class="form-input">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-input">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-input">
            <input type="submit" value="Login" class="submit-btn">
        </div>
    </form>
    <div style="text-align: center;">
        Don't have an account? <a href="/WhatsappClone/Register/register.jsp" style="color: #4CAF50; text-decoration: none;">Sign up</a>
    </div>
</div>

</body>
</html>
