<%-- 
    Document   : AdminTransaction
    Created on : 18-dic-2015, 10:33:20
    Author     : inftel10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
            <div class="container">
                <section id="perf" class="section appear clearfix">
                    <div class="container">  
                        <div class="align-center"><h1>Transacciones</h1><br> </div>
                                <c:choose>
                                    <c:when test="${param.pendant}">
                                    <a href="ServletAdminTransaction"><input type="button" class="line-btn" value="Ver Todas"></a>
                                    </c:when>
                                    <c:otherwise>
                                    <a href="ServletAdminPendantTransaction"><input type="button" class="line-btn" value="Ver Pendientes"></a>
                                    </c:otherwise>
                                </c:choose>
                        <div class="accordion">      
                            <c:set var="i" value="1"></c:set>
                            <c:forEach var="transaction" items="${transactions}">
                                <div class="accordion-section">
                                    <a class="accordion-section-title" href="#accordion-${i}">Pedido nº ${transaction.norder}</a>
                                    <div id="accordion-${i}" class="accordion-section-content">
                                        <p>Número de socio: <strong>${transaction.memberNumber.memberNumber}</strong></p>
                                        <p>Compra de agua para: <strong>${transaction.landId.nameland}</strong></p>
                                        <p>Cantidad de agua: <strong>${transaction.amount} m<sup>3</sup></strong></p>
                                        <p>Precio total: <strong>${transaction.price} €</strong></p>
                                        <p>Fecha del pedido: <strong><fmt:formatDate type="date" value="${transaction.dateOrder}" /></strong></p>
                                        <p>Estado del pedido: <strong>${transaction.stateOrder}</strong></p>
                                        <c:if test="${transaction.stateOrder eq 'pendiente'}">
                                            <a href="ServletAcceptTransaction?norder=${transaction.norder}&landId=${transaction.landId.landId}&amountWater=${transaction.amount}"><button  class="line-btn green-light">Aceptar</button></a>
                                            <a href="ServletDeniedTransaction?norder=${transaction.norder}"><button  class="line-btn red">Denegar</button></a>
                                        </c:if>
                                    </div><!--end .accordion-section-content-->
                                </div><!--end .accordion-section--> 
                                <c:set var="i" value="${i+1}"></c:set>
                            </c:forEach>
                        </div><!--end .accordion-->
                    </div>
                </section>
            </div>