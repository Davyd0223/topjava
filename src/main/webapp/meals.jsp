<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><h2>Моя еда</h2></title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Назад</a></h3>
<hr>
<h2>Моя еда</h2>
<a href="meals?action=create">Добавить</a>
<table>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>
    <%--@elvariable id="meals" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"--%>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td><fmt:formatDate value="${meal.dateTimeAsDate}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Обновить</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
