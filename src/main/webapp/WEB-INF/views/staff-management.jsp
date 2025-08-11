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
        :root {
            --primary-color: #4285F4;
            --secondary-color: #34A853;
            --accent-color: #EA4335;
            --light-bg: #F8F9FA;
            --dark-text: #202124;
            --light-text: #FFFFFF;
            --border-color: #DADCE0;
            --success-color: #0F9D58;
            --error-color: #DB4437;
            --card-shadow: 0 1px 2px 0 rgba(60,64,67,0.3), 0 2px 6px 2px rgba(60,64,67,0.15);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Roboto', 'Arial', sans-serif;
        }

        body {
            background-color: var(--light-bg);
            color: var(--dark-text);
            padding: 30px;
            line-height: 1.6;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        h2 {
            color: var(--primary-color);
            margin-bottom: 25px;
            font-weight: 500;
            padding-bottom: 10px;
            border-bottom: 2px solid var(--primary-color);
        }

        .message {
            color: var(--light-text);
            background-color: var(--success-color);
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 25px;
            display: inline-block;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .form-section, .table-section {
            background: var(--light-text);
            padding: 30px;
            border-radius: 8px;
            margin-bottom: 30px;
            box-shadow: var(--card-shadow);
        }

        h3 {
            color: var(--dark-text);
            margin-bottom: 20px;
            font-weight: 500;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--dark-text);
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 2px rgba(66,133,244,0.2);
        }

        .form-actions {
            margin-top: 25px;
        }

        input[type="submit"] {
            background-color: var(--primary-color);
            color: var(--light-text);
            border: none;
            padding: 12px 25px;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #3367D6;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            overflow: hidden;
            border-radius: 8px;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid var(--border-color);
        }

        th {
            background-color: var(--primary-color);
            color: var(--light-text);
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
        }

        tr:nth-child(even) {
            background-color: rgba(66,133,244,0.05);
        }

        tr:hover {
            background-color: rgba(66,133,244,0.1);
        }

        .action-btn {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
            transition: all 0.2s ease;
            margin-right: 15px;
        }

        .action-btn:hover {
            text-decoration: underline;
            color: #3367D6;
        }

        .btn-delete {
            background-color: transparent;
            color: var(--accent-color);
            border: none;
            font-weight: 500;
            cursor: pointer;
            padding: 0;
            font-size: 1rem;
        }

        .btn-delete:hover {
            text-decoration: underline;
        }

        .empty-state {
            text-align: center;
            padding: 40px;
            color: var(--dark-text);
            opacity: 0.7;
            font-style: italic;
        }

        .action-separator {
            color: var(--border-color);
            margin: 0 10px;
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            body {
                padding: 20px;
            }

            .form-section, .table-section {
                padding: 20px;
            }

            table {
                display: block;
                overflow-x: auto;
            }
        }

        /* Animation for table rows */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        tr {
            animation: fadeIn 0.3s ease forwards;
        }

        tr:nth-child(1) { animation-delay: 0.1s; }
        tr:nth-child(2) { animation-delay: 0.2s; }
        tr:nth-child(3) { animation-delay: 0.3s; }
        tr:nth-child(4) { animation-delay: 0.4s; }
        tr:nth-child(5) { animation-delay: 0.5s; }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Staff Management</h2>

    <% if (message != null) { %>
    <p class="message"><%= message %></p>
    <% } %>

    <div class="form-section">
        <h3>Add New Staff</h3>
        <form method="post" action="staff-management">
            <input type="hidden" name="action" value="add" />

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required />
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required />
            </div>

            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" required />
            </div>

            <div class="form-actions">
                <input type="submit" value="Add Staff" />
            </div>
        </form>
    </div>

    <div class="table-section">
        <h3>Existing Staff</h3>
        <% if (staffList == null || staffList.isEmpty()) { %>
        <div class="empty-state">No staff members found.</div>
        <% } else { %>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Staff staff : staffList) { %>
            <tr>
                <td><%= staff.getUserId() %></td>
                <td><%= staff.getUsername() %></td>
                <td><%= staff.getFullName() %></td>
                <td>
                    <a href="staff-management?action=edit&id=<%= staff.getUserId() %>" class="action-btn">Edit</a>
                    <span class="action-separator">|</span>
                    <form method="post" action="staff-management" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this staff member?');">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="id" value="<%= staff.getUserId() %>" />
                        <button type="submit" class="btn-delete">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>
</body>
</html>