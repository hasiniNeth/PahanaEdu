<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 2:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.Staff" %>
<%
    List<Staff> staffList = (List<Staff>) request.getAttribute("staffList");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        h2 { color: #2c3e50; }
        .message { color: green; font-weight: bold; margin-bottom: 15px; }
        .form-section, .table-section {
            background: white; padding: 20px; margin-bottom: 30px;
            border: 1px solid #ddd; border-radius: 5px;
        }
        label { display: block; margin-top: 10px; }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 8px; margin-top: 5px;
        }
        input[type="submit"] {
            margin-top: 15px; padding: 10px 20px;
            background: #3498db; color: white; border: none; cursor: pointer;
        }
        table {
            width: 100%; border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ccc; padding: 10px;
        }
        th { background-color: #ecf0f1; }
        a { text-decoration: none; color: #3498db; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<h2>Staff Management</h2>

<% if (message != null) { %>
<p class="message"><%= message %></p>
<% } %>

<div class="form-section">
    <h3>Add New Staff</h3>
    <form method="post" action="staff-management">
        <input type="hidden" name="action" value="add" />
        <label for="username">Username:</label>
        <input type="text" name="username" required />

        <label for="password">Password:</label>
        <input type="password" name="password" required />

        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" required />

        <input type="submit" value="Add Staff" />
    </form>
</div>

<div class="table-section">
    <h3>Existing Staff</h3>
    <% if (staffList == null || staffList.isEmpty()) { %>
    <p>No staff members found.</p>
    <% } else { %>
    <table>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Full Name</th>
            <th>Actions</th>
        </tr>
        <% for (Staff staff : staffList) { %>
        <tr>
            <td><%= staff.getUserId() %></td>
            <td><%= staff.getUsername() %></td>
            <td><%= staff.getFullName() %></td>
            <td>
                <a href="staff-management?action=edit&id=<%= staff.getUserId() %>">Edit</a> |
                <form method="post" action="staff-management" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this staff member?');">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="<%= staff.getUserId() %>" />
                    <input type="submit" value="Delete" />
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } %>
</div>

</body>
</html>
