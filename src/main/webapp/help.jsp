<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/11/2025
  Time: 12:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedubookshop.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Pahana Edu</title>
    <style>
        :root {
            --primary-color: #4361ee;
            --secondary-color: #3a0ca3;
            --accent-color: #f72585;
            --light-bg: #f8f9fa;
            --text-color: #2b2d42;
            --card-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        body {
            font-family: 'Poppins', sans-serif;
            background: var(--light-bg);
            margin: 0;
            padding: 0;
            color: var(--text-color);
        }
        .header {
            background: var(--primary-color);
            padding: 1rem 2rem;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: var(--card-shadow);
        }
        .container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 2rem;
            background: white;
            border-radius: 8px;
            box-shadow: var(--card-shadow);
        }
        h1 {
            color: var(--primary-color);
            font-size: 1.8rem;
            margin-bottom: 1rem;
        }
        h2 {
            color: var(--secondary-color);
            margin-top: 1.5rem;
        }
        ul {
            margin-left: 1.5rem;
        }
        .back-link {
            display: inline-block;
            margin-top: 2rem;
            padding: 0.6rem 1.2rem;
            background: var(--accent-color);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: background 0.3s ease;
        }
        .back-link:hover {
            background: #d91a6b;
        }
    </style>
</head>
<body>

<div class="header">
    <h2>Pahana Edu - Help Section</h2>
    <span>Welcome, <%= user.getFullName() %></span>
</div>

<div class="container">
    <h1>System Usage Guidelines</h1>
    <p>Welcome to the Pahana Edu Bookshop Management System! This guide will help you get started and use the system efficiently.</p>

    <h2>Logging In</h2>
    <ul>
        <li>Go to the login page and enter your username and password.</li>
        <li>If you forget your password, contact your system administrator.</li>
    </ul>

    <h2>Navigation</h2>
    <ul>
        <li>Use the sidebar menu to access different sections like Customers, Staff, Books, and Billing.</li>
        <li>Admins will see all options; staff will see only relevant ones.</li>
    </ul>

    <h2>Customer Management</h2>
    <ul>
        <li>Add, edit, and view customer details.</li>
        <li>Only admins can delete customer accounts.</li>
    </ul>

    <h2>Staff Management</h2>
    <ul>
        <li>Only admins can add, edit, or delete staff members.</li>
    </ul>

    <h2>Book Management</h2>
    <ul>
        <li>Add new books with title, author, ISBN, and price in LKR.</li>
        <li>Edit existing books and update prices or stock.</li>
    </ul>

    <h2>Billing</h2>
    <ul>
        <li>Create a bill by entering the customer account number.</li>
        <li>Add books to the bill, specify quantities, and print receipts.</li>
        <li>Stock updates automatically when a bill is completed.</li>
    </ul>

    <h2>Logging Out</h2>
    <ul>
        <li>Click the "Logout" button at the top right to securely exit the system.</li>
    </ul>

    <a href="<%= request.getContextPath() %>/admin-dashboard.jsp" class="back-link">← Back to Dashboard</a>
</div>

</body>
</html>
