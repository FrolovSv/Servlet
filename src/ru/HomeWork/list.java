package ru.HomeWork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.HomeWork.Logic.Model;
import ru.HomeWork.Logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ExportException;
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class list extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getTextResponse(request, response);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.getWriter().print("<html>" +
                    "<h3>Что-то пошло не так</h3>" +
                    "<a href=\"addUser.html\">Добавить пользователя</a><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>" +
                    "</html>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getJsonResponse(request,response);
    }

    private void getTextResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        Integer id = Integer.parseInt(request.getParameter("id"));

        if (id==0) {
            pw.print("<html>"+
                    "<h3>Доступные пользователи:</h3><br/>"+
                    "ID пользователя:"+
                    "<ul>");
            for (Map.Entry<Integer, User> entry : model.getFromList().entrySet()){
                pw.print("<li>" + entry.getKey() + "</li>"+
                        "<ul>"+
                        "<li>Имя: " + entry.getValue().getName() + "</li>"+
                        "<li>Фамилия: " + entry.getValue().getSurName() + "</li>"+
                        "<li>Зарплата: " + entry.getValue().getSalary() + "</li>"+
                        "</ul>");
            }
            pw.print("</ul>" +
                    "<a href=\"addUser.html\">Добавить пользователя</a><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }else if (id >0) {
            if (id > model.getFromList().size()) {
                pw.print("<html>" +
                        "<h3>Пользователя с таким номером нет в базе ((</h3><br/>" +
                        "<a href=\"index.jsp\">Домой</a><br/>"+
                        "</html>");
            } else {
                pw.print("<html>" +
                        "<h3>Запрошенный пользователь:</h3><br/>" +
                        "<br/>"+
                        "<li>Имя: " + model.getFromList().get(id).getName() + "</br>"+
                        "<li>Фамилия: " + model.getFromList().get(id).getSurName() + "</br>"+
                        "<li>Зарплата: " + model.getFromList().get(id).getSalary() + "</br>"+
                        "<a href=\"index.jsp\">Домой</a><br/>"+
                        "</html>");

            }
        }else if (id < 0) {
            pw.print("<html>" +
                    "<h3>Номер пользователя должен быть больше нуля</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>" +
                    "</html>");
        }
    }

    private void getJsonResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        StringBuffer jb = new StringBuffer();
        String line;
        PrintWriter pw = response.getWriter();
        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                jb.append(line);
            }
        }catch (ExportException e){
            System.out.println(e);
        }

        JsonObject jsObject = gson.fromJson(String.valueOf(jb), JsonObject.class);

        Integer id = jsObject.get("id").getAsInt();

        if (id==0) {
            pw.print(gson.toJson(model.getFromList()));
        }else if (id >0) {
            if (id > model.getFromList().size()) {
                response.getWriter().print("<html>" +
                        "<h3>Пользователя с данным ID нет в базе</h3>" +
                        "<a href=\"index.jsp\">Домой</a><br/>" +
                        "</html>");
            } else {
                pw.print(gson.toJson(model.getFromList().get(id)));
            }
        }else if (id < 0) {
            response.getWriter().print("<html>" +
                    "<h3>ID должно быть больше 0/h3>" +
                    "<a href=\"index.jsp\">Домой</a><br/>" +
                    "</html>");
        }


    }
}
