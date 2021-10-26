package com.group4.ex12_2.admin;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.group4.ex12_2.business.User;
import com.group4.ex12_2.data.UserDB;

@WebServlet(urlPatterns = {"/ex12_2/", "/ex12_2/userAdmin"})
public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        String url = "/ex12_2/index.jsp";
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "display_users";  // default action
        }
        
        // perform action and set URL to appropriate page
        if (action.equals("display_users")) {            
            // get list of users
            ArrayList<User> users = UserDB.selectUsers();
            request.setAttribute("users", users);
        } 
        else if (action.equals("display_user")) {
            // get user for specified email
            // set as session attribute
            // forward to user.jsp
            User user = UserDB.selectUser(request.getParameter("email"));
            request.setAttribute("user", user);
            url = "/ex12_2/user.jsp";
        }
        else if (action.equals("update_user")) {
            // update user in database
            // get current user list and set as request attribute
            // forward to index.jsp
            User user = new User();
            user.setEmail(request.getParameter("email"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            UserDB.update(user);
            request.setAttribute("users", UserDB.selectUsers());
        }
        else if (action.equals("delete_user")) {
            // get the user for the specified email
            // delete the user            
            // get current list of users
            // set as request attribute
            // forward to index.jsp
            User user = new User();
            user.setEmail(request.getParameter("email"));
            UserDB.delete(user);
            request.setAttribute("users", UserDB.selectUsers());
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }    
}