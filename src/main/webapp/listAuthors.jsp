<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <h1>Author List</h1>
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Author Name</th>
                <th align="right" class="tableHead">Date Added</th>
                <th align="left" class="tableHead">Edit?</th>
                <th align="left" class="tableHead">Delete?</th>
            </tr>
        <c:forEach var="a" items="${authors}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td align="left">${a.authorId}</td>
            <td align="left">${a.authorName}</td>
            <td align="right">
                <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
            </td>
            <td align="left"><a href="AuthorController?action=updateRedirect&id=${a.authorId}">edit?</a></td>
            <td align="left"><a href="AuthorController?action=delete&id=${a.authorId}">delete?</a></td>
        </tr>
        </c:forEach>
        </table>
        <h2>Add New Author?</h2><br>
        <form id="AuthorEntry" name="form1" method="POST" action="AuthorController?action=add">
            <p style="font-weight: bold">
                Name
                <input type="text" name="newName" form ="AuthorEntry" required autofocus>
            </p>
            <input type="submit" value="Add Author">
        </form>
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>
