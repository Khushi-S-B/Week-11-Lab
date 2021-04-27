<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NoteKeeper</title>
    </head>
    <body>
        <h1>NoteKeeper</h1>
        <h2>Login</h2>
        <form action="login" method="post">
            <label>Username:</label>
            <input type="text" name="username" value="${username}">
            <br>
            <label>Password:</label>
            <input type="password" name="password">
            <br>
            <input type="submit" value="Sign in">
        </form>
            <p>${message}</p>
            <p><a href="reset" />Reset Password</p>
    </body>
</html>
