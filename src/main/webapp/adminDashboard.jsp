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
    <title>Admin Dashboard</title>
</head>
<body>
<h1>Welcome, <%= user.getFullName() %> (Admin)</h1>

<h2>Admin Menu</h2>
<ul>
    <li><a href="../customerManagement.jsp">Manage Customers</a></li>
    <li><a href="../itemManagement.jsp">Manage Items</a></li>
    <li><a href="../userManagement.jsp">Manage Users</a></li>
</ul>

<a href="../logout">Logout</a>
</body>
</html>