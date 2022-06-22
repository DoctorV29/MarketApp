<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
<title>MarketApp | Категории</title>
<meta charset="utf-8">
<link type="text/css" rel="stylesheet" href="/styles/allCategories/style.css" />
</head>
<body class="page">
<div id="wrap">
  <div id="header"> <a href="/market"> <img src="/styles/allCategories/images/logo_marketApp.png"/> </a>
    <div id="nav">
      <ul class="menu">
        <li><a href="/market">Маркет</a></li>
        <li><a href="/market/service">Сервис</a></li>
      </ul>
    </div>
    <!--end nav-->
  </div>
  <!--end header-->
  <div class="page-headline">Категории</div>
  <div id="main">
    <div id="porfolio-content">
      <div class="portfolio-item first"> <a href="/market/productsByCategory?categoryId=pastry"><img class="conditer"/></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item"> <a href="/market/productsByCategory?categoryId=milk"><img class="milk"></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item"> <a href="/market/productsByCategory?categoryId=chai"><img class="tea"></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item first"> <a href="/market/productsByCategory?categoryId=coffee"><img class="coffe"/></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item"> <a href="/market/productsByCategory?categoryId=meatCanned"><img class="beercons"></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item"> <a href="/market/productsByCategory?categoryId=pasta"><img class="makar"></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item first"> <a href="/market/productsByCategory?categoryId=rice"><img class="ris"/></a> </div>
      <!--end portfolio-item-->
      <div class="portfolio-item"> <a href="/market/productsByCategory?categoryId=sunflowerOil"><img class="oil"></a> </div>
      <!--end portfolio-item-->
    </div>
    <!--portfolio-content-->
  </div>
  <!--end main-->
</div>
<!--end wrap-->
</body>
<!--end cache-images-->
</html>