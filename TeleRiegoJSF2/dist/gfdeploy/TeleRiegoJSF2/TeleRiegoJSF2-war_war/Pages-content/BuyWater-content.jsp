<%-- 
    Document   : BuyWater-content
    Created on : 17-dic-2015, 20:22:11
    Author     : aitorpagan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
        <div class="container">
            <section id="login" class="section appear clearfix">
             

                    <div class="row mar-bot40" style="margin-top: 40px">
                        <div class="col-md-offset-3 col-md-6">
                            <div class="section-header">
                                <h2 class="section-heading animated" data-animation="bounceInUp">Compra de agua</h2>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 col-md-offset-2">
                            <div class="cform" id="contact-form">
                                <form action="ServletConfirmBuyWater?landId=${specificLand.landId}" method="post" role="form" class="ServletLogin">
                                    <div class="wow bounceIn">
                                        <div class="form-group">
                                            <label for="name">Nº de socio: ${membership.memberNumber}</label> 
                                        </div>
                                        <div class="form-group">
                                            <label for="name">Nombre de usuario: ${membership.userName} ${membership.surname}</label> 
                                        </div>
                                        <div class="form-group">
                                            <label for="name">Nombre del terreno: ${specificLand.nameland}</label> 
                                        </div>
                                        <div class="form-group">
                                            <label for="name">Precio del litro del agua: </label> <strong> 0.22 €/l</strong>
                                        </div>
                                        
                                        <div class="form-group">
                                            <label for="email">Cantidad:</label>
                                            <input type="text" class="form-control" name="quantity" id="quantity"  data-rule="required" data-msg="Por favor, introduzca una cantidad" />
                                            <div class="validation"></div>
                                        </div>
                                    <button type="submit" class="line-btn green">Comprar</button>
                                </form>
                                    <c:if test="${param.confirmedBuyWater}">
                                        <!--<p class="verificate">La compra ha sido realizada, total: ${total}€.</p>-->
                                        <c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 2) %></c:set>
                                        <c:set var="now" value="<%=new java.util.Date()%>" />
                                        <p class="verificateWater">Para finalizar la tramitación de su pedido, haga clic <a href="mailto:teleriegoinftel@gmail.com&subject=Asunto">aquí</a></p>
                                    </c:if>
                            </div>
                        </div>
                        <!-- ./span12 -->
                    </div>

                </div>
            </section>
            </div>