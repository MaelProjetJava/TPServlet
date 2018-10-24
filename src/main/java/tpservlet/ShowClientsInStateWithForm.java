/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
@WebServlet(name = "CombinedFormShow", urlPatterns = {"/ShowClientsInStateWithForm"})
public class ShowClientsInStateWithForm extends HttpServlet {

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
        
        DAOEx dao = new DAOEx(DataSourceFactory.getDataSource());
        boolean states_errors = false;
        
        List<String> states;
        try {
            states = dao.getAllStates();
        } catch (DAOException e) {
            states = new ArrayList<>();
            states_errors = true;
        }
        
        String stateName = request.getParameter("state");

        List<CustomerEntity> customers;
        boolean customers_errors = false;
        try {
            customers = dao.customersInState(stateName);
        } catch (DAOException e) {
            customers = new ArrayList<>();
            customers_errors = true;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StateForm</title>");
            out.println("<style>table, th, td {\n" +
                            "    border: 1px solid black;\n" + "}</style>");
            out.println("</head>");
            out.println("<body>");
            
            if (states_errors) {
                out.println("<span style=\"color: red;\">Error retrieving states !</span>");
            }
            
            if (customers_errors) {
                out.println("<span style=\"color: red;\">Error retrieving customers !</span>");
            }
            
            out.println("<form method=\"get\">");
            out.println("<select name=\"state\">");
            
            for (String state_name : states) {
                out.print("<option value=\"");
                out.print(state_name);
                out.print("\"");
                if (state_name.equals(stateName)) {
                    out.print(" selected");
                }
                out.print(">");
                out.print(state_name);
                out.println("</option>");
            }
            
            out.println("</select>");
            out.println("<input type=\"submit\" value=\"Valider\">");
            out.println("</form>");
            
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Name</th>");
            out.println("<th>Address</th>");
            out.println("</tr>");
            for (CustomerEntity customer : customers) {
                out.println("<tr>");
                out.println("<td>" + customer.getCustomerId() + "</td>");
                out.println("<td>" + customer.getName() + "</td>");
                out.println("<td>" + customer.getAddressLine1() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            out.println("</body>");
            out.println("</html>");
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
