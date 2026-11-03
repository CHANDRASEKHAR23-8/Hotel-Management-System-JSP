
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f3f5f9;
        }
        header {
            background: #203864;
            color: white;
            padding: 22px;
            text-align: center;
        }
        .container {
            max-width: 900px;
            margin: 35px auto;
            padding: 20px;
        }
        .menu {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }
        .menu a {
            display: block;
            background: white;
            padding: 30px 20px;
            text-align: center;
            text-decoration: none;
            color: #203864;
            font-size: 18px;
            font-weight: bold;
            border-radius: 10px;
            box-shadow: 0 3px 10px #00000012;
        }
        .menu a:hover {
            background: #dce6f7;
        }
        .logout {
            display: inline-block;
            margin-top: 25px;
            color: #b42318;
        }
    </style>
</head>
<body>

<header>
    <h1>Hotel Management System</h1>
    <p>Admin Dashboard</p>
</header>

<div class="container">
    <h2>Welcome, ${sessionScope.username}!</h2>
    <p>Select a module to continue.</p>

    <div class="menu">
        <a href="${pageContext.request.contextPath}/rooms">
            Manage Rooms
        </a>
        <a href="${pageContext.request.contextPath}/guests">
            Manage Guests
        </a>
        <a href="${pageContext.request.contextPath}/bookings">
            Manage Bookings
        </a>
        <a href="${pageContext.request.contextPath}/payments">
            Manage Payments
        </a>
    </div>

    <a class="logout"
       href="${pageContext.request.contextPath}/logout">
        Logout
    </a>
</div>

</body>
</html>
