<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
<title>MarketApp | Сервис</title>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="/styles/allCategories/style.css"/>
</head>
<body class="page">
<div id="wrap">
  <div id="header"> <a href="/market"> <img src="/styles/allCategories/images/logo_marketApp.png"/> </a>
    <div id="nav">
      <ul class="menu">
        <li><a href="/market/item/create">Добавить продукт</a></li>
        <li><a href="/market/categories">Категории</a></li>
        <li><a href="/market">Маркет</a></li>
      </ul>
    </div>
    <!--end nav-->
  </div>
  <div class="page-headline">Сервис</div>
  <div id="main">
  <div id="content">
  <div class="post_box">
    <c:forEach items="${allProducts}" var="product">
      <img src="/img/${product.id}.jpg" width="200px" height="200px"/>
      <div class="text"> <h3> <a href="#">${product.name}</a></h3> <br>
        Состав: ${product.composition} <br> </div>
        <br>
        <div class="btn_more float_r">
          <table>
          <td> <button class="button button2" onclick="document.location='item/edit?id=${product.id}'">Редактировать</button> </td>
          <form method="POST" action="">
              <td> <input name="id" value="${product.id}" hidden>
              <button class="button button3" onclick="alert('Продукт удален!')">Удалить</button> </td>
              </table>
          </form>
        </div>
        <br>
        <div class="btn_more float_l"><b>Цена: ${product.price} руб.</b></div>
        <br>
        <hr>
      </c:forEach>
  </div>
  </div>
  </div>
</div>
</body>
</html>