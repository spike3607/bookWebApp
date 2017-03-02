package edu.wctc.mss.bookwebapp.controller;

import edu.wctc.mss.bookwebapp.model.Author;
import edu.wctc.mss.bookwebapp.model.AuthorDao;
import edu.wctc.mss.bookwebapp.model.AuthorService;
import edu.wctc.mss.bookwebapp.model.DbAccessor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Spike
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private AuthorService serv;

    private String driverClass = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/book";
    private String username = "root";
    private String password = "admin";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = "/index.html";
        String action = request.getParameter("action");

        AuthorService authService = new AuthorService(driverClass, url, username, password);

        try {

            if (action.equals("list")) {
                List<Author> authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = "/listAuthors.jsp";

            } else if (action.equals("add")) {
                String newName = request.getParameter("newName");
                authService.addAuthor(newName, new Date());

                List<Author> authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = "/listAuthors.jsp";

            } else if (action.equals("updateRedirect")) {
                String id = request.getParameter("id");
                request.setAttribute("id", id);
                destination = "/editAuthor.jsp";
            } else if (action.equals("update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String newName = request.getParameter("newName");
                
                authService.updateAuthor(id, newName);

                List<Author> authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = "/listAuthors.jsp";

            } else if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                
                authService.deleteAuthor(id);

                List<Author> authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = "/listAuthors.jsp";

            } else {
                // Error
                System.out.println("Unable to find action parameter");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
