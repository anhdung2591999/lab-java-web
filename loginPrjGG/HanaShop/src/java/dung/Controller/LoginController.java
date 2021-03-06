/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dung.Controller;

import dung.model.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Haima
 */
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String ADMIN = "LoadController";
    private static final String USER = "LoadController";

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String role = null;
            String action = request.getParameter("action");
            if (action.equals("Google")) {
                String nameGG = request.getParameter("name");
                role = request.getParameter("role");
               String email =request.getParameter("email");
                session.setAttribute("ROLE", role);
                session.setAttribute("USER", email);
                session.setAttribute("NAME", nameGG);
            } else {
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");
                AccountDao dao = new AccountDao();
                role = dao.Login(username, password);
                String name = dao.LoadName(username);
                session.setAttribute("ROLE", role);
                session.setAttribute("NAME", name);
                session.setAttribute("USER", username);
            }

            if (role.equals("user")) {
                url = USER;
            } else if (role.equals("admin")) {
                url = ADMIN;
            } else {
                request.setAttribute("FAIL", "Username or password is invalid");
            }
        } catch (Exception e) {
            log("Error at LoginController" + e.getMessage());
        } finally {
            System.out.println(url);
            request.getRequestDispatcher(url).forward(request, response);

        }
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
