<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Customer Management</title>
  <style>
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
    .form-container { background: #f5f5f5; padding: 20px; margin-bottom: 20px; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; font-weight: bold; }
    input[type="text"], textarea {
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
<h1>Customer Management</h1>

<c:if test="${not empty error}">
  <div style="color: red; padding: 10px; background: #ffeeee;">${error}</div>
</c:if>

<!-- Add Customer Form -->
<div class="form-container">
  <h2>Add New Customer</h2>
  <form action="customer-management" method="post">
    <input type="hidden" name="action" value="add">
    <div class="form-group">
      <label>Account Number:</label>
      <input type="text" name="accountNumber" required>
    </div>
    <div class="form-group">
      <label>Name:</label>
      <input type="text" name="name" required>
    </div>
    <div class="form-group">
      <label>Address:</label>
      <textarea name="address" rows="3" required></textarea>
    </div>
    <div class="form-group">
      <label>Telephone:</label>
      <input type="text" name="telephone" required>
    </div>
    <button type="submit">Add Customer</button>
  </form>
</div>

<!-- Customer List -->
<h2>Customer List</h2>
<table>
  <thead>
  <tr>
    <th>Account Number</th>
    <th>Name</th>
    <th>Address</th>
    <th>Telephone</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${customers}" var="customer">
    <tr>
      <td>${customer.accountNumber}</td>
      <td>${customer.name}</td>
      <td>${customer.address}</td>
      <td>${customer.telephone}</td>
      <td class="action-btns">
        <!-- Edit Button -->
        <button onclick="showEditForm('${customer.accountNumber}')">Edit</button>

        <!-- Delete Button (Admin only) -->
        <c:if test="${sessionScope.role == 'admin'}">
          <form action="customer-management" method="post" style="display: inline;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
            <button type="submit" class="delete">Delete</button>
          </form>
        </c:if>
      </td>
    </tr>

    <!-- Edit Form (hidden by default) -->
    <tr id="edit-${customer.accountNumber}" style="display: none;">
      <td colspan="5">
        <form action="customer-management" method="post">
          <input type="hidden" name="action" value="edit">
          <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
          <div class="form-group">
            <label>Name:</label>
            <input type="text" name="name" value="${customer.name}" required>
          </div>
          <div class="form-group">
            <label>Address:</label>
            <textarea name="address" rows="3" required>${customer.address}</textarea>
          </div>
          <div class="form-group">
            <label>Telephone:</label>
            <input type="text" name="telephone" value="${customer.telephone}" required>
          </div>
          <button type="submit">Save Changes</button>
          <button type="button" onclick="hideEditForm('${customer.accountNumber}')">Cancel</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<script>
  function showEditForm(accountNumber) {
    document.getElementById('edit-' + accountNumber).style.display = 'table-row';
  }

  function hideEditForm(accountNumber) {
    document.getElementById('edit-' + accountNumber).style.display = 'none';
  }
</script>
</body>
</html>