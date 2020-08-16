<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="ru.HomeWork.Logic.Model" %>
<%--
  Created by IntelliJ IDEA.
  User: SusPecT
  Date: 16.08.2020
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<html>
  <head>
    <title>Main page</title>
  </head>
  <body>
  <h1>Домашняя странрица по работе с пользователями</h1>
  Введите ID пользователя (0 - для вывода всего списка пользователей)
  <br/>
  Доступные: <%
        Model model = Model.getInstance();
        out.print(model.getFromList().size());
        %>
  <form method="get" action="get">
    <label>ID:
      <input type="text" name="id"><br/>
    </label>
    <button type="submit">Поиск</button>
  </form>

  <a href="addUser.html">Добавить пользователя</a><br/>
  </body>
</html>
