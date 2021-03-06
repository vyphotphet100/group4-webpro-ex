package com.group4.ex12_1.sql;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;

@WebServlet(urlPatterns = {"/ex12_1/sqlGateway", "/ex12_1/"})
public class SqlGatewayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";
        Connection connection = ConnectionPool.getInstance().getConnection();

        if (connection == null) {
            sqlResult = "<p>Error loading the databse driver</p>";
        } else {
            try {
                // create a statement
                Statement statement = connection.createStatement();
                // parse the SQL string
                sqlStatement = sqlStatement.trim();
                if (sqlStatement.length() >= 6) {
                    String sqlType = sqlStatement.substring(0, 6);
                    if (sqlType.equalsIgnoreCase("select")) {
                        // create the HTML for the result set
                        ResultSet resultSet
                                = statement.executeQuery(sqlStatement);
                        sqlResult = SQLUtil.getHtmlTable(resultSet);
                        resultSet.close();
                    } else {
                        int i = statement.executeUpdate(sqlStatement);
                        if (i == 0) { // a DDL statement
                            sqlResult =
                                    "<p>The statement executed successfully.</p>";
                        } else { // an INSERT, UPDATE, or DELETE statement
                            sqlResult =
                                    "<p>The statement executed successfully.<br>"
                                            + i + " row(s) affected.</p>";
                        }
                    }
                }
                statement.close();
            } catch (SQLException e) {
                sqlResult = "<p>Error executing the SQL statement: <br>"
                        + e.getMessage() + "</p>";
            } finally {
                ConnectionPool.getInstance().freeConnection(connection);
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/ex12_1/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/ex12_1/index.jsp").forward(request, response);
    }
}