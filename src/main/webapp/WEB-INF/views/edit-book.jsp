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
            max-width: 600px;
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
            padding-bottom: 10px;
            border-bottom: 2px solid var(--primary-color);
        }

        form {
            display: grid;
            gap: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        label {
            font-weight: 500;
            color: var(--dark-text);
            font-size: 0.95rem;
        }

        input[type="text"],
        input[type="number"] {
            padding: 12px 15px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-size: 1rem;
            transition: all 0.3s ease;
            width: 100%;
        }

        input[type="text"]:focus,
        input[type="number"]:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 2px rgba(66,133,244,0.2);
        }

        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 20px;
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

        .btn-cancel {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 12px 25px;
            background-color: var(--light-bg);
            color: var(--dark-text);
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .btn-cancel:hover {
            background-color: #e8e8e8;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .not-found {
            text-align: center;
            padding: 20px;
            color: var(--dark-text);
            font-size: 1.1rem;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            .container {
                padding: 30px;
            }
        }

        @media (max-width: 480px) {
            .container {
                padding: 20px;
            }

            .form-actions {
                flex-direction: column;
                gap: 10px;
            }

            input[type="submit"],
            .btn-cancel {
                width: 100%;
            }
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Edit Book</h2>

    <% if (book != null) { %>
    <form action="book-management" method="post">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />

        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="<%= book.getTitle() %>" required />
        </div>

        <div class="form-group">
            <label for="author">Author:</label>
            <input type="text" id="author" name="author" value="<%= book.getAuthor() %>" />
        </div>

        <div class="form-group">
            <label for="isbn">ISBN:</label>
            <input type="text" id="isbn" name="isbn" value="<%= book.getIsbn() %>" />
        </div>

        <div class="form-group">
            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" value="<%= book.getPrice() %>" required />
        </div>

        <div class="form-group">
            <label for="stock">Stock:</label>
            <input type="number" id="stock" name="stock" value="<%= book.getStock() %>" required />
        </div>

        <div class="form-actions">
            <input type="submit" value="Update Book" />
            <a href="book-management" class="btn-cancel">Cancel</a>
        </div>
    </form>
    <% } else { %>
    <div class="not-found">
        <p>Book not found.</p>
        <a href="book-management" class="btn-cancel">Back to Book List</a>
    </div>
    <% } %>
</div>
</body>
</html>