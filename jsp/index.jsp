<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>

<%if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {%>

<body style="background-color: black">
<div class="container" style="width: 100%; max-height: 75%">
    <div class="row">
        <div class="col-md-12">
            <br /><br /><br />
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner">
                    <div class="item active">
                        <div class="indexCarousel">
                            <img src="../images/carousel2.jpg"
                                 alt="" />
                            <h2>
                                <span>
                                    <a href="#carousel-example-generic" role="button" data-slide="prev">&lt</a>
                                    DESTI
                                    <a href="#carousel-example-generic" role="button" data-slide="next">&gt</a>
                                </span>
                            </h2>
                            <div class="carouselLogin">
                                <a class="btn btn-default navbar-btn" role="button">Login</a>
                            </div>
                            <div class="carouselRegister">
                                <a href="#" data-toggle="modal"
                                   data-target="#signUpForm"
                                   class="btn btn-default navbar-btn"
                                   role="button">Sign Up</a>
                            </div>
                        </div>
                    </div>

                    <div class="item">
                        <div class="indexCarousel">
                            <img src="../images/carousel1.jpg" alt="" />
                            <h2>
                                <span>
                                    <a href="#carousel-example-generic" role="button" data-slide="prev">&lt</a>
                                    DESTI
                                    <a href="#carousel-example-generic" role="button" data-slide="next">&gt</a>
                                </span>
                            </h2>
                            <div class="carouselLogin">
                                <a class="btn btn-default navbar-btn" role="button">Login</a>
                            </div>
                            <div class="carouselRegister">
                                <a href="#" data-toggle="modal"
                                   data-target="#signUpForm"
                                   class="btn btn-default navbar-btn"
                                   role="button">Sign Up</a>
                            </div>
                        </div>
                    </div>

                    <div class="item">
                        <div class="indexCarousel">
                            <img src="../images/carousel3.jpg" />
                            <h2>
                                <span>
                                    <a href="#carousel-example-generic" role="button" data-slide="prev">&lt</a>
                                    DESTI
                                    <a href="#carousel-example-generic" role="button" data-slide="next">&gt</a>
                                </span>
                            </h2>
                            <div class="carouselLogin">
                                <a class="btn btn-default navbar-btn" role="button">Login</a>
                            </div>
                            <div class="carouselRegister">
                                <a href="#" data-toggle="modal"
                                   data-target="#signUpForm"
                                   class="btn btn-default navbar-btn"
                                   role="button">Sign Up</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<!-- Jquery Javascript -->
<script src="/CS2340Servlet/js/jquery.js"></script>
<script src="/CS2340Servlet/js/bootstrap.min.js"></script>

<%@ include file="footer-centered.jsp" %>

<% } else { %>
<%  Itinerary activeItinerary = (Itinerary) session.getAttribute("activeItinerary");
    List<City> cities = (List<City>) session.getAttribute("cities");
    String cityAddress = "";
    City indexPanelCity = (City) session.getAttribute("activeCity");
    List<Place> userEvents = (indexPanelCity != null) ? indexPanelCity.getEvents() : null;
    int numEvents = 0;
    if (userEvents != null) {
        numEvents = userEvents.size();
    }
%>

<body style="overflow-x: hidden">
    <div id="itinerary-header">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">DESTI</a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Link</a></li>
                        <li><a href="#">Link</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Separated link</a></li>
                                <li class="divider"></li>
                                <li><a href="#">One more separated link</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search">
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Link</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Separated link</a></li>
                                <li id="fbLoginButton" scope="public_profile,email" onclick="fb_login();"><a href="#">Facebook Login</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="row" id="landing-title">
            <h1 id="landing-title-h1">
                DESTI<span style="position: relative; top: -8px">&#9679;</span>NATION
            </h1>
        </div>
        <div class="container" id="landing-cards">
            <div class="row">
                <div class="col-md-3">
                    <span class="landing-card-subtitle" style="margin-left: 27%; margin-top: -1%">Map</span>
                    <div class="landing-card" id="map-landing-card"
                         style="height: 200px; background-color: rgb(243, 156, 18); color: rgb(243, 156, 18);">
                        <span style="position: absolute; bottom: 0; left: 5%; color: white; display: none">
                        </span>
                    </div>
                </div>
                <div class="col-md-2" >
                    <span class="landing-card-subtitle">Lodging</span>
                    <div class="landing-card" id="lodging-landing-card"
                         style="height: 200px; background-color: rgb(139, 0, 0); color: rgb(139, 0, 0)">
                        <span style="position: absolute; bottom: 0; left: 5%; color: white; display: none">
                            From the Lodging panel:<br /><br />
                            <ul style="margin-right: 10%">
                                <li>Find a Lodging using Yelp or Google</li>
                                <li>Add what you find to your Itinerary</li>
                                <li>Set check-in and check-out times for your Lodging</li>
                            <%  if (indexPanelCity.getLodging() == null) { %>
                            <br />You currently have no lodging for your Itinerary. Click here to create one.
                            <%  } %>
                            </ul>
                        </span>
                    </div>
                </div>
                <div class="col-md-2">
                    <span class="landing-card-subtitle">Itinerary</span>
                    <div class="landing-card" id="itinerary-landing-card"
                         style="height: 200px; background-color: rgb(4, 75, 144); color: rgb(4, 75, 144)">
                        <span style="position: absolute; bottom: 0; left: 5%; color: white; display: none">
                            From the Itinerary panel:<br /><br />
                            <ul style="margin-right: 10%">
                                <li><b>Discover</b><br /> Search for Places using Yelp or Google</li>
                                <li><b>Stay informed</b><br /> Browse reviews, ratings and more</li>
                                <li><b>Spread out</b><br /> Add another City to your Itinerary</li>
                                <li><b>Interact</b><br /> Send messages to your friends via Facebook</li>
                            </ul>
                        </span>
                    </div>
                </div>
                <div class="col-md-2">
                    <span class="landing-card-subtitle">Events & Places</span>
                    <div class="landing-card" id="event-landing-card"
                         style="height: 200px; background-color: rgb(26, 188, 156); color: rgb(26, 188, 156)">
                        <span style="position: absolute; bottom: 0; left: 5%; color: white; display: none">
                            From the Events & Places panel:
                            <ul style="margin-right: 10%">
                                <li>Quickly find restaurants, airports, and more</li>
                                <li>Endless search possibilities with Google or Yelp</li>
                                <li>Update event times on the fly</li>
                                <li>Neatly organize your events using different views</li>
                                <li>Sort events by name, creation date or time</li>
                                <%  if (indexPanelCity.getEvents() == null) { %>
                                <br />You currently have no events for your Itinerary. Click here to create some.
                                <%  } %>
                            </ul>
                        </span>
                    </div>
                </div>
                <div class="col-md-3">
                    <span class="landing-card-subtitle" style="margin-top: -1%">Budget</span>
                    <div class="landing-card" id="budget-landing-card"
                         style="height: 200px; background-color: rgb(147, 112, 219); color: rgb(147, 112, 219)">
                        <span style="position: absolute; bottom: 0; left: 5%; color: white; display: none">
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="itinerary-overview" style="margin-top: 10px">
        <ul class="nav nav-pills" style="float: right">
            <li>
                <a href="#" style="color: rgb(66, 139, 202); font-weight: bold">
                    Active Itinerary: <%=activeItinerary.getName()%>
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="badge pull-right"
                          style="position: relative; top: 2px; background-color: rgb(66, 139, 202)">
                        <%=numEvents%>
                    </span>
                    Events
                </a>
            </li>
            <li>
                <a href="#" id="preferences-trigger">
                    Preferences
                </a>
            </li>
        </ul>
        <div class="page-divider-header">
            <div style="display: inline-block; margin-bottom: 50px">
                <h1><span class="glyphicon glyphicon-tags"></span> YOUR ITINERARY</h1>
                <hr class="hr-title" />
            </div>
            <p><span style="font-size: 20px"><b>Find a place to stay:</b></span><br />
                Add a new Lodging below<br /><br />
                <span style="font-size: 20px"><b>Find Events for your First City:</b></span><br />
                To add events for this city, Create a New Event<br /><br />
                <span style="font-size: 20px"><b>Or travel to a New City:</b></span><br />
                To go sightseeing in another city, Add a New City to your Itinerary
            </p><br /><br />
            <div class="container" style="width: 80% !important">
                <ul class="nav nav-tabs" id="cityList" role="tablist">
                    <li><a href="#">Your Cities</a></li>
                    <%  if (cities != null) {
                            indexPanelCity = (City) session.getAttribute("activeCity");
                            cityAddress = (indexPanelCity != null) ? indexPanelCity.getAddress() : "";
                            if (cities != null) {
                                for (City city : cities) { %>
                    <li><a href="/CS2340Servlet/itinerary?city_id=<%=city.getID()%>"><%=city.getName()%></a></li>
                            <%  } %>
                        <%  } %>
                    <%  } %>
                </ul>
                <div style="height: 40px">
                    <span style="float: left">Active City:&nbsp;&nbsp;&nbsp;<%=indexPanelCity.getName()%></span>
                    <span style="float: right">Address:&nbsp;&nbsp;&nbsp;<%=cityAddress%></span>
                </div>
            </div>
        </div>
        <div>
            <ol class="pager">
                <li>
                    <a href="/CS2340Servlet/jsp/itinerary_overview.jsp">Select Itinerary</a>
                </li>
                <li><a href="#"
                       onclick="showPage1()"
                       data-toggle="modal"
                       data-target="#itineraryModal">Create New Itinerary
                </a>
                </li>
                <li id="li-create-event">
                    <a id="a-create-event" href="/CS2340Servlet/itinerary?create_event=1">
                        Create New Event</a>
                </li>
                <li id="li-create-city">
                    <a href="#" data-toggle="modal" data-target="#newCityModal">Add New City</a>
                </li>
                <li>
                    <div class="fb-login-button" data-scope="publish_actions" data-max-rows="1" data-size="medium"></div>
                </li>
            </ol><br /><br />

            <ul class="media-list">
                <li class="media" style="margin-left: 15%">
                    <div class="pull-left">
                        <div class="new-city-media-element" data-toggle="modal"
                             data-target="#newCityModal" role="button">
                            <h3>Add new <b>City</b></h3>
                            <p>Add a <strong>New City</strong> to your Itinerary</p>
                        </div>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading"
                            style="margin-left: 5%; color: white; font-family: 'Audiowide', cursive">
                            Expand your Itinerary by adding another City to it.
                        </h4><br /><br />
                        <p style="margin-left: 5%; color:white; font-family: 'Ubuntu', cursive">
                            With Desti, you can confine yourself to your Itinerary's default city<br />
                            or, instead of creating another Itinerary with its own city, you can<br />
                            add another city to your itinerary and easily swap between the two<br />
                            whenever you want. Each city has its own lodging, its own events, <br />
                            and its own budget, allowing for greater organization and flexibility<br />
                            over your itinerary.
                        </p>
                    </div>
                </li>

                <li class="media" style="margin-top: 30px">
                    <div class="pull-left"  style="margin-left: 15%">
                        <div class="new-lodging-media-element" data-toggle="modal"
                             data-target="#eventAjaxModal" role="button">
                            <h3>Find a <b>Lodging</b></h3>
                            <p>Add a <strong>New Lodging</strong> to your City</p>
                        </div>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading"
                            style="margin-left: 5%; color: white; font-family: 'Audiowide', cursive">
                            Find a place to stay by searching for a lodging.
                        </h4><br /><br />
                        <p style="margin-left: 5%; color:white; font-family: 'Ubuntu', cursive">
                            With Desti, you can confine yourself to your Itinerary's default city<br />
                            In planning a trip, it's always necessary to discover a great<br />
                            place to stay. Due to Yelp integration, Desti makes it easy to<br />
                            to find hotels, motels, apartments--you name it.
                        </p>
                    </div>
                </li>

                <li class="media" style="margin-top: 30px">
                    <div class="pull-left"  style="margin-left: 15%">
                        <div class="new-event-media-element" data-toggle="modal"
                             data-target="#eventAjaxModal" role="button">
                            <h3>Add New <strong>Event</strong></h3>
                            <p>Add a <strong>New Event</strong> to your City</p>
                        </div>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading"
                            style="margin-left: 5%; color: white; font-family: 'Audiowide', cursive">
                            Add as many events to your itinerary as you like.
                        </h4><br /><br />
                        <p style="margin-left: 5%; color:white; font-family: 'Ubuntu', cursive">
                            Equipped with Google and Yelp searches, Desti will help you find<br />
                            whatever you need while on a trip. From banks to restaurnts or<br />
                            from shopping malls to bowling alleys, Desti has got you covered.<br />
                            Quickly find and add new events to your itinerary, then review them<br />
                            using Desti's different views.
                        </p>
                    </div>
                </li>
            </ul>
            <%@ include file="eventAndLodgingModals.jsp" %>
        </div>
    </div>

    <div id="lodging-page">
        <div class="page-divider-header">
            <div style="display: inline-block">
                <h1><span class="glyphicon glyphicon-home"></span> LODGING</h1>
                <hr class="hr-title" />
            </div>
            <%@ include file="lodging_panel.jsp" %>
        </div>
    </div>

    <div id="event-places-page">
        <div class="page-divider-header">
            <div style="display: inline-block">
                <h1><span class="glyphicon glyphicon-th"></span> EVENTS & PLACES</h1>
                <hr class="hr-title" />
            </div>
            <%@ include file="events_places_panels.jsp" %>
        </div>
    </div>

    <div id="map-page">
        <div class="page-divider-header">
            <div style="display: inline-block; margin-bottom: 20px">
                <h1><span class="glyphicon glyphicon-globe"></span> MAP</h1>
                <hr class="hr-title" />
            </div><br />
        </div>
        <div align="center">
            <b>Start: </b>
            <%  Place lodgingSelect = (indexPanelCity != null) ? indexPanelCity.getLodging() : null; %>
            <select class="map-event-tooltips" id="start" onchange="calcRoute();" style="margin-bottom: 40px">
              <option value= '<%=cityAddress%>'>Center </option>
              <% if (lodgingSelect != null) { %>
              <option value= '<%=lodgingSelect.getFormattedAddress()%>' title="<%=lodgingSelect.getName()%>">
                  Lodging
              </option>
              <% } %>
              <% numEvents = 0;
                 if (userEvents != null) {
                   numEvents = userEvents.size();
                 }
                 for (int curEventID = 0; curEventID < numEvents; curEventID++) {
                   Place event = userEvents.get(curEventID); %>
              <option value= '<%=event.getFormattedAddress()%>' title="<%=event.getName()%>">
                  Event <%=curEventID + 1%>
              </option>
                <% } %>
            </select>
            <b>End: </b>
            <select id="end" onchange="calcRoute();">
              <option value= '<%=cityAddress%>'>Center </option>
              <% if (lodgingSelect != null) { %>
              <option value= '<%=lodgingSelect.getFormattedAddress()%>'>Lodging </option>
              <% } %>
              <% for (int curEventID = 0; curEventID < numEvents; curEventID++) {
                   Place event = userEvents.get(curEventID); %>
              <option value= '<%=event.getFormattedAddress()%>' title="<%=event.getName()%>">Event <%=curEventID + 1%></option>
              <% } %>
            </select>
            <b>Mode of Transit: </b>
            <select id="transitMode" onchange="calcRoute()">
              <option value= "DRIVING">Car </option>
              <option value= "BICYCLING">Bicycle </option>
              <option value= "TRANSIT">Public Transit </option>
              <option value= "WALKING">Walking </option>
            </select>
            <div class="row popin" style="max-width: 90%; min-height: 500px">
                <div class="col-md-12" id="mainMapWrapper">
                    <div id="main-map" style="height: 500px"></div>
                </div>
                <div class="hideAway" id="directionsPanelWrapper" style="overflow: auto">
                    <div id="directionsPanel"></div>
                </div>
            </div>
        </div>
    </div>

    <div id="budget-page" class="itinerary-sections">
        <%@ include file="budget_panel.jsp" %>
    </div>

    <!-- Error Message -->
    <div class="modal fade" id="errorMessage" tabindex="-1" role="dialog" aria-labelledby="errorMessageTitle" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="errorMessageTitle">Error</h4>
                </div>
                <div class="modal-body">
                    <span class="text-danger">
                        ${error}
                    </span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <!--Jquery-->
    <script src="/CS2340Servlet/js/jquery.js"></script>

    <!-- Bootstrap Javascript -->
    <script src="/CS2340Servlet/js/bootstrap.min.js"></script>

    <!-- Facebook Login -->
    <script src="/CS2340Servlet/js/facebookSDK.js"></script>

    <!-- Includes Google Maps Javascript functionality needed by this page -->
    <script src="/CS2340Servlet/js/itinerary_wizard_js.js"></script>

    <!-- Event Search Bar Javascript -->
    <script src="/CS2340Servlet/js/typeahead.bundle.js"></script>
    <script src="/CS2340Servlet/js/event_autocomplete.js"></script>

    <script src="/CS2340Servlet/js/navigation.js" type="text/javascript"></script>

    <!-- Index Javascript -->
    <script type="text/javascript">
        getCurrentPageSection('<%=request.getAttribute("currentSection")%>');

        // Change city tabs
        var citySelection = "<%=indexPanelCity.getName()%>";
        console.log("City selected: " + citySelection);
        var cityList = $("#cityList li");
        cityList.each(function(i, li) {
            if ($(li).text() === citySelection) {
                $(li).addClass("active");
            }
        });

        // Sidebar Functionality
        $('[data-toggle=collapse]').click(function(){
            $(this).find("i").toggleClass("glyphicon-chevron-right glyphicon-chevron-down");
        });

        $('.collapse').on('show', function (e) {
            $('.collapse').each(function(){
                if ($(this).hasClass('in')) {
                    $(this).collapse('toggle');
                }
            });
        });

        $(document).ready (function () {
            $(window).scroll (function () {
                var sT = $(this).scrollTop();
                var $overview = $("#itinerary-overview");
                var $overview_offset = $overview.offset().top - 50;
                var $lodging = $("#lodging-page");
                var $lodging_offset = $lodging.offset().top - 50;
                if (sT < $overview_offset && sT < $lodging_offset) {
                    $('#fixed-nav').removeClass('navbar-default');
                    $('#fixed-nav').addClass('navbar-inverse');
                } else if (sT >= $overview_offset && sT < $lodging_offset) {
                    $('#fixed-nav').removeClass('navbar-inverse');
                    $('#fixed-nav').addClass('navbar-default');
                } else if (sT >= $lodging_offset) {
                    $('#fixed-nav').removeClass('navbar-default');
                    $('#fixed-nav').removeClass('navbar');
                    $('#fixed-nav').addClass('navbar-custom');
                } else {

                }
            });
        });

        $(document).ready(function() {
            // Initial view
            $("#div-overview").show();
            $("#itinerary-side-bar").show();

            // Navbar Navigation Color Change
            $('.navbar-navigation li a').click(function(e) {
                var $this = $(this);
                if (!$this.hasClass('active')) {
                    $('.navbar-navigation li a').removeClass('active');
                    $this.addClass('active');
                    var hash = $(this).attr("href");
                    $('html, body').animate({
                        scrollTop: $(hash).offset().top - 10
                    }, 0);
                }
                e.preventDefault();
            });

            // Details button for new event location search
            $('.popover-dismiss').popover({
                trigger: 'focus'
            });

            // Error Message
            var error = '<%= request.getAttribute("error")%>';
            if (error != 'null') {
                $("#errorMessage").modal("show");
            }
        });

        $('form.ajax').on('submit', function () {
            var that = $(this),
                    url = that.attr('action'),
                    method = that.attr('method'),
                    data = {};

            that.find('[name]').each(function (index, value) {
                var that = $(this),
                        name = that.attr('name'),
                        value = that.val();

                data[name] = value;
            });

            $.ajax({
                url: url,
                type: method,
                data: data,
                success: function (json) {
                    console.log(json);
                    $("#ajax-event-table").append(json);
                }
            });

            return false;
        });
    </script>
</body>

<%@ include file="footer.jsp" %>

<%}%>
