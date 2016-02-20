/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.controller;


import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.ejb.EJB;
import com.google.gson.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import teleriegojsf.model.Membership;
import teleriegojsf.ejb.MembershipFacade;
import com.ning.http.util.Base64;
import java.net.*;
import java.security.*;
import javax.crypto.*;

/**
 *
 * @author inftel12
 */
@WebServlet(name = "ServletPairLatch", urlPatterns = {"/ServletPairLatch"})
public class ServletPairLatch extends HttpServlet {
    @EJB
    private MembershipFacade membershipFacade;

    private static final String LATCH_APP_ID = "W2i6TtX6N8C9GNugxkRh";
    private static final String LATCH_SECRET = "T67WcbigW69xjzqJMFuYp9xFRFRj3YrcgMVaxwTQ";

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
        
        BigDecimal memberNumber = (BigDecimal) request.getSession().getAttribute("memberNumber");
        Membership membership = membershipFacade.getMembership(memberNumber);
        //Only can access if membership is an administrator
        if(!membership.getRole().equalsIgnoreCase("administrador")){
            request.getRequestDispatcher("WEB-INF/Pages/Login.jsp").forward(request, response);
            return;
        }
        
        LatchApp latch = new LatchApp(LATCH_APP_ID, LATCH_SECRET);
        
        String token = request.getParameter("token");
        LatchResponse pairResponse = latch.pair(token);
        
        String accountId = pairResponse.getData().get("accountId").getAsString();
        membershipFacade.setMembershipAccountId(accountId, memberNumber);        
        
        if(membershipFacade.getMembershipAccountId(memberNumber) != null){
            request.setAttribute("accountId", membershipFacade.getMembershipAccountId(memberNumber));
            request.setAttribute("accountIdExists", true);
        }else{
            request.setAttribute("accountIdExists", false);
        }
        
        request.getRequestDispatcher("WEB-INF/Pages/ProfileAdmin.jsp").forward(request, response);
        
        
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
