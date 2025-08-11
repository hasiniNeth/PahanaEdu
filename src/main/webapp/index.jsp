<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu Bookshop - Home</title>
    <style>
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
            --accent-color: #e74c3c;
            --light-bg: #f5f7fa;
            --dark-text: #2c3e50;
            --light-text: #ecf0f1;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: var(--light-bg);
            color: var(--dark-text);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            background-image: linear-gradient(rgba(255,255,255,0.9), rgba(255,255,255,0.9)),
            url('https://images.unsplash.com/photo-1507842217343-583bb7270b66?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
        }

        .container {
            max-width: 800px;
            padding: 2rem;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        h2 {
            font-size: 2.5rem;
            margin-bottom: 2rem;
            color: var(--primary-color);
            text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
        }

        .login-btn {
            display: inline-block;
            padding: 12px 30px;
            background-color: var(--secondary-color);
            color: white;
            text-decoration: none;
            border-radius: 30px;
            font-weight: 600;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border: 2px solid var(--secondary-color);
        }

        .login-btn:hover {
            background-color: transparent;
            color: var(--secondary-color);
            transform: translateY(-3px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .login-btn:active {
            transform: translateY(1px);
        }

        footer {
            margin-top: 3rem;
            color: var(--dark-text);
            font-size: 0.9rem;
            opacity: 0.8;
        }

        @media (max-width: 600px) {
            h2 {
                font-size: 2rem;
            }

            .container {
                padding: 1.5rem;
                width: 90%;
            }

            .login-btn {
                padding: 10px 25px;
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
    <h2>Welcome to Pahana Edu Bookshop</h2>
    <a href="login.jsp" class="login-btn">
        <i class="fas fa-sign-in-alt"></i> Login
    </a>
</div>
<footer>
    &copy; 2023 Pahana Edu Bookshop. All rights reserved.
</footer>
</body>
</html>