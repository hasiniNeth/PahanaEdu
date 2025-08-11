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

        /* Sidebar Navigation */
        .sidebar {
            width: 280px;
            background: linear-gradient(135deg, var(--dark-bg), #1a1a2e);
            color: var(--light-text);
            padding: 2rem 0;
            position: relative;
            z-index: 10;
            box-shadow: 4px 0 15px rgba(0, 0, 0, 0.1);
            transition: var(--transition);
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
            letter-spacing: 1px;
        }

        .nav-menu {
            list-style: none;
        }

        .nav-menu li {
            margin-bottom: 0.8rem;
            position: relative;
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
            box-shadow: 0 4px 12px rgba(67, 97, 238, 0.3);
        }

        .nav-menu li a.active:hover {
            transform: none;
        }

        /* Main Content Area */
        .main-content {
            flex: 1;
            padding: 2rem;
            background-color: var(--light-bg);
            overflow-y: auto;
        }

        .header {
            background: white;
            color: var(--text-color);
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

        .user-info span {
            color: var(--text-color);
            font-weight: 500;
        }

        .logout-btn {
            background: var(--accent-color);
            color: white;
            border: none;
            padding: 0.6rem 1.2rem;
            border-radius: 6px;
            cursor: pointer;
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
            box-shadow: 0 4px 8px rgba(247, 37, 133, 0.3);
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
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
            border: 1px solid rgba(0, 0, 0, 0.05);
            position: relative;
            overflow: hidden;
        }

        .menu-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 4px;
            height: 100%;
            background: var(--primary-color);
            transition: var(--transition);
        }

        .menu-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }

        .menu-card:hover::before {
            width: 6px;
            background: var(--accent-color);
        }

        .menu-card h3 {
            margin-top: 0;
            color: var(--primary-color);
            font-size: 1.2rem;
            margin-bottom: 0.8rem;
            font-weight: 600;
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

        .menu-card a::after {
            content: '→';
            transition: var(--transition);
        }

        .menu-card a:hover::after {
            transform: translateX(3px);
        }

        /* Responsive adjustments */
        @media (max-width: 992px) {
            .dashboard-menu {
                grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
            }
        }

        @media (max-width: 768px) {
            body {
                flex-direction: column;
            }
            .sidebar {
                width: 100%;
                padding: 1rem 0;
            }
            .nav-menu {
                display: flex;
                overflow-x: auto;
                padding-bottom: 1rem;
            }
            .nav-menu li {
                flex: 0 0 auto;
                margin-bottom: 0;
                margin-right: 0.5rem;
            }
            .main-content {
                padding: 1.5rem;
            }
            .header {
                flex-direction: column;
                align-items: flex-start;
                gap: 1rem;
            }
        }

        @media (max-width: 576px) {
            .dashboard-menu {
                grid-template-columns: 1fr;
            }
            .menu-card {
                padding: 1.5rem;
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<!-- Sidebar Navigation -->
<div class="sidebar">
    <div class="logo">
        <h1>PAHANA EDU</h1>
        <p>BOOKSHOP MANAGEMENT</p>
    </div>

    <div class="nav-rhombus">
        <h2>Admin Menu</h2>
        <ul class="nav-menu">
            <li><a href="#" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
            <li><a href="customer-management"><i class="fas fa-users"></i> Customer Management</a></li>
            <li><a href="staff-management"><i class="fas fa-user-tie"></i> Staff Management</a></li>
            <li><a href="book-management"><i class="fas fa-book"></i> Book Management</a></li>
        </ul>
    </div>
</div>

<!-- Main Content Area -->
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
                <a href="<%= request.getContextPath() %>/customer-management">
                    Go to Customer Management
                </a>
            </div>

            <div class="menu-card">
                <h3>Staff Management</h3>
                <p>Add, edit, or remove staff accounts</p>
                <a href="<%= request.getContextPath() %>/staff-management">
                    Go to Staff Management
                </a>
            </div>

            <div class="menu-card">
                <h3>Inventory Management</h3>
                <p>Add, edit, and manage book inventory</p>
                <a href="<%= request.getContextPath() %>/book-management">
                    Go to Book Management
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>