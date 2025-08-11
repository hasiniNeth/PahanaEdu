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
            --primary-color: #4a6fa5;
            --secondary-color: #166d67;
            --accent-color: #ff7e5f;
            --light-bg: #f8f9fa;
            --card-bg: #ffffff;
            --sidebar-bg: #2c3e50;
            --text-color: #333333;
            --light-text: #ffffff;
            --border-color: #e0e0e0;
            --success-color: #28a745;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: var(--light-bg);
            color: var(--text-color);
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Navigation */
        .sidebar {
            width: 250px;
            background-color: var(--sidebar-bg);
            color: var(--light-text);
            padding: 20px 0;
            transition: all 0.3s ease;
        }

        .logo {
            text-align: center;
            padding: 20px 0;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            margin-bottom: 30px;
        }

        .logo h1 {
            font-size: 1.5rem;
            color: var(--light-text);
            margin-bottom: 5px;
        }

        .logo p {
            font-size: 0.8rem;
            color: rgba(255,255,255,0.7);
        }

        .nav-rhombus {
            padding: 0 20px;
        }

        .nav-rhombus h2 {
            color: var(--light-text);
            font-size: 1.1rem;
            margin-bottom: 20px;
            padding-left: 10px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .nav-menu {
            list-style: none;
        }

        .nav-menu li {
            margin-bottom: 10px;
        }

        .nav-menu li a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 15px;
            color: rgba(255,255,255,0.8);
            text-decoration: none;
            border-radius: 4px;
            transition: all 0.3s ease;
        }

        .nav-menu li a:hover {
            background-color: rgba(255,255,255,0.1);
            color: var(--light-text);
        }

        .nav-menu li a.active {
            background-color: var(--primary-color);
            color: var(--light-text);
            font-weight: 500;
        }

        .nav-menu li a i {
            width: 20px;
            text-align: center;
        }

        /* Main Content Area */
        .main-content {
            flex: 1;
            padding: 30px;
            overflow-y: auto;
        }

        .header {
            background-color: var(--card-bg);
            color: var(--text-color);
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .header h1 {
            font-size: 1.5rem;
            color: var(--primary-color);
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .user-info span {
            font-weight: 500;
            color: var(--text-color);
        }

        .logout-btn {
            background-color: var(--danger-color);
            color: var(--light-text);
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .logout-btn:hover {
            background-color: #c82333;
            transform: translateY(-2px);
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Dashboard Content */
        .dashboard-menu {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 30px;
        }

        .menu-card {
            background-color: var(--card-bg);
            border-radius: 8px;
            padding: 25px;
            box-shadow: 0 3px 15px rgba(0,0,0,0.05);
            transition: all 0.3s ease;
            border-top: 4px solid var(--primary-color);
        }

        .menu-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }

        .menu-card h3 {
            color: var(--primary-color);
            margin-bottom: 15px;
            font-size: 1.2rem;
        }

        .menu-card p {
            color: var(--text-color);
            margin-bottom: 20px;
            line-height: 1.5;
        }

        .menu-card a {
            display: inline-flex;
            align-items: center;
            gap: 5px;
            color: var(--secondary-color);
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .menu-card a:hover {
            color: var(--primary-color);
            gap: 8px;
        }

        .menu-card a::after {
            content: "→";
            transition: all 0.3s ease;
        }

        /* Quick Actions */
        .quick-actions {
            background-color: var(--card-bg);
            border-radius: 8px;
            padding: 25px;
            box-shadow: 0 3px 15px rgba(0,0,0,0.05);
            margin-top: 30px;
        }

        .quick-actions h3 {
            color: var(--primary-color);
            margin-bottom: 20px;
            font-size: 1.2rem;
            border-bottom: 1px solid var(--border-color);
            padding-bottom: 10px;
        }

        .quick-actions ul {
            list-style: none;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 15px;
        }

        .quick-actions li {
            margin-bottom: 0;
        }

        .quick-actions a {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 12px 15px;
            background-color: rgba(74, 111, 165, 0.1);
            color: var(--primary-color);
            text-decoration: none;
            border-radius: 6px;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .quick-actions a:hover {
            background-color: var(--primary-color);
            color: var(--light-text);
            transform: translateX(5px);
        }

        .quick-actions a i {
            width: 20px;
            text-align: center;
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            body {
                flex-direction: column;
            }
            .sidebar {
                width: 100%;
                height: auto;
            }
            .main-content {
                padding: 20px;
            }
            .dashboard-menu {
                grid-template-columns: 1fr;
            }
            .quick-actions ul {
                grid-template-columns: 1fr;
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<!-- Sidebar Navigation -->
<div class="sidebar">
    <div class="logo">
        <h1>PAHANA EDU</h1>
        <p>STAFF PORTAL</p>
    </div>

    <div class="nav-rhombus">
        <h2>Staff Menu</h2>
        <ul class="nav-menu">
            <li><a href="#" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
            <li><a href="<%= request.getContextPath() %>/customer-management"><i class="fas fa-users"></i> Customers</a></li>
            <li><a href="<%= request.getContextPath() %>/book-management"><i class="fas fa-book"></i> Inventory</a></li>
            <li><a href="<%= request.getContextPath() %>/bill-create"><i class="fas fa-receipt"></i> Billing</a></li>
            <li><a href="<%= request.getContextPath() %>/help.jsp"><i class="fas fa-question-circle"></i> Help</a></li>
        </ul>
    </div>
</div>

<!-- Main Content Area -->
<div class="main-content">
    <div class="header">
        <h1>Pahana Edu - Staff Dashboard</h1>
        <div class="user-info">
            <span>Welcome, <%= user.getFullName() %></span>
            <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Logout</a>
        </div>
    </div>

    <div class="container">
        <h2>Daily Operations</h2>

        <div class="dashboard-menu">
            <div class="menu-card">
                <h3>Customer Management</h3>
                <p>View and manage customer accounts and information</p>
                <a href="<%= request.getContextPath() %>/customer-management">Go to Customer Management</a>
            </div>

            <div class="menu-card">
                <h3>Book Management</h3>
                <p>Add, edit, and manage book inventory and stock</p>
                <a href="<%= request.getContextPath() %>/book-management">Go to Inventory Management</a>
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

        <div class="quick-actions">
            <h3>Quick Actions</h3>
            <ul>
                <li><a href="<%= request.getContextPath() %>/bill-create"><i class="fas fa-file-invoice-dollar"></i> Create New Bill</a></li>
                <li><a href="<%= request.getContextPath() %>/customer-management"><i class="fas fa-user-plus"></i> Add New Customer</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
