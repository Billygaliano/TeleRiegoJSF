<%-- 
    Document   : Lands-content
    Created on : 16-dic-2015, 23:08:27
    Author     : aitorpagan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

 <div class="container">
                <section id="perf" class="section appear clearfix">
                    <div class="container">  
                        <div class="align-center"><h1>Mis Terrenos</h1><br> </div>
                        <div class="accordion">      
                            <c:set var="i" value="1"></c:set>
                            <c:forEach var="land" items="${membership.landCollection}">
                                <div class="accordion-section">
                                    <a class="accordion-section-title" href="#accordion-${i}">${land.nameland}</a>
                                    <div id="accordion-${i}" class="accordion-section-content">
                                        <p>Estado: <strong>${land.state}</strong></p>
                                        <p>Último riego: <strong><fmt:formatDate type="date" value="${land.lastDateIrrigation}" /></strong></p>
                                        <p>Humedad: <strong>${land.humidity} %</strong></p>
                                        <a href="ServletLand?landid=${land.landId}">
                                            <button type="submit" class="line-btn green">Acceder</button>
                                        </a>
                                    </div><!--end .accordion-section-content-->
                                </div><!--end .accordion-section--> 
                                <c:set var="i" value="${i+1}"></c:set>
                            </c:forEach>
                        </div><!--end .accordion-->
                    </div>
                </section>
            </div>
