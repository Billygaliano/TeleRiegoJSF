<%-- 
    Document   : Land-content
    Created on : 16-dic-2015, 23:15:33
    Author     : aitorpagan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<div class="container">
    <section id="perf" class="section appear clearfix">
        <div class="container">  
            <div class="align-center"><h2>Terreno: ${specificLand.nameland}</h2><br> </div>
            <div class="row mar-bot40 col-sm-6" role="group" style="margin-top: 0px">
                <div class="land-details">
                    <h3>Predicción próximos días</h3>
                    <table style="width: 100%"> 
                        <tbody> 
                            <tr>                               
                                <c:forEach var="predictionForDay" items="${weatherPrediction}"> 
                                    <td style="width: 33%">  
                                        <!--http://stackoverflow.com/questions/18829354/converting-string-to-date-format-in-jsp-->
                                        <p class="nomDay">${predictionForDay.dateWeather}</p>
                                        <c:choose>
                                            <c:when test="${predictionForDay.prediction eq '\"Soleado\"'}">
                                                <p class="simbDay"><img src="http://www.tiempo.com/css/images/widget/g20/new/big-1.png" alt="Soleado" title="Soleado"></p>
                                            </c:when>
                                            <c:when test="${predictionForDay.prediction eq '\"Poco nuboso\"'}">
                                                <p class="simbDay"><img src="http://www.tiempo.com/css/images/widget/g20/new/big-2.png" alt="Poco nuboso" title="Poco nuboso"></p>
                                            </c:when>
                                            <c:when test="${predictionForDay.prediction eq '\"Muy nuboso\"'}">
                                                <p class="simbDay"><img src="http://www.tiempo.com/css/images/widget/g20/new/big-3.png" alt="Muy nuboso" title="Muy nuboso"></p>
                                            </c:when>
                                        </c:choose>
                                        <p class="temps"> 
                                            <span class="TMax">${predictionForDay.tmax}°</span> 
                                            <span class="TMin">${predictionForDay.tmin}°</span> 
                                        </p> 
                                        <p class="nomData">Lluvia: <span class="data">${predictionForDay.precipitations} mm</span><p> 
                                        <p class="nomData">Prob.: <span class="data">${predictionForDay.probability}%</span></p>
                                    </td>
                                </c:forEach>
                            </tr> 
                        </tbody>
                    </table>
                </div>
                <div class="land-details">
                    <h3>Características del terreno</h3>
                    <p id="area">Superficie: <strong>${specificLand.squareMeters} m<sup>2</sup></strong></p>
                    <p id="humedad">Humedad de la tierra: <strong>${specificLand.humidity}%</strong></p>
                    <p id="fechariego">Fecha del último riego: <strong><fmt:formatDate type="date" value="${specificLand.lastDateIrrigation}" /></strong></p>
                </div>
                <c:if test="${stateIrrigation eq 'parado'}">
                    <div class="land-details">
                        <h3>Agua disponible para regar</h3>
                        <p id="humedad">Agua disponible para regar: <strong>${specificLand.WMAvailable}m<sup>3</sup></strong></p>
                        <form action="ServletBuyWater?landId=${specificLand.landId}" method="post" role="form">
                            <button type="submit" class="line-btn green">Comprar agua</button>
                        </form>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${specificLand.WMAvailable ge specificLand.squareMeters /1000}">
                        <div class="land-details">
                            <c:choose>
                                <c:when test="${stateIrrigation eq 'regando'}">
                                    <h3>Estado del riego</h3>
                                    <span>Regando...</span>
                                    <form action="ServletStopIrrigation?landId=${specificLand.landId}" method="post" role="form">
                                        <button type="submit" class="line-btn green">Dejar de regar</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <h3>Recomendación de riego</h3>
                                    <c:choose>
                                        <c:when test="${needIrrigate}">
                                            <p>Le recomendamos que riegue este terreno.</p>
                                            <form action="ServletStartIrrigation?landId=${specificLand.landId}" method="post" role="form">
                                                <button type="submit" class="line-btn green">Regar</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Este terreno no necesita regarse.</p>
                                            <form action="ServletStartIrrigation?landId=${specificLand.landId}" method="post" role="form">
                                                <button type="submit" class="line-btn green">Regar</button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                           <c:if test="${latchIrrigation}">
                                <label class="error">El regado está bloqueado por el administrador</label>
                           </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose> 
            </div>
            <div class="row mar-bot40 col-sm-6" role="group" style="margin-top: 0px">
                <p id="map">Información sobre la localización MAPA</p>
            </div>                      
        </div>
    </section>
</div>
