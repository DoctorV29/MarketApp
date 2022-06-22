<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/styles/itemInfo/styleInfo.css">

</head>

<body>
<section>
<div class="card">
<h2><center>${name}  ${weight} ${EI}</center></h2>
<br>
<h3>Состав: </h3><p class="composition">${composition}</p>

<h3>Категория: </h3><p class="categoryId">${categoryId}</p>

<h3>Вес: </h3><p class="weight">${weight} ${EI}</p>

<h3>Цена: </h3><p class="price">${price} руб.</p>

<h3>Смотри похожие товары (+- ${priceDelta} руб.):</h3>
<ul>
<c:forEach items="${similarProducts}" var="simproduct">
	<li> <a href="/market/item?itemId=${simproduct.id}">${simproduct.name}, ${simproduct.price} руб.</a></li>
</c:forEach>
</ul>
<hr>

<a class="home" href="/market"><center>Домой</center></a>
</div>
</section>
</body>
</html>