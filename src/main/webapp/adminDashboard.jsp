<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/4/2025
  Time: 8:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedubookshop.model.User" %>
<%
    // Manual authentication check
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
    if (!"admin".equalsIgnoreCase(user.getRole())) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        return;
    }
%>
<html>
<head>
    <title>Admin Dashboard - Pahana Edu</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .header {
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background: white;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .dashboard-menu {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .menu-card {
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 20px;
            transition: all 0.3s ease;
        }
        .menu-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .menu-card h3 {
            margin-top: 0;
            color: #2c3e50;
        }
        .menu-card a {
            text-decoration: none;
            color: #3498db;
            font-weight: bold;
        }
        .menu-card a:hover {
            color: #2980b9;
        }
        .logout-btn {
            background: #e74c3c;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .logout-btn:hover {
            background: #c0392b;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Pahana Edu - Admin Dashboard</h1>
    <div>
        <span>Welcome, <%= user.getFullName() %></span>
        <a href="../logout" class="logout-btn">Logout</a>
    </div>
</div>

<div class="container">
    <h2>Administration Panel</h2>

    <div class="dashboard-menu">
        <div class="menu-card">
            <h3>Customer Management</h3>
            <p>Manage all customer accounts and information</p>
            <a href="<%= request.getContextPath() %>/customer-management">Go to Customer Management →</a>
        </div>

        <div class="menu-card">
            <h3>Staff Management</h3>
            <p>Add, edit, or remove staff accounts</p>
            <a href="<%= request.getContextPath() %>/staff-management">Go to Staff Management →</a>
        </div>


        <div class="menu-card">
            <h3>Inventory Management</h3>
            <p>Add, edit, and manage book inventory</p>
            <a href="../itemManagement.jsp">Go to Inventory Management →</a>
        </div>

        <div class="menu-card">
            <h3>User Accounts</h3>
            <p>Manage staff and admin accounts</p>
            <a href="../userManagement.jsp">Go to User Management →</a>
        </div>

        <div class="menu-card">
            <h3>Reports</h3>
            <p>View sales and system reports</p>
            <a href="../reports.jsp">View Reports →</a>
        </div>
    </div>
</div>
</body>
</html>