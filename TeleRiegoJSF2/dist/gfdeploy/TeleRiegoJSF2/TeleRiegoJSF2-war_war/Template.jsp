<%-- 
    Document   : Template
    Created on : 16-dic-2015, 22:47:56
    Author     : aitorpagan
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : UserView
    Created on : 12-dic-2015, 12:37:39
    Author     : inftel10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
    <!-- BASICS -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Tele Riego</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/isotope.css" media="screen" />	
    <link rel="stylesheet" href="js/fancybox/jquery.fancybox.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/bootstrap-theme.css">
    <link href="css/responsive-slider.css" rel="stylesheet">
    <link rel="stylesheet" href="css/animate.css">
    <link rel="stylesheet" href="css/style.css">

    <link rel="stylesheet" href="css/font-awesome.min.css">
    <!-- skin -->
    <link rel="stylesheet" href="skin/default.css">

</head>
	 
<body>
    <jsp:include page="/WEB-INF/header.jsp"></jsp:include>

    <jsp:include page="/WEB-INF/Pages-content/${param.content}.jsp"></jsp:include>

    <jsp:include page="/WEB-INF/footer.jsp"></jsp:include>



        <script src="js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="js/jquery.js"></script>
        <script src="js/jquery.easing.1.3.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6JFvBfAvf0e91N6WgmpJ4vk-e4uJKO6U&sensor=false"></script>        
        <script src="js/jquery.isotope.min.js"></script>
        <script src="js/jquery.nicescroll.min.js"></script>
        <script src="js/fancybox/jquery.fancybox.pack.js"></script>
        <script src="js/jquery.parallax-1.1.3.js" type="text/javascript" ></script>
        <script src="js/skrollr.min.js"></script>		
        <script src="js/jquery.scrollTo-1.4.3.1-min.js"></script>
        <script src="js/jquery.localscroll-1.2.7-min.js"></script>
        <script src="js/stellar.js"></script>
        <script src="js/responsive-slider.js"></script>
        <script src="js/jquery.appear.js"></script>
        <script src="js/validate.js"></script>
        <script src="js/grid.js"></script>
        <script src="js/main.js"></script>
        <script type="text/javascript" src="js/accordion.js"></script>

    <c:if test="${land}">
        <script type="text/javascript">
            google.maps.event.addDomListener(window, 'load', init);

            function init() {
                // Basic options for a simple Google Map
                // For more options see: https://developers.google.com/maps/documentation/javascript/reference#MapOptions
                var pos = new google.maps.LatLng(${specificLand.longitude}, ${specificLand.latitude});
                
                var mapOptions = {
                    // How zoomed in you want the map to start at (always required)
                    zoom: 15,
                    // The latitude and longitude to center the map (always required)
                    center: pos, // New York //parametro de localizacion
                    //shape: 
                    //mapMaker: true 

                    // How you would like to style the map. 
                    // This is where you would paste any style found on Snazzy Maps.
                    //styles: [	{		featureType:"all",		elementType:"all",		stylers:[		{			invert_lightness:true		},		{			saturation:10		},		{			lightness:30		},		{			gamma:0.5		},		{			hue:"#1C705B"		}		]	}	]
                };

                // Get the HTML DOM element that will contain your map 
                // We are using a div with id="map" seen below in the <body>
                var mapElement = document.getElementById('map');

                // Create the Google Map using out element and options defined above
                var map = new google.maps.Map(mapElement, mapOptions);
                <c:forEach var="specland" items="${membership.landCollection}">
                    var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(${specland.longitude}, ${specland.latitude}), 
                    map: map, 
                    title:"${specland.nameland}"});
                    google.maps.event.addDomListener(marker,'click',function(){
                        window.location.href = 'ServletLand?landid=${specland.landId}';
                    })
                </c:forEach>
                
            }
        </script>
    </c:if>
    <script src="js/wow.min.js"></script>
    <script>
           wow = new WOW(
                   {
                   })
                   .init();
    </script>
</body>
</html>

