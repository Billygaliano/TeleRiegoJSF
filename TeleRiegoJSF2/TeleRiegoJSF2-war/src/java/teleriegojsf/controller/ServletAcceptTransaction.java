/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import teleriegojsf.model.Membership;
import teleriegojsf.model.Transaction;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.ejb.TransactionFacade;

/**
 *
 * @author inftel11
 */
@WebServlet(name = "ServletAcceptTransaction", urlPatterns = {"/ServletAcceptTransaction"})
public class ServletAcceptTransaction extends HttpServlet {
    @EJB
    private LandFacade landFacade;
    @EJB
    private TransactionFacade transactionFacade;
    @EJB
    private MembershipFacade membershipFacade;

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
        
        if(request.getSession().getAttribute("memberNumber")==null){
            request.getRequestDispatcher("WEB-INF/Pages/Login.jsp").forward(request, response);
            return;
        }
        
        
       
        int landIdInteger = Integer.parseInt(request.getParameter("landId"));
        BigDecimal landId = new BigDecimal(landIdInteger);
        int nOrderInteger = Integer.parseInt(request.getParameter("norder"));
        BigDecimal nOrder = new BigDecimal(nOrderInteger);
        double amountWaterInteger = Double.parseDouble(request.getParameter("amountWater"));
        BigInteger amountWater = new BigDecimal(amountWaterInteger).toBigInteger();
        BigDecimal memberNumber = (BigDecimal) request.getSession().getAttribute("memberNumber");
        Membership membership = membershipFacade.getMembership(memberNumber);
                //Only can access if membership is an administrator
        if(!membership.getRole().equalsIgnoreCase("administrador")){
            request.getRequestDispatcher("WEB-INF/Pages/Login.jsp").forward(request, response);
            return;
        }
        //Only accept transaction if state is pendant.
        if(transactionFacade.getTransactionState(nOrder).equalsIgnoreCase("pendiente")){
           transactionFacade.acceptAdminTransaction(nOrder);
           landFacade.updateAdminLand(landId,amountWater);
           membershipFacade.sendTransactionEmail(nOrder);
        }
        
        
        Collection<Transaction> transactions = transactionFacade.getTransactions();
        
        
        
        request.setAttribute("transactions", transactions);
        request.setAttribute("membership", membership);
        request.setAttribute("adminTransaction", true);
        request.getRequestDispatcher("WEB-INF/Pages/AdminTransaction.jsp").forward(request, response);
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
