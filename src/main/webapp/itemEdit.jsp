<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
<title>MarketApp</title>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="/styles/allCategories/style.css"/>
  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
  <meta http-equiv="Pragma" content="no-cache" />
  <meta http-equiv="Expires" content="-1" />

  <%
  response.setHeader("Cache-Control","no-cache");
  response.setHeader("Pragma","no-cache");
  response.setDateHeader ("Expires", -1);
  %>

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
  <div class="page-headline">Обновление товара</div>
  <div id="main">
  <div id="content">
  <div class="post_box">
  <center><h2>${name}</h2></center>

      <div style="display: inline-block;">
        <form method="post" action="" enctype="multipart/form-data">

            <div style="display:table-row;">
                <div style="display:table-cell;"> <label for="name">Название</label> </div>
                <div style="display:table-cell;"> <input type="text" name="name" id="name" value="${name}" class="inputProduct"/></div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell;"> <label for="kol">Количество</label>  </div>
                <div style="display:table-cell;"> <input type="number" name="kol" id="kol" value="${kol}" class="inputProduct"/></div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell;"> <label for="EI">Единица измерения</label> </div>
                <div style="display:table-cell;">
                    <select name="EI" id="EI" class="selectProduct">
                        <option value="0">Выберите единицу измерения</option>
                        <c:forEach var="item" items="${eiList}">
                            <option value="${item}" ${item == ei ? 'selected' : ''}>${item}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell;"> <label for="categoryId">Категория</label>   </div>
                <div style="display:table-cell;">
                    <select name="categoryId" id="categoryId" class="selectProduct">
                        <option value="0">Выберите категорию</option>
                        <c:forEach var="item" items="${listCategories}">
                            <option value="${item.id}" ${item.id == categoryId ? 'selected' : ''}>${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell;"> <label for="weight">Вес</label> </div>
                <div style="display:table-cell;"> <input type="number" name="weight" id="weight" value="${weight}" class="inputProduct" min  =0 step =0.01> </div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell;"> <label for="price">Цена</label> </div>
                <div style="display:table-cell;"> <input type="number" name="price" id="price" value="${price}" class="inputProduct" min  =0 step =0.01>  </div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell; vertical-align: top;"> <label for="composition">Состав</label> </div>
                <div style="display:table-cell;"> <textarea rows="5" cols="20" size="100"  name="composition" id="composition">${composition}</textarea> </div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell; vertical-align: top;"> <label for="uploadFile">Можно выбрать другую картинку</label> </div>
                <div style="display:table-cell;"> <input type="file" name="uploadFile"> </div>
            </div>
            <div style="display:table-row;">
                <div style="display:table-cell;"> <input class="button button2" type="submit" name="add" value="Обновить"> </div>
            </div>
        </form>
      </div>
      <div style="display: inline-block; vertical-align: top;">
          <!--<img src="${filePath}" width="200px" height="200px"/><br>-->
          <img src="data:image/png;base64,${imageBase}" width="200px" height="200px"/><br>
          <label>${result}</label>
      </div>
      <div>
          <ul>
              <c:forEach var="error" items="${resultChecking}">
                  <li style="color:#ff0000">${error}</li>
              </c:forEach>
          </ul>
      </div>
    </div>
    </div>
    </div>
    </div>
    <hr>
    <a href="/market/service"><center>Домой</center></a>
</body>
</html>