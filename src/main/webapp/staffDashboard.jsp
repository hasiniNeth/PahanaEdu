<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/4/2025
  Time: 8:33 PM
  To change this template use File | Settings | File Templates.
--%>
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
<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard - Pahana Edu</title>
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2c3e50;
            --accent-color: #e74c3c;
            --light-bg: #f5f7fa;
            --dark-text: #2c3e50;
            --light-text: #ecf0f1;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            background-color: var(--light-bg);
            color: var(--dark-text);
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Navigation - Rhombus Style */
        .sidebar {
            width: 250px;
            background-color: white;
            box-shadow: 2px 0 10px rgba(0,0,0,0.1);
            padding: 20px 0;
        }

        .logo {
            text-align: center;
            padding: 20px 0;
            border-bottom: 1px solid #eee;
            margin-bottom: 30px;
        }

        .logo h1 {
            font-size: 1.5rem;
            color: var(--primary-color);
            margin-bottom: 5px;
        }

        .nav-rhombus {
            padding: 0 20px;
        }

        .nav-rhombus h2 {
            color: var(--primary-color);
            font-size: 1.2rem;
            margin-bottom: 20px;
            padding-left: 10px;
        }

        .nav-menu {
            list-style: none;
        }

        .nav-menu li {
            margin-bottom: 15px;
        }

        .nav-menu li a {
            display: block;
            padding: 10px 15px;
            color: #34495e;
            text-decoration: none;
            border-radius: 4px;
            transition: all 0.3s ease;
        }

        .nav-menu li a:hover,
        .nav-menu li a.active {
            background-color: var(--primary-color);
            color: white;
        }

        /* Main Content Area */
        .main-content {
            flex: 1;
            padding: 30px;
        }

        .header {
            background-color: var(--primary-color);
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
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
            color: var(--primary-color);
        }

        .menu-card a {
            text-decoration: none;
            color: var(--secondary-color);
            font-weight: bold;
        }

        .menu-card a:hover {
            color: var(--primary-color);
        }

        .logout-btn {
            background: var(--accent-color);
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

        .quick-actions ul {
            list-style: none;
        }

        .quick-actions li {
            margin-bottom: 10px;
        }

        .quick-actions a {
            text-decoration: none;
            color: var(--primary-color);
            font-weight: bold;
        }

        .quick-actions a:hover {
            text-decoration: underline;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<!-- Rhombus Style Sidebar Navigation -->
<div class="sidebar">
    <div class="logo">
        <h1>PAHANA EDU</h1>
        <p>STAFF PORTAL</p>
    </div>

    <div class="nav-rhombus">
        <h2>Staff Menu</h2>
        <ul class="nav-menu">
            <li><a href="#" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
            <li><a href="customer-management"><i class="fas fa-users"></i> Customers</a></li>
            <li><a href="book-management"><i class="fas fa-book"></i> Inventory</a></li>
            <li><a href="../billing.jsp"><i class="fas fa-receipt"></i> Billing</a></li>
            <li><a href="../inventory.jsp"><i class="fas fa-search"></i> Lookup</a></li>
            <li><a href="../help.jsp"><i class="fas fa-question-circle"></i> Help</a></li>
        </ul>
    </div>
</div>

<!-- Main Content Area -->
<div class="main-content">
    <div class="header">
        <h1>Pahana Edu - Staff Dashboard</h1>
        <div>
            <span>Welcome, <%= user.getFullName() %></span>
            <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Logout</a>
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
                <h3>Inventory Management</h3>
                <p>Add, edit, and manage book inventory</p>
                <a href="<%= request.getContextPath() %>/book-management">Go to Inventory Management →</a>
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
</div>
</body>
</html>