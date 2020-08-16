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

@WebServlet(urlPatterns = "/delete")
public class delete extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getContentType().equals("application/json")) {
                getJsonResponse(request, response);
            }else{
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

    private void getJsonResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setCharacterEncoding("UTF-8");
        // если нужно отправлять ответ в виде json то нужно убрать комментарий следующей строки
        //response.setContentType("application/json;charset=utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();

        StringBuffer jb = new StringBuffer();
        String line;
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

        if (model.deleteUser(id)) {
            // если нужно отпавлять отчет в формате json то нужно изменить pw.print
            pw.print("<html>"+
                    "<h3>Пользователь с ID = " + id +" - успешно удален</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }else {
            // если нужно отпавлять отчет в формате json то нужно изменить pw.print
            pw.print("<html>" +
                    "<h3>Пользователя с номером = "+ id +" - нет в базе ((</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }
    }

    private void getTextResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        Integer id = Integer.parseInt(request.getParameter("id"));

        if (model.deleteUser(id)) {
            if (id>0) {
                pw.print("<html>" +
                        "<h3>Пользователь с ID = " + id + " - успешно удален</h3><br/>" +
                        "<a href=\"index.jsp\">Домой</a><br/>" +
                        "</html>");
            }else {
                pw.print("<html>" +
                        "<h3>Все пользователи успешно удалены</h3><br/>" +
                        "<a href=\"index.jsp\">Домой</a><br/>" +
                        "</html>");
            }
        }else {
            pw.print("<html>" +
                    "<h3>Пользователя с номером = "+ id +" - нет в базе ((</h3><br/>" +
                    "<a href=\"index.jsp\">Домой</a><br/>"+
                    "</html>");
        }
    }
}
