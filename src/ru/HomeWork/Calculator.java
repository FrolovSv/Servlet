package ru.HomeWork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/Calculator")
public class Calculator extends HttpServlet {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getJsonResponse(request, response);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.getWriter().print("<html>" +
                    "<h3>Что-то пошло не так</h3>" +
                    "</html>");
        }
    }

    private void getJsonResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();

        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (ExportException e) {
            System.out.println(e);
        }

        JsonObject jsObject = gson.fromJson(String.valueOf(jb), JsonObject.class);
        Integer dig1 = jsObject.get("a").getAsInt();
        Integer dig2 = jsObject.get("b").getAsInt();
        String operation = jsObject.get("math").getAsString();

        Map<String, String> result = new HashMap<>();

//        result.put("result", Double.parseDouble(twoNumberOperation(dig1, operation, dig2)));
//        pw.print(gson.toJson(result));
        pw.print("{"+
                "   \"result\"="+Double.parseDouble(twoNumberOperation(dig1, operation, dig2))+
                "}");
    }

    // создаем метод выполнения арифметических операций над числами
    private String twoNumberOperation(Integer num1, String operation, Integer num2) {
        // Ищем совпадения оператора
        switch (operation) {
            // выполняем операцию сложения
            case "+":
                return String.valueOf(num1 + num2);
            // выполняем операцию разности
            case "-":
                return String.valueOf(num1 - num2);
            // выполняем операцию деления
            case "/":
                // если деление производится без остатка возвращаем результат в формате Integer (без остатка)
                if (num1 % num2 == 0) {
                    return String.valueOf(num1 / num2);
                    // в противном случае возвращаем результат в формате Double (значение с плавающей запятой
                } else {
                    return String.valueOf(num1 / Double.valueOf(num2));
                }
                // выполняем операцию умножения
            case "*":
                return String.valueOf(num1 * num2);
            // Если введенная операция не соответсвует описаным выше
            default:
                // выводим сообщение об ошибке
                //throw new IllegalArgumentException("Арифметическая операция: " +operation + " - не поддерживается. Доступные операции: `+`,`-`,`*`,`/`");
                //System.out.println("Арифметическая операция: " +operation + " - не поддерживается. Доступные операции: `+`,`-`,`*`,`/`");
                return "Арифметическая операция: " +operation + " - не поддерживается. Доступные операции: `+`,`-`,`*`,`/`";
        }
    }

}
