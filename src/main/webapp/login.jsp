<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>HAL. LOGIN</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #4B5066;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background-color: #fff;
            width: 400px;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.3);
            text-align: center;
        }
        .login-container img {
            width: 80px;
            margin-bottom: 10px;
        }
        .login-container h2 {
            margin: 0;
            font-size: 24px;
            font-weight: bold;
        }
        .login-container p {
            color: #333;
            margin: 10px 0 30px;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
            position: relative;
        }
        .form-group input {
            width: 100%;
            padding: 12px 40px 12px 10px;
            box-sizing: border-box;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #e5e5e5;
        }
        .form-group i {
            position: absolute;
            right: 10px;
            top: 12px;
            color: #333;
        }
        .btn-login {
            background-color: #008000;
            color: #fff;
            border: none;
            padding: 12px;
            width: 100%;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-login:hover {
            background-color: #006400;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <img src="logo.png" alt="Logo SMA">
        <h2>SMA Tadika Mesra</h2>
        <p>Silakan login menggunakan Username dan Kata Sandi anda.</p>

        <form method="post" action="${pageContext.request.contextPath}/LoginServlet">
            <div class="form-group">
                <input type="text" name="username" placeholder="Username" required>
                <i>&#9993;</i>
            </div>
            <div class="form-group">
                <input type="password" name="password" placeholder="Kata Sandi" required>
                <i>&#128065;</i>
            </div>
            <button type="submit" class="btn-login">Login</button>
        </form>

    </div>
</body>
</html>
