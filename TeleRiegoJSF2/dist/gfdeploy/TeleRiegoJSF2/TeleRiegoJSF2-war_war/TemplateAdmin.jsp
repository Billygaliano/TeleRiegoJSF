<?xml version='1.0' encoding='UTF-8' ?> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://xmlns.jcp.org/jsf/html">

    <h:head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta name="description" content="" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Tele Riego</title>
        <h:outputStylesheet library="css" name="isotope.css"> </h:outputStylesheet>	
        <h:outputScript library="js" name="fancybox/jquery.fancybox.css"> </h:outputScript>
        <h:outputStylesheet library="css" name="bootstrap.css"> </h:outputStylesheet>
        <h:outputStylesheet library="css" name="bootstrap-theme.css"> </h:outputStylesheet>
        <h:outputStylesheet library="css" name="responsive-slider.css"> </h:outputStylesheet>
        <h:outputStylesheet library="css" name="animate.css"> </h:outputStylesheet>
        <h:outputStylesheet library="css" name="style.css"> </h:outputStylesheet>

        <h:outputStylesheet library="css" name="font-awesome.min.css"> </h:outputStylesheet>
        <!-- skin -->
        <h:outputStylesheet library="skin" name="default.css"> </h:outputStylesheet>
    </h:head>

    <h:body>
        <!-- HEADER -->
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
                               <div>
                                    <a href="ServletProfile">

                                    </a>
                               </div>
                           </li>
                            <li><a href="ServletProfile">${membership.userName}</a></li>
                            <li><a href="ServletTransaction">Transacciones</a></li>
                            <li><a href="ServletLogOut">Cerrar Sesi�n</a></li>
                        </ul>
                    </div><!--/.navbar-collapse -->
                </div>
            </section>
        </div>
        <!-- END HEADER -->
  
        <ui:insert name="content"></ui:insert>
        
       <!-- FOOTER -->
        <section id="footer" class="section footer">
            <div class="container">
                <div class="row animated opacity mar-bot0" data-andown="fadeIn" data-animation="animation">
                    <div class="col-sm-12 align-center">
                        <ul class="social-network social-circle">
                            <li><a href="http://www.fenacore.org/escaparate/gmms/fenacore/contenidosGMMFenacore.cgi?tipo=noticiasenportada" class="icoRss" title="Rss"><i class="fa fa-rss"></i></a></li>
                            <li><a href="https://twitter.com/FenacoreOficial" class="icoTwitter" title="Twitter"><i class="fa fa-twitter"></i></a></li>
                        </ul>				
                    </div>
                </div>

                <div class="row align-center copyright">
                    <div class="col-sm-12"><p>Copyright &copy; 2016 - M�ster INFTEL</p></div>
                    <!-- 
                        All links in the footer should remain intact. 
                        Licenseing information is available at: http://bootstraptaste.com/license/
                        You can buy this theme without footer links online at: http://bootstraptaste.com/buy/?theme=Green
                    -->
                </div>
            </div>
        </section>
        <!-- END FOOTER -->

	<h:outputScript library="js" name="modernizr-2.6.2-respond-1.1.0.min.js"></h:outputScript>
	<h:outputScript library="js"  name="jquery.js"></h:outputScript>
	<h:outputScript library="js"  name="jquery.easing.1.3.js"></h:outputScript>
        <h:outputScript library="js"  name="bootstrap.min.js"></h:outputScript>
        
	<h:outputScript library="js"  name="jquery.isotope.min.js"></h:outputScript>
	<h:outputScript library="js"  name="jquery.nicescroll.min.js"></h:outputScript>
	<h:outputScript library="js"  name="fancybox/jquery.fancybox.pack.js"></h:outputScript>
	<h:outputScript library="js"  name="skrollr.min.js"></h:outputScript>		
	<h:outputScript library="js"  name="jquery.scrollTo-1.4.3.1-min.js"></h:outputScript>
	<h:outputScript library="js"  name="jquery.localscroll-1.2.7-min.js"></h:outputScript>
	<h:outputScript library="js"  name="stellar.js"></h:outputScript>
	<h:outputScript library="js"  name="responsive-slider.js"></h:outputScript>
	<h:outputScript library="js"  name="jquery.appear.js"></h:outputScript>
	<h:outputScript library="js"  name="validate.js"></h:outputScript>
	<h:outputScript library="js"  name="grid.js"></h:outputScript>
        <h:outputScript library="js"  name="main.js"></h:outputScript>
        <h:outputScript library="js"  name="accordion.js"></h:outputScript>
        <h:outputScript library="js"  name="wow.min.js"></h:outputScript>
    </h:body>

</html>