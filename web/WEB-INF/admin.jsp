<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NoteKeeper</title>
    </head>
    <body>
        <h1>NoteKeeper</h1>
        <h2>Admin Control Panel</h2>
        <p>
            Welcome ${username}<br>
            <a href=" <c:url value='/login' /> ">Log out</a>
        </p>
        <%-- View all users --%>
        <h2>Manage Users</h2>
        <table cellpadding="5" border="1">
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Role</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.role.roleName}</td>
                    <td>
                        <form action="admin" method="get">
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="view">
                            <input type="hidden" name="selectedUsername" value="${user.username}">
                        </form>
                    </td>
                    <td>
                        <form action="admin" method="post" >
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedUsername" value="${user.username}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <%-- forms for adding or editing a user --%>
        <c:if test="${selectedUser == null}">
            <h3>Add User</h3>
            <form action="admin" method="POST">
                username: <input type="text" name="username"><br>
                password: <input type="password" name="password"><br>
                first name: <input type="text" name="firstname"><br>
                last name: <input type="text" name="lastname"><br>
                email: <input type="email" name="email"><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
            </form>
        </c:if>
        <c:if test="${selectedUser != null}">
            <h3>Edit User</h3>
            <form action="admin" method="POST">
                username: <input type="text" name="username" value="${selectedUser.username}" readonly><br>
                password: <input type="password" name="password" value="${selectedUser.password}"><br>
                first name: <input type="text" name="firstname" value="${selectedUser.firstName}"><br>
                last name: <input type="text" name="lastname" value="${selectedUser.lastName}"><br>
                email: <input type="email" name="email" value="${selectedUser.email}"><br>
                <input type="hidden" name="action" value="edit">
                <input type="submit" value="Save">
            </form>
        </c:if>
    </body>
</html>
