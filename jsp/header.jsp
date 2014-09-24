<%@ page import="database.Preference" %>
<%@ page import="database.Place" %>
<%@ page import="database.City" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />

    <!-- Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Monda' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Monda|Francois+One' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Audiowide' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>

    <!-- Stylesheets -->
    <link rel="stylesheet" type="text/css" href="/CS2340Servlet/css/style.css">
    <link href="/CS2340Servlet/css/bootstrap.min.css" rel="stylesheet">
    <link href="/CS2340Servlet/css/bootstrap-theme.css" rel="stylesheet" type="text/css">
    <link href="/CS2340Servlet/css/dashboard.css" rel="stylesheet">
    <link href="/CS2340Servlet/css/jquery.raty.css" rel="stylesheet" type="text/css">


    <!-- DateTime picker CSS -->
    <link rel="stylesheet" type="text/css" href="/CS2340Servlet/css/jquery.datetimepicker.css">
</head>
<body>

<%
if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
    <div class="navbar-white navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="/CS2340Servlet/jsp/index.jsp">
                            DESTI
                        </a>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="#" data-toggle="modal" data-target="#signUpForm">Sign Up</a>
                            </li>
                            <li class="dropdown">
                                <a class="dropdown-toggle" href="#" data-toggle="dropdown">Login <strong class="caret"></strong></a>
                                <div class="dropdown-menu" style="padding: 15px;">
                                    <!--Login form-->
                                    <form action="/CS2340Servlet" method="POST" accept-charset="UTF-8">
                                        <input id="user_username" style="margin-bottom: 15px;" type="text" name="username" size="30" placeholder="Username" required="required"/>
                                        <input id="user_password" style="margin-bottom: 15px;" type="password" name="password" size="30" placeholder="Password" required="required"/>
                                        <input class="btn btn-primary" style="clear: left; width: 100%; height: 32px; font-size: 13px;" type="submit" name="loginButton" value="Login" />
                                    </form>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Sign Up Form -->
    <div class="modal fade" id="signUpForm" tabindex="-1" role="dialog" aria-labelledby="signUpLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="signUpLabel">Sign Up</h4>
                </div>
                <div class="modal-body">
                    <form action="/CS2340Servlet/createAccount" method="POST" class="form-inline" role="form">
                        <b>Enter your Personal Information</b>

                        <br />
                        <br />

                        <div class="form-group">
                            <label class="sr-only" for="firstName">Name</label>
                            <input type="text" class="form-control" id="firstName" name="firstName"
                            required="required"
                            placeholder = "Enter First Name" />
                        </div>

                        <br />
                        <br />

                        <div class="form-group">
                            <label class="sr-only" for="lastName">Name</label>
                            <input type="text" class="form-control" id="lastName" name="lastName"
                            required="required"
                            placeholder = "Enter Last Name" />
                        </div>

                        <br />
                        <br />
                        <br />

                        <b>Enter new Account Information</b>

                        <br />
                        <br />

                        <div class="form-group">
                            <label class="sr-only" for="newUsername">Name</label>
                            <input type="text" class="form-control" id="newUsername"
                            name="newUsername"
                            required="required"
                            placeholder = "Enter Username" />
                        </div>

                        <br />
                        <br />

                        <div class="form-group">
                            <label class="sr-only" for="newPassword">Password</label>
                            <input type="password" class="form-control" id="newPassword"
                            name="newPassword"
                            onkeyup="checkPass(document.getElementById('newPassword'),
                            document.getElementById('confirmPassword'),
                            document.getElementById('passwordError'));
                            return false;"
                            required="required"
                            placeholder = "Enter Password" />
                        </div>

                        <br />
                        <br />

                        <div class="form-group">
                            <label class="sr-only" for="confirmPassword">Password</label>
                            <input type="password" class="form-control" id="confirmPassword"
                            name="confirmPassword"
                            onkeyup="checkPass(document.getElementById('newPassword'),
                            document.getElementById('confirmPassword'),
                            document.getElementById('passwordError'));
                            return false;"
                            required="required"
                            placeholder = "Re-enter Password" />
                        </div>

                        <div class="modal-footer">
                            <div id="passwordError" class="breadcrumb pull-left" style="text-align: left"></div>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" name="signUpButton" class="btn btn-primary">Sign Up</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var accountCreateSuccess = '<%=session.getAttribute("accountCreateSuccess")%>';
        if (accountCreateSuccess !== null) {
            console.log("New account created successfully");
        }
    </script>

<% } else { %>

    <div class="navbar-white navbar-fixed-top" id="fixed-nav" role="navigation">
        <div class="container" style="width: 100%">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">
                            DESTI
                        </a>
                    </div>
                    <div class="navbar-collapse collapse" style="margin-left: 30%">
                        <ul class="nav navbar-nav navbar-left">
                            <%  City headerCity = (City) session.getAttribute("activeCity");
                                double lat = headerCity != null ? headerCity.getLatitude() : 0;
                                double lng = headerCity != null ? headerCity.getLongitude() : 0;
                            %>
                            <li><a onclick="fadeToElement('#itinerary-header', <%=lat%>, <%=lng%>)" href="javascript:void(0)">Home<hr class="hr-title"/></a></li>
                            <li><a onclick="fadeToElement('#map-page', <%=lat%>, <%=lng%>)" href="javascript:void(0)">Map</a></li>
                            <li><a onclick="fadeToElement('#lodging-page', <%=lat%>, <%=lng%>)" href="javascript:void(0)">Lodging</a></li>
                            <li><a onclick="fadeToElement('#itinerary-overview', <%=lat%>, <%=lng%>)" href="javascript:void(0)">Itinerary</a></li>
                            <li><a onclick="fadeToElement('#event-places-page', <%=lat%>, <%=lng%>)" href="javascript:void(0)">Events & Places</a></li>
                            <li><a onclick="fadeToElement('#budget-page', <%=lat%>, <%=lng%>)" href="javascript:void(0)">Budget</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    Itineraries
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <%  final User currentUser = (User) session.getAttribute("currentUser");
                                        if (currentUser != null) {
                                            List<Itinerary> itineraries = currentUser.getItineraries();
                                            for (Itinerary itinerary : itineraries) { %>
                                            <li class="dropdown-header" style="overflow-x: hidden">
                                                <a href="/CS2340Servlet/index?itinerary_id=<%=itinerary.getID()%>">
                                                    <%=itinerary.getName()%>
                                                </a>
                                            </li>
                                    <%      }
                                        }
                                    %>
                                    <li class="dropdown-header">
                                        <a href="#"
                                           onclick="showPage1()"
                                           data-toggle="modal"
                                           data-target="#itineraryModal">Create New Itinerary
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li><a href="/CS2340Servlet/jsp/update_account.jsp">Settings</a></li>
                            <li><a href="/CS2340Servlet/jsp/deleteLoginSession.jsp">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

<% } %>