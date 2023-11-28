package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UserDTO;
import org.example.entities.User;
import org.example.services.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("Hello!");
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(request.getHeader(""));
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            /*report an error*/
            e.printStackTrace();
        }
        System.out.println(jb.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User( objectMapper.readValue(jb.toString(), UserDTO.class));

        UserService userService = new UserService();

        System.out.println("helo");

        userService.addUser(user);

    }
}
