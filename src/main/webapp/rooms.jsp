
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Room" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Room Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background: #f4f6f9;
        }
        h2 {
            color: #243b55;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background: #243b55;
            color: white;
        }
        tr:nth-child(even) {
            background: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Hotel Room Management</h2>

    <%
        List<Room> rooms =
            (List<Room>) request.getAttribute("rooms");
    %>

    <% if (rooms != null && !rooms.isEmpty()) { %>
        <table>
            <tr>
                <th>Room ID</th>
                <th>Room Number</th>
                <th>Room Type</th>
                <th>Capacity</th>
                <th>Rent</th>
                <th>Available</th>
            </tr>

            <% for (Room room : rooms) { %>
                <tr>
                    <td><%= room.getRoomId() %></td>
                    <td><%= room.getRoomNumber() %></td>
                    <td><%= room.getRoomType() %></td>
                    <td><%= room.getCapacity() %></td>
                    <td><%= room.getRent() %></td>
                    <td><%= room.getAvailable() == 1
                            ? "Yes" : "No" %></td>
                </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No rooms found. Check your database or server console.</p>
    <% } %>
</body>
</html>
