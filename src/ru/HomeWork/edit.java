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

@WebServlet(urlPatterns = "/edit")
public class edit extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getContentType().equals("application/json")) {
                getJsonResponse(request, response);
            }
            else {
                getTextResponse(request, response);
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.getWriter().print("<html>" +
                    "<h3>Что-то пошло не так</h3>" +
                    "<a href=\"addUser.html\">Добавить пользователя</a><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>" +
                    "</html>");
        }
    }

    private void getTextResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Double salary = Double.parseDouble(request.getParameter("salary"));
        Integer id = Integer.parseInt(request.getParameter("id"));



        if (id>0 && id <= model.getFromList().size()) {
            model.changeUser(id, new User(name,surname,salary));
            pw.print("<html>"+
                    "<h3>Пользователь с ID = " + id +" - успешно изменен</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }else {
            pw.print("<html>" +
                    "<h3>Пользователя с номером = "+ id +" - нет в базе ((</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }
    }

    private void getJsonResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/json;charset=utf-8");
        response.setContentType("text/html;charset=utf-8");
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

        String name = jsObject.get("name").getAsString();
        String surname = jsObject.get("surname").getAsString();
        Double salary = jsObject.get("salary").getAsDouble();
        Integer id = jsObject.get("id").getAsInt();

        if (id>=0 && id <= model.getFromList().size()) {
            model.changeUser(id, new User(name,surname,salary));
            pw.print("<html>"+
                    "<h3>Пользователь с ID = " + id +" - успешно изменен</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }else {
            pw.print("<html>" +
                    "<h3>Пользователя с номером = "+ id +" - нет в базе ((</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }
    }
}
