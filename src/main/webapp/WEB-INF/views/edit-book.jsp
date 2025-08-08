<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 6:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedubookshop.model.Book" %>
<%
    Book book = (Book) request.getAttribute("book");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Book</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        input[type="text"], input[type="number"] {
            width: 300px;
            padding: 6px;
        }
        input[type="submit"] {
            padding: 8px 16px;
        }
    </style>
</head>
<body>

<h2>Edit Book</h2>

<% if (book != null) { %>
<form action="book-management" method="post">
    <input type="hidden" name="action" value="update" />
    <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />

    <label>Title:</label><br/>
    <input type="text" name="title" value="<%= book.getTitle() %>" required /><br/><br/>

    <label>Author:</label><br/>
    <input type="text" name="author" value="<%= book.getAuthor() %>" /><br/><br/>

    <label>ISBN:</label><br/>
    <input type="text" name="isbn" value="<%= book.getIsbn() %>" /><br/><br/>

    <label>Price:</label><br/>
    <input type="number" name="price" step="0.01" value="<%= book.getPrice() %>" required /><br/><br/>

    <label>Stock:</label><br/>
    <input type="number" name="stock" value="<%= book.getStock() %>" required /><br/><br/>

    <input type="submit" value="Update Book" />
    <a href="book-management" style="margin-left: 10px;">Cancel</a>
</form>
<% } else { %>
<p>Book not found.</p>
<% } %>

</body>
</html>
