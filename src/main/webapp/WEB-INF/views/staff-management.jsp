<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 2:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Staff Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .admin-only { background-color: #fff8f8; border-left: 4px solid #ffcccc; padding: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        .form-container { background: #f5f5f5; padding: 20px; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="password"], select {
            width: 100%; padding: 8px; box-sizing: border-box;
            border: 1px solid #ddd; border-radius: 4px;
        }
        button {
            background-color: #4CAF50; color: white; padding: 10px 15px;
            border: none; border-radius: 4px; cursor: pointer;
        }
        button.delete { background-color: #f44336; }
        .action-btns { display: flex; gap: 10px; }
    </style>
</head>
<body>
<h1>Staff Management</h1>

<c:if test="${not empty message}">
    <div style="color: green; padding: 10px; background: #eeffee;">${message}</div>
</c:if>
<c:if test="${not empty error}">
    <div style="color: red; padding: 10px; background: #ffeeee;">${error}</div>
</c:if>

<!-- Only show management interface to admin -->
<c:if test="${not empty sessionScope.role and sessionScope.role eq 'admin'}">
    <!-- Add Staff Form -->
    <div class="form-container admin-only">
        <h2>Add New Staff Member</h2>
        <form action="staff-management" method="post">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label>Username:</label>
                <input type="text" name="username" required>
            </div>
            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>
            <div class="form-group">
                <label>Full Name:</label>
                <input type="text" name="fullName" required>
            </div>
            <div class="form-group">
                <label>Role:</label>
                <select name="role" required>
                    <option value="staff">Staff</option>
                    <option value="admin">Admin</option>
                </select>
            </div>
            <button type="submit">Add Staff</button>
        </form>
    </div>

    <!-- Staff List -->
    <h2>Staff Members</h2>
    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Full Name</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${staffList}" var="staff">
            <tr>
                <td>${staff.username}</td>
                <td>${staff.fullName}</td>
                <td>${staff.role}</td>
                <td class="action-btns">
                    <button onclick="showEditForm('${staff.username}')">Edit</button>
                    <form action="staff-management" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="username" value="${staff.username}">
                        <button type="submit" class="delete">Delete</button>
                    </form>
                </td>
            </tr>
            <!-- Edit Form (hidden by default) -->
            <tr id="edit-${staff.username}" style="display: none;">
                <td colspan="4">
                    <form action="staff-management" method="post">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="originalUsername" value="${staff.username}">
                        <div class="form-group">
                            <label>Username:</label>
                            <input type="text" name="username" value="${staff.username}" required>
                        </div>
                        <div class="form-group">
                            <label>Password (leave blank to keep current):</label>
                            <input type="password" name="password">
                        </div>
                        <div class="form-group">
                            <label>Full Name:</label>
                            <input type="text" name="fullName" value="${staff.fullName}" required>
                        </div>
                        <div class="form-group">
                            <label>Role:</label>
                            <select name="role" required>
                                <option value="staff" ${staff.role eq 'staff' ? 'selected' : ''}>Staff</option>
                                <option value="admin" ${staff.role eq 'admin' ? 'selected' : ''}>Admin</option>
                            </select>
                        </div>
                        <button type="submit">Save Changes</button>
                        <button type="button" onclick="hideEditForm('${staff.username}')">Cancel</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${sessionScope.role ne 'admin'}">
    <div class="admin-only">
        <h2>Access Denied</h2>
        <p>Only administrators can access staff management functions.</p>
    </div>
</c:if>

<script>
    function showEditForm(username) {
        document.getElementById('edit-' + username).style.display = 'table-row';
    }

    function hideEditForm(username) {
        document.getElementById('edit-' + username).style.display = 'none';
    }
</script>
</body>
</html>