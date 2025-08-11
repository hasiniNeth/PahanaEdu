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
        :root {
            --primary-color: #4285F4;
            --secondary-color: #34A853;
            --accent-color: #EA4335;
            --light-bg: #F8F9FA;
            --dark-text: #202124;
            --light-text: #FFFFFF;
            --border-color: #DADCE0;
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
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
            line-height: 1.6;
        }

        .container {
            width: 100%;
            max-width: 500px;
            background: var(--light-text);
            padding: 40px;
            border-radius: 10px;
            box-shadow: var(--card-shadow);
            animation: fadeIn 0.5s ease-out;
        }

        h2 {
            color: var(--primary-color);
            margin-bottom: 25px;
            font-weight: 500;
            text-align: center;
            padding-bottom: 10px;
            border-bottom: 2px solid var(--primary-color);
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

        input[type="password"]::placeholder {
            color: #999;
            font-style: italic;
        }

        .form-actions {
            margin-top: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .btn-submit {
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

        .btn-submit:hover {
            background-color: #3367D6;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .btn-back {
            display: inline-flex;
            align-items: center;
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-back:hover {
            color: #3367D6;
            text-decoration: underline;
        }

        .btn-back::before {
            content: "←";
            margin-right: 5px;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Responsive adjustments */
        @media (max-width: 600px) {
            .container {
                padding: 30px;
            }

            .form-actions {
                flex-direction: column;
                gap: 15px;
            }

            .btn-submit {
                width: 100%;
            }

            .btn-back {
                align-self: flex-start;
            }
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Edit Staff Member</h2>

    <form method="post" action="staff-management">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="id" value="<%= staff.getUserId() %>" />

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="<%= staff.getUsername() %>" required />
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Leave blank to keep current password" />
        </div>

        <div class="form-group">
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" value="<%= staff.getFullName() %>" required />
        </div>

        <div class="form-actions">
            <input type="submit" class="btn-submit" value="Update Staff" />
            <a href="staff-management" class="btn-back">Back to Staff Management</a>
        </div>
    </form>
</div>
</body>
</html>