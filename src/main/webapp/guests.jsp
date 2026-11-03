
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Guest" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Guest Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background: #f4f6f9;
        }
        h2 { color: #243b55; }
        form, table {
            background: white;
            padding: 15px;
            margin-bottom: 25px;
            width: 100%;
            box-sizing: border-box;
        }
        input {
            padding: 9px;
            margin: 5px 0 12px;
            width: 95%;
        }
        button {
            padding: 10px 18px;
            background: #243b55;
            color: white;
            border: none;
            cursor: pointer;
        }
        table { border-collapse: collapse; }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th { background: #243b55; color: white; }
        .message { margin: 12px 0; }
    </style>
</head>
<body>
    <h2>Guest Management</h2>

    <% if ("1".equals(request.getParameter("added"))) { %>
        <p class="message">Guest added successfully.</p>
    <% } %>

    <% if ("1".equals(request.getParameter("error"))) { %>
        <p class="message">Could not add guest. Check the ID and server console.</p>
    <% } %>

    <h3>Add New Guest</h3>
    <form action="<%= request.getContextPath() %>/guests"
          method="post">
        <label>Guest ID</label><br>
        <input type="number" name="guestId" required><br>

        <label>Name</label><br>
        <input type="text" name="name" required><br>

        <label>Phone</label><br>
        <input type="text" name="phone"><br>

        <label>Email</label><br>
        <input type="email" name="email"><br>

        <label>Address</label><br>
        <input type="text" name="address"><br>

        <button type="submit">Add Guest</button>
    </form>

    <h3>Guest List</h3>

    <%
        List<Guest> guests =
            (List<Guest>) request.getAttribute("guests");
    %>

    <% if (guests != null && !guests.isEmpty()) { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Address</th>
            </tr>

            <% for (Guest guest : guests) { %>
                <tr>
                    <td><%= guest.getGuestId() %></td>
                    <td><%= guest.getName() %></td>
                    <td><%= guest.getPhone() %></td>
                    <td><%= guest.getEmail() %></td>
                    <td><%= guest.getAddress() %></td>
                </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No guests found yet.</p>
    <% } %>
</body>
</html>
