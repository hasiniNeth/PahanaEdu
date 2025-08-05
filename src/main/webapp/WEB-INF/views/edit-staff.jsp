<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedubookshop.model.Staff" %>
<%
    Staff staff = (Staff) request.getAttribute("staff");
    if (staff == null) {
        response.sendRedirect("staff-management?error=Staff not found");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Staff</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        .container {
            max-width: 500px; margin: 0 auto; background: white;
            padding: 20px; border: 1px solid #ddd; border-radius: 5px;
        }
        h2 { color: #2c3e50; text-align: center; }
        label { display: block; margin-top: 10px; }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 8px; margin-top: 5px;
        }
        input[type="submit"] {
            margin-top: 15px; padding: 10px 20px;
            background: #3498db; color: white; border: none; cursor: pointer;
        }
        a {
            display: inline-block; margin-top: 20px; color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Edit Staff Member</h2>

    <form method="post" action="staff-management">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="id" value="<%= staff.getUserId() %>" />

        <label for="username">Username:</label>
        <input type="text" name="username" value="<%= staff.getUsername() %>" required />

        <label for="password">Password:</label>
        <input type="password" name="password" placeholder="Leave blank to keep current password" />

        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" value="<%= staff.getFullName() %>" required />

        <input type="submit" value="Update Staff" />
    </form>

    <a href="staff-management">← Back to Staff Management</a>
</div>

</body>
</html>
