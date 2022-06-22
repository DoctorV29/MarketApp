<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <li><a href="/market/categories">Категории</a></li>
        <li><a href="/market">Маркет</a></li>
      </ul>
    </div>
    <!--end nav-->
  </div>
  <div class="page-headline">Добавление товара</div>
  <div id="main">
  <div id="content">
  <div class="post_box">
  <table>
    <form method="post" action="">
        <tr>
           <td> <label for="id">ID товара</label> </td>
           <td> <input type="text" name="id" id="id" value="${id}" class="inputProduct"/></td>
           <th rowspan="5">
               <ul>
               <c:forEach var="error" items="${resultChecking}">
                    <li style="color:Red">${error}</li>
               </c:forEach>
               </ul>
           </th>
        </tr>
        <tr>
           <td> <label for="name">Название</label> </td>
           <td> <input type="text" name="name" id="name" value="${name}" class="inputProduct"/></td>
        </tr>
        <tr>
           <td> <label for="kol">Количество</label> </td>
           <td> <input type="number" name="kol" id="kol" value="${kol}" class="inputProduct"/> </td>
        </tr>
        <tr>
           <td> <label for="EI">Единица измерения</label> </td>
           <td>
                 <select name="EI" id="EI" class="selectProduct">
                            <option value="0">Выберите единицу измерения</option>
                           <c:forEach var="item" items="${eiList}">
                                <option value="${item}" ${item == ei ? 'selected' : ''}>${item}</option>
                           </c:forEach>
                    </select>
             </td>
        </tr>
        <tr>
            <td> <label for="categoryId">Категория</label> </td>
            <td>
            <select name="categoryId" id="categoryId" class="selectProduct">
                    <option value="0">Выберите категорию</option>
                   <c:forEach var="item" items="${listCategories}">
                        <option value="${item.id}" ${item.id == categoryId ? 'selected' : ''}>${item.name}</option>
                   </c:forEach>
            </select>
             </td>
        </tr>
        <tr>
            <td> <label for="weight">Вес</label> </td>
            <td> <input type="number" name="weight" id="weight" value="${weight}" class="inputProduct" min  =0 step =0.01> </td>
            <th rowspan="3" align="center" style="vertical-align:center;">${elementImg}</th>
        </tr>
        <tr>
            <td> <label for="price">Цена</label> </td>
            <td> <input type="number" name="price" id="price" value="${price}" class="inputProduct" min  =0 step =0.01> </td>
        </tr>
        <tr>
            <td> <label for="composition">Состав</label> </td>
            <td> <textarea rows="5" cols="20" size="100"  name="composition" id="composition" class="selectProduct">${composition}</textarea> </td>
        </tr>
        <tr>
            <td> <input type="submit" name="add" value="Добавить"> </td>
            <td> <label>${result}</label> </td>
        </tr>
    </form>
    </table>
    </div>
    </div>
    </div>
    <hr>
    <a href="/market"><center>Домой</center></a>
</body>
</html>