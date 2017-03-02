<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Author</title>
    </head>
    <body>
        <h1>Now Editing Author ${id}</h1>

        <form method="POST" action="AuthorController?action=update&id=${id}">
            <p style="font-weight: bold">
                New Name
                <input type="text" name="newName" required autofocus>
            </p>
            <input type="submit" value="Edit Author">
        </form>
    </body>
</html>
