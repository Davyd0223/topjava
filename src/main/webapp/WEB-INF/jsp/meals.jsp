<%--
  Created by IntelliJ IDEA.
  User: homaz
  Date: 10.02.2026
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <style>
        .excess {
            background-color: red
        }

        .normal {
            background-color: green
        }

        table, th, td {
            border: 1px solid black; border-collapse: collapse; padding: 5px;
        }
    </style>
    <title>
        <h2>Меню на день</h2>
    </title>
</head>
<body>
<table>
    <thead>
        <tr>
            <th>Время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
    </thead>
    <tbody>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${meals}">
            <c:choose>
                <c:when test="${meal.excess}">
                    <tr class="excess">
                </c:when>
                <c:otherwise>
                    <tr class="normal">
                </c:otherwise>
            </c:choose>
                    <td>
                        <c:out value="${fn:substring(meal.dateTime.toString(), 0, fn:indexOf(meal.dateTime.toString(), 'T'))}"/>
                    </td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.calories}"/></td>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
