<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.Book" %>
<%
    List<Book> bookList = (List<Book>) request.getAttribute("bookList");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    String role = (String) session.getAttribute("role");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Book Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-section { margin-bottom: 30px; background: #f9f9f9; padding: 20px; border: 1px solid #ddd; }
        .success { color: green; }
        .error { color: red; }
    </style>
</head>
<body>

<h2>Book Management</h2>

<% if (message != null) { %>
<p class="success"><%= message %></p>
<% } %>
<% if (error != null) { %>
<p class="error"><%= error %></p>
<% } %>

<!-- Add Book Form -->
<div class="form-section">
    <h3>Add New Book</h3>
    <form method="post" action="book-management">
        <input type="hidden" name="action" value="add" />
        Title: <input type="text" name="title" required /><br/><br/>
        Author: <input type="text" name="author" /><br/><br/>
        ISBN: <input type="text" name="isbn" /><br/><br/>
        Price: <input type="number" name="price" step="0.01" required /><br/><br/>
        <input type="submit" value="Add Book" />
    </form>
</div>

<!-- Book List Table -->
<h3>Available Books</h3>
<% if (bookList == null || bookList.isEmpty()) { %>
<p>No books found.</p>
<% } else { %>
<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>ISBN</th>
        <th>Price</th>
        <th>Actions</th>
    </tr>
    <% for (Book book : bookList) { %>
    <tr>
        <td><%= book.getBookId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getIsbn() %></td>
        <td><%= String.format("%.2f", book.getPrice()) %></td>
        <td>
            <a href="book-management?action=edit&id=<%= book.getBookId() %>">Edit</a>
            <% if ("admin".equals(role)) { %>
            |
            <form method="post" action="book-management" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this book?');">
                <input type="hidden" name="action" value="delete" />
                <input type="hidden" name="id" value="<%= book.getBookId() %>" />
                <input type="submit" value="Delete" />
            </form>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
<% } %>

</body>
</html>
