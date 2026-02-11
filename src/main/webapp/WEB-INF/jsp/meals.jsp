<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            border: 1px solid black;
            border-collapse: collapse;
            padding: 5px;
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
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
            <td>${meal.description}"</td>
            <td>${meal.calories}"</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
