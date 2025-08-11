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
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Pahana Edu</title>
    <style>
        :root {
            --primary-color: #4361ee;
            --secondary-color: #3a0ca3;
            --accent-color: #f72585;
            --success-color: #4cc9f0;
            --light-bg: #f8f9fa;
            --dark-bg: #2b2d42;
            --text-color: #2b2d42;
            --light-text: #f8f9fa;
            --card-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --transition: all 0.3s ease;
        }
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background-color: var(--light-bg);
            color: var(--text-color);
            display: flex;
            min-height: 100vh;
            line-height: 1.6;
        }
        .sidebar {
            width: 280px;
            background: linear-gradient(135deg, var(--dark-bg), #1a1a2e);
            color: var(--light-text);
            padding: 2rem 0;
            box-shadow: 4px 0 15px rgba(0, 0, 0, 0.1);
        }
        .logo {
            text-align: center;
            padding: 0 2rem 2rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            margin-bottom: 1.5rem;
        }
        .logo h1 {
            font-size: 1.8rem;
            color: white;
            margin-bottom: 0.5rem;
            font-weight: 700;
            letter-spacing: 1px;
        }
        .logo p {
            color: rgba(255, 255, 255, 0.7);
            font-size: 0.9rem;
        }
        .nav-rhombus {
            padding: 0 1.5rem;
        }
        .nav-rhombus h2 {
            color: rgba(255, 255, 255, 0.9);
            font-size: 1.1rem;
            margin-bottom: 1.5rem;
            padding-left: 0.5rem;
            font-weight: 500;
            text-transform: uppercase;
        }
        .nav-menu {
            list-style: none;
        }
        .nav-menu li {
            margin-bottom: 0.8rem;
        }
        .nav-menu li a {
            display: flex;
            align-items: center;
            padding: 0.8rem 1.2rem;
            color: rgba(255, 255, 255, 0.8);
            text-decoration: none;
            border-radius: 6px;
            transition: var(--transition);
            font-size: 0.95rem;
        }
        .nav-menu li a i {
            margin-right: 0.8rem;
            font-size: 1.1rem;
            width: 20px;
            text-align: center;
        }
        .nav-menu li a:hover {
            background: rgba(255, 255, 255, 0.1);
            color: white;
            transform: translateX(5px);
        }
        .nav-menu li a.active {
            background: var(--primary-color);
            color: white;
        }
        .main-content {
            flex: 1;
            padding: 2rem;
            background-color: var(--light-bg);
            overflow-y: auto;
        }
        .header {
            background: white;
            padding: 1.5rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            border-radius: 8px;
            box-shadow: var(--card-shadow);
        }
        .header h1 {
            font-size: 1.5rem;
            font-weight: 600;
            color: var(--primary-color);
        }
        .user-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        .logout-btn {
            background: var(--accent-color);
            color: white;
            padding: 0.6rem 1.2rem;
            border-radius: 6px;
            text-decoration: none;
            font-weight: 500;
            transition: var(--transition);
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }
        .logout-btn:hover {
            background: #d91a6b;
            transform: translateY(-2px);
        }
        .dashboard-menu {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 1.5rem;
            margin-top: 1.5rem;
        }
        .menu-card {
            background: white;
            border-radius: 10px;
            padding: 1.8rem;
            transition: var(--transition);
            box-shadow: var(--card-shadow);
        }
        .menu-card h3 {
            color: var(--primary-color);
            font-size: 1.2rem;
            margin-bottom: 0.8rem;
        }
        .menu-card p {
            color: #666;
            margin-bottom: 1.5rem;
            font-size: 0.95rem;
        }
        .menu-card a {
            text-decoration: none;
            color: var(--secondary-color);
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: var(--transition);
        }
        .menu-card a:hover {
            color: var(--accent-color);
            transform: translateX(5px);
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<!-- Sidebar -->
<div class="sidebar">
    <div class="logo">
        <h1>PAHANA EDU</h1>
        <p>BOOKSHOP MANAGEMENT</p>
    </div>
    <div class="nav-rhombus">
        <h2>Admin Menu</h2>
        <ul class="nav-menu">
            <li><a href="#" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
            <li><a href="<%= request.getContextPath() %>/customer-management"><i class="fas fa-users"></i> Customer Management</a></li>
            <li><a href="<%= request.getContextPath() %>/staff-management"><i class="fas fa-user-tie"></i> Staff Management</a></li>
            <li><a href="<%= request.getContextPath() %>/book-management"><i class="fas fa-book"></i> Book Management</a></li>
            <li><a href="<%= request.getContextPath() %>/bill-create"><i class="fas fa-receipt"></i> Billing</a></li>
            <li><a href="<%= request.getContextPath() %>/help.jsp"><i class="fas fa-question-circle"></i> Help</a></li>
        </ul>
    </div>
</div>

<!-- Main Content -->
<div class="main-content">
    <div class="header">
        <h1>Pahana Edu - Admin Dashboard</h1>
        <div class="user-info">
            <span>Welcome, <%= user.getFullName() %></span>
            <a href="<%= request.getContextPath() %>/logout" class="logout-btn">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </div>

    <div class="container">
        <h2>Administration Panel</h2>
        <div class="dashboard-menu">
            <div class="menu-card">
                <h3>Customer Management</h3>
                <p>Manage all customer accounts and information</p>
                <a href="<%= request.getContextPath() %>/customer-management">Go to Customer Management</a>
            </div>
            <div class="menu-card">
                <h3>Staff Management</h3>
                <p>Add, edit, or remove staff accounts</p>
                <a href="<%= request.getContextPath() %>/staff-management">Go to Staff Management</a>
            </div>
            <div class="menu-card">
                <h3>Inventory Management</h3>
                <p>Add, edit, and manage book inventory</p>
                <a href="<%= request.getContextPath() %>/book-management">Go to Book Management</a>
            </div>
            <div class="menu-card">
                <h3>Billing System</h3>
                <p>Create and manage customer bills and invoices</p>
                <a href="<%= request.getContextPath() %>/bill-create">Go to Billing</a>
            </div>
            <div class="menu-card">
                <h3>Help Center</h3>
                <p>Get assistance with the system and operations</p>
                <a href="<%= request.getContextPath() %>/help.jsp">Get Help</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
