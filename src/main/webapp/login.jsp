
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Management System - Login</title>
</head>
<body>
    <h2>Hotel Management System Login</h2>

    <form action="${pageContext.request.contextPath}/login"
          method="post">
        <label>Username:</label>
        <input type="text" name="username" required>
        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">Login</button>
    </form>

    <p style="color:red">${error}</p>
</body>
</html>
