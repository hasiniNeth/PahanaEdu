<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 6:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahanaedubookshop.model.Book" %>
<%
    Book book = (Book) request.getAttribute("book");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Book</title>
</head>
<body>
<h2>Edit Book</h2>

<form action="book-management" method="post">
    <input type="hidden" name="action" value="update" />
    <input type="hidden" name="id" value="<%= book.getBookId() %>" />

    Title: <input type="text" name="title" value="<%= book.getTitle() %>" required /><br/><br/>
    Author: <input type="text" name="author" value="<%= book.getAuthor() %>" /><br/><br/>
    ISBN: <input type="text" name="isbn" value="<%= book.getIsbn() %>" /><br/><br/>
    Price: <input type="number" name="price" step="0.01" value="<%= book.getPrice() %>" required /><br/><br/>

    <input type="submit" value="Update Book" />
</form>

<br/>
<a href="book-management">Back to Book List</a>
</body>
</html>

