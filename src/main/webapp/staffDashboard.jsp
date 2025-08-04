<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/4/2025
  Time: 8:33 PM
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
    if (!"staff".equalsIgnoreCase(user.getRole())) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        return;
    }
%>
<html>
<head>
    <title>Staff Dashboard - Pahana Edu</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .header {
            background-color: #3498db;
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
            color: #3498db;
        }
        .menu-card a {
            text-decoration: none;
            color: #2c3e50;
            font-weight: bold;
        }
        .menu-card a:hover {
            color: #3498db;
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
        .quick-actions {
            margin-top: 30px;
            padding: 20px;
            background: #eaf2f8;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Pahana Edu - Staff Dashboard</h1>
    <div>
        <span>Welcome, <%= user.getFullName() %></span>
        <a href="../logout" class="logout-btn">Logout</a>
    </div>
</div>

<div class="container">
    <h2>Daily Operations</h2>

    <div class="dashboard-menu">
        <div class="menu-card">
            <h3>Customer Management</h3>
            <p>View and manage customer accounts</p>
            <a href="<%= request.getContextPath() %>/customer-management">Go to Customer Management →</a>
        </div>

        <div class="menu-card">
            <h3>Billing System</h3>
            <p>Create and manage customer bills</p>
            <a href="../billing.jsp">Go to Billing →</a>
        </div>

        <div class="menu-card">
            <h3>Inventory Lookup</h3>
            <p>Check book availability and details</p>
            <a href="../inventory.jsp">View Inventory →</a>
        </div>

        <div class="menu-card">
            <h3>Help Center</h3>
            <p>Get assistance with the system</p>
            <a href="../help.jsp">Get Help →</a>
        </div>
    </div>

    <div class="quick-actions">
        <h3>Quick Actions</h3>
        <ul>
            <li><a href="../billing.jsp?action=new">Create New Bill</a></li>
            <li><a href="../customer-management.jsp?action=add">Add New Customer</a></li>
            <li><a href="../inventory.jsp?filter=low">View Low Stock Items</a></li>
        </ul>
    </div>
</div>
</body>
</html>