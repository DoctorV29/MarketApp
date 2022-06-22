<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
<title>MarketApp</title>
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
  <div class="page-headline"></div>
  <div id="main">
  <div id="content">
  <div class="post_box">
    <div class="text"><h2>К сожалению все продукты закончились. Добавьте новые.</h2></div>
  </div>
  </div>
  </div>
</div>
</body>
</html>