<%-- 
    Document   : reset
    Created on : Mar 31, 2021, 4:17:56 PM
    Author     : 826535
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>

        <h1>Enter a new Password</h1>
        <form action="reset" method="post">
            <input type="text" name="nPass" value="">
             <input type="hidden" name="action" value="resetPassword">
            <input type="submit" value="submit">
        </form>
        
        <p>${message}</p>

    </body>
</html>
