<%-- 
    Document   : profile-content
    Created on : 16-dic-2015, 22:57:02
    Author     : aitorpagan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<div class="container">
                <section id="perf" class="section appear clearfix">
                    <div class="container">  
                        <div class="align-center"><h2>Mi perfil</h2><br> </div>
                        <div class="row mar-bot40 col-md-2" role="group" style="margin-top: 0px">
                            <p id="photo" >
                              <img class="img-rounded " alt="profile" src="ServletImage?id=${sessionScope.memberNumber}"  width="70%"/>
                            </p>
                            <a href="ServletUploadImage"><button type="button" id="uploadimage" class="line-btn green">Cambiar/Subir Foto</button></a>
                            <c:if test="${param.uploadimage}">
                                <form action="ServletChangeImage" method="POST" enctype="multipart/form-data">
                                    <input type="file" name="image">
                                    <input type="submit" value="Subir">
                                </form>
                                <c:if test="${param.baduploadimage}">
                                    <div id="baduploadimage" class="error">
                                        Imagen no válida
                                    </div>
                                </c:if>
                            </c:if>
                            
                        </div>                        
                        <div class="row mar-bot40 col-md-offset-1 col-md-4" role="group" style="margin-top: 0px"> 
                            <p>Número de usuario: <strong>${membership.memberNumber}</strong></p>
                            <p>DNI: <strong>${membership.dni}</strong></p>
                            <p>Nombre y apellidos: <strong>${membership.userName} ${membership.surname}</strong></p>
                            <p>Dirección: <strong>${membership.address}</strong></p>
                            <p>Teléfono: <strong>${membership.phone}</strong></p>
                            <p>E-Mail: <strong>${membership.email}</strong></p>
                            <a href="ServletChangePass"><button type="button" id="changepass" class="line-btn green">Cambiar Contraseña</button></a>                         
                        </div>
                        <div class="row mar-bot40 col-lg-offset-2 col-md-3" role="group" style="margin-top:0px">
                            <c:if test="${param.changepass}">
                                <div class="cform" id="contact-form">
                                    <form action="ServletConfirmChangePass" method="post" role="form">
                                        <div class="wow bounceIn">
                                            <div class="form-group">
                                                <label for="name">Contraseña Actual</label>
                                                <input type="password" name="oldPass" class="form-control" id="user" placeholder="" data-rule="required" data-msg="Por favor, introduca contraseña" />
                                                <div class="validation"></div>
                                            </div>
                                            <div class="form-group">
                                                <label for="email">Nueva Contraseña</label>
                                                <input type="password" class="form-control" name="newPass" id="password" placeholder="" data-rule="required" data-msg="Por favor, introduzca contraseña" />
                                                <div class="validation"></div>
                                            </div>
                                               <button type="submit" class="line-btn green">Cambiar Contraseña</button>        
                                        </div>
                                    </form>
                                </div>
                            </c:if >
                            <c:choose>
                                <c:when test="${correctUpdate}">
                                    <p class="verificate">Tu contraseña ha sido actualizada</p>
                                </c:when>
                                <c:when test="${incorrectUpdate}">
                                    <p class="error">Tu contraseña no se ha podido actualizar</p>
                                </c:when>
                            </c:choose>

                        </div>    
                    </div>
                </section>
            </div>
