package com.group4.ex8.email;

import com.group4.ex8.business.User;
import com.group4.ex8.data.UserIO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(urlPatterns = {"/ex8/emailList", "/ex8/"})
public class EmailListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // perform action and set URL to appropriate page
        String url = "/ex8/index.jsp";
        if (action.equals("join")) {
            url = "/ex8/index.jsp";    // the "join" page
        }
        else if (action.equals("add")) {
            // get parameters from the request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            if (firstName == null || firstName.trim().equals("") ||
                lastName == null || lastName.trim().equals("") ||
                email == null || email.trim().equals("")) {
                request.setAttribute("message", "Some field(s) is empty. Please check again.");
            }
            else {
                // store data in User object and save User object in database
                User user = new User(firstName, lastName, email);
                //UserDB.insert(user);

                // set User object in request object and set URL
                request.setAttribute("user", user);
                url = "/ex8/thanks.jsp";   // the "thanks" page
            }
        }

        // create the Date object and store it in the request
        Date currentDate = new Date();
        request.setAttribute("currentDate", currentDate);

        // create users list and store it in the session
        String path = getServletContext().getRealPath("/WEB-INF/EmailList.txt");
        ArrayList<User> users = UserIO.getUsers(path);
        HttpSession session = request.getSession();
        session.setAttribute("users", users);

        // forward request and response objects to specified URL
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
