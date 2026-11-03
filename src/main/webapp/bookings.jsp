<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Booking" %>
<%@ page import="com.hotel.model.Guest" %>
<%@ page import="com.hotel.model.Room" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background: #f4f6f9;
        }
        h2 { color: #243b55; }
        form {
            background: white;
            padding: 20px;
            margin-bottom: 25px;
        }
        input, select {
            width: 95%;
            padding: 9px;
            margin: 6px 0 14px;
        }
        button {
            padding: 10px 18px;
            background: #243b55;
            color: white;
            border: none;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }
        th, td {
            padding: 9px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th { background: #243b55; color: white; }
    </style>
</head>
<body>

<h2>Booking Management</h2>

<% if ("1".equals(request.getParameter("added"))) { %>
    <p>Booking added successfully.</p>
<% } %>

<% if (request.getParameter("error") != null) { %>
    <p style="color:red">
        Booking failed. Check the dates, IDs, room availability,
        and Eclipse Console for details.
        Error: <%= request.getParameter("error") %>
    </p>
<% } %>

<h3>Create Booking</h3>

<%
    List<Guest> guests =
        (List<Guest>) request.getAttribute("guests");
    List<Room> rooms =
        (List<Room>) request.getAttribute("rooms");
%>

<form action="<%= request.getContextPath() %>/bookings"
      method="post">

    <label>Booking ID</label><br>
    <input type="number" name="bookingId" min="1" required>

    <label>Guest</label><br>
    <select name="guestId" required>
        <option value="">Select guest</option>
        <% if (guests != null) {
            for (Guest g : guests) { %>
                <option value="<%= g.getGuestId() %>">
                    <%= g.getGuestId() %> -
                    <%= g.getName() %>
                </option>
        <%  }
        } %>
    </select>

    <label>Room</label><br>
    <select name="roomId" required>
        <option value="">Select room</option>
        <% if (rooms != null) {
            for (Room r : rooms) {
                if (r.getAvailable() == 1) { %>
                    <option value="<%= r.getRoomId() %>">
                        <%= r.getRoomNumber() %> -
                        <%= r.getRoomType() %> -
                        Rent: <%= r.getRent() %>
                    </option>
        <%      }
            }
        } %>
    </select>

    <label>Check-in</label><br>
    <input type="date" name="checkIn" required>

    <label>Check-out</label><br>
    <input type="date" name="checkOut" required>

    <button type="submit">Create Booking</button>
</form>

<h3>Existing Bookings</h3>

<%
    List<Booking> bookings =
        (List<Booking>) request.getAttribute("bookings");
%>

<% if (bookings != null && !bookings.isEmpty()) { %>
<table>
    <tr>
        <th>Booking ID</th>
        <th>Guest</th>
        <th>Room</th>
        <th>Check-in</th>
        <th>Check-out</th>
        <th>Total Amount</th>
        <th>Status</th>
    </tr>

    <% for (Booking b : bookings) { %>
    <tr>
        <td><%= b.getBookingId() %></td>
        <td><%= b.getGuestName() %></td>
        <td><%= b.getRoomNumber() %></td>
        <td><%= b.getCheckIn() %></td>
        <td><%= b.getCheckOut() %></td>
        <td><%= b.getTotalAmount() %></td>
        <td><%= b.getStatus() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
    <p>No bookings found.</p>
<% } %>

</body>
</html>
