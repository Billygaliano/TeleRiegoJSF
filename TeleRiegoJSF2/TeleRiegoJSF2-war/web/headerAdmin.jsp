<%-- 
    Document   : headerAdmin
    Created on : 18-dic-2015, 11:05:39
    Author     : inftel10
--%>

<div class="header">
            <section id="header" class="appear">
                <div class="navbar"  role="navigation" data-0="line-height:100px; height:100px; background-color:rgba(0,0,0,1);" data-300="line-height:60px; height:60px; background-color:rgba(0,0,0,1);">

                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="fa fa-bars color-white"></span>
                        </button>
                        <h1><a class="navbar-brand" href="index.html" data-0="line-height:90px;" data-300="line-height:50px;">Tele Riego
                            </a></h1>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav" data-0="margin-top:20px;" data-300="margin-top:5px;">
                            <li id="photo">  
                                <div><a href="ServletAdminProfile">
                                        <img class="img-circle" alt="profile" src="ServletImage?id=${sessionScope.memberNumber}" height="10%"width="10%" />
                                      </a>
                                </div>
                            </li>                            
                            <li><a href="ServletAdminProfile">${membership.userName}</a></li>
                            <li><a href="ServletAdminTransaction">Transacciones</a></li>
                            <li><a href="ServletLogOut">Cerrar Sesión</a></li>
                        </ul>
                    </div><!--/.navbar-collapse -->
                </div>
            </section>
            </div>
