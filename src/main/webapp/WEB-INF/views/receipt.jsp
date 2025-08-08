<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/8/2025
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.*" %>

<%
    Bill bill = (Bill) session.getAttribute("recentBill");
    if (bill == null) {
%>
<p>No bill found. Please go back and try again.</p>
<%
} else {
%>
<!DOCTYPE html>
<html>
<head>
    <title>Receipt</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h2 { margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f0f0f0; }
        .total { font-weight: bold; }
        .print-button {
            padding: 10px 20px;
            background: green;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h2>Receipt</h2>

<table>
    <tr><th>Title</th><th>Qty</th><th>Price</th><th>Subtotal</th></tr>
    <%
        for (BillItem item : bill.getItems()) {
    %>
    <tr>
        <td><%= item.getBookTitle() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getSubtotal() %></td>
    </tr>
    <% } %>
    <tr class="total">
        <td colspan="3" align="right">Total:</td>
        <td><%= bill.getTotalAmount() %></td>
    </tr>
</table>

<button class="print-button" onclick="window.print()">🖨️ Print Receipt</button>

</body>
</html>
<%
    }
%>
