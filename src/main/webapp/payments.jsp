
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Payment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Management</title>
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
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th { background: #243b55; color: white; }
    </style>
</head>
<body>

<h2>Payment Management</h2>

<% if ("1".equals(request.getParameter("added"))) { %>
    <p>Payment recorded successfully.</p>
<% } %>

<% if (request.getParameter("error") != null) { %>
    <p style="color:red">
        Payment could not be recorded.
        Check the booking ID, payment details, and Eclipse Console.
    </p>
<% } %>

<h3>Record Payment</h3>

<form action="<%= request.getContextPath() %>/payments"
      method="post">

    <label>Payment ID</label><br>
    <input type="number" name="paymentId" min="1" required>

    <label>Booking ID</label><br>
    <input type="number" name="bookingId" min="1" required>

    <label>Amount</label><br>
    <input type="number" name="amount"
           min="0.01" step="0.01" required>

    <label>Payment Method</label><br>
    <select name="paymentMethod" required>
        <option value="">Select method</option>
        <option value="Cash">Cash</option>
        <option value="Card">Card</option>
        <option value="UPI">UPI</option>
        <option value="Bank Transfer">Bank Transfer</option>
    </select>

    <label>Payment Date</label><br>
    <input type="date" name="paymentDate" required>

    <button type="submit">Record Payment</button>
</form>

<h3>Payment History</h3>

<%
    List<Payment> payments =
        (List<Payment>) request.getAttribute("payments");
%>

<% if (payments != null && !payments.isEmpty()) { %>
<table>
    <tr>
        <th>Payment ID</th>
        <th>Booking ID</th>
        <th>Amount</th>
        <th>Method</th>
        <th>Date</th>
        <th>Status</th>
    </tr>

    <% for (Payment p : payments) { %>
    <tr>
        <td><%= p.getPaymentId() %></td>
        <td><%= p.getBookingId() %></td>
        <td><%= p.getAmount() %></td>
        <td><%= p.getPaymentMethod() %></td>
        <td><%= p.getPaymentDate() %></td>
        <td><%= p.getStatus() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
    <p>No payments recorded yet.</p>
<% } %>

</body>
</html>
