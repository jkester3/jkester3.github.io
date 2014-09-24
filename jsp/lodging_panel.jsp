<%@ page import="database.City" %>
<%@ page import="database.Place" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section id="lodging"></section>
<div class="custom-centered-pills" style="margin-bottom: 30px">
    <ul class="nav nav-pills">
        <li style="float: right">
            <a class="alert-danger" href="#main-lodging" id="create-lodging-pill"
               data-toggle="collapse" data-parent="#accordion" style="margin-top: 50px">
                <span class="glyphicon glyphicon-plus-sign"
                      style="position: relative; top: 2px;">
                </span>
                <b>Add Lodging</b>
            </a>
        </li>
    </ul>
</div>

<div align="center">
<%  final City lodgingPanelCity = (City) session.getAttribute("activeCity");
    Place selection = (lodgingPanelCity != null) ? lodgingPanelCity.getLodging() : null;
    Object lodgingObject = session.getAttribute("lodgingResults");
    int numberOfLodgingsFound = 0;
    List<Place> lodgingResults = new ArrayList<Place>();
    if (lodgingObject != null) {
        lodgingResults = (List<Place>) lodgingObject;
        numberOfLodgingsFound = lodgingResults.size();
    }
    if (selection != null) { %>
    <div id="main-lodging">
    <% } else if (session.getAttribute("lodgingResults") != null) { %>
    <div id="main-lodging" class="panel-collapse collapse in">
    <% } else { %>
    <div id="main-lodging" class="panel-collapse collapse">
    <% } %>
        <div class="panel panel-danger" style="color: black">
            <div class="panel-heading">
                <% if (selection == null) { %>
                New Lodging
                <% } else { %>
                <b>Lodging: <%=selection.getName()%></b>
                <% } %>
            </div>
            <div class="panel-body">
                <div class="row">
                    <table class="table table-striped" style="width: 98%">
                    <% if (selection == null) { %>
                        <form action="/CS2340Servlet/itinerary" method="GET">
                            <div class="row" style="color: black">
                                <div class="form-inline" style="float: left; padding-left: 50px">
                                    <input name="lodgingName" style="min-width: 200px"
                                           class="form-control" placeholder="Lodging Name (optional)" />
                                    &nbsp;&nbsp;&nbsp;Radius (miles):&nbsp;&nbsp;&nbsp;
                                    <input name="lodgingRadius" type="number" min="1" max="25"
                                           class="form-control" />
                                    &nbsp;&nbsp;&nbsp;No. Results:&nbsp;&nbsp;&nbsp;
                                    <input name="lodgingFilter" type="number" min="1" max="20"
                                           class="form-control" />
                                </div>
                            </div>
                            <div class="row">
                                <div style="float: left; padding-left: 50px; padding-top: 20px">
                                    <input type="submit" class="form-control btn-primary" value="Search"
                                        name="lodgingSubmitButton" style="width: 150px;" />
                                </div>
                            </div>
                        </form><br />
                        <% if (session.getAttribute("lodgingResults") != null) { %>
                            <div class="row">
                                <div style="text-align: center; max-width: 90%;">
                                    Select a lodging below. This list has been created
                                    based on your Itinerary's address, which is:
                                    <b><%=lodgingPanelCity.getAddress()%></b>
                                </div>
                            </div><br/><br/>
                            <thead id="lodging-table-head">
                                <tr>
                                    <th>Name</th>
                                    <th>Address</th>
                                    <th>Rating</th>
                                    <th>Open</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                        <% } %>
                    <% } else { %>
                            <thead>
                                <tr>
                                    <th>Address</th>
                                    <th>Phone Number</th>
                                    <th>Rating</th>
                                    <th></th>
                                </tr>
                            </thead>
                    <% } %>
                            <tbody>
                    <%  if (selection == null) {
                        for (int i = 0; i < numberOfLodgingsFound; i++) {
                            String lodgingIsOpenColor, openClose;
                            lodgingIsOpenColor = (lodgingResults.get(i).isOpenNow()) ? "green" : "red";
                            openClose = (lodgingResults.get(i).isOpenNow()) ? "Open" : "Closed"; %>
                                <tr>
                                    <td style="max-width: 150px;">
                                        <a rel="popover"
                                           data-html="true"
                                           data-content="<img src='<%=lodgingResults.get(i).getRatingImage()%>'>
                                                <br /><%=lodgingResults.get(i).getSnippet()%>"
                                           data-title="<%=lodgingResults.get(i).getName()%>">
                                            <%=lodgingResults.get(i).getName()%>
                                        </a>
                                    </td>
                                    <td class="tableg-address" style="max-width: 250px;">
                                        <%=lodgingResults.get(i).getFormattedAddress()%>
                                    </td>
                                    <td><%=lodgingResults.get(i).getRating()%></td>
                                    <td style="color: <%=lodgingIsOpenColor%>"><%=openClose%></td>
                                    <td><a href="/CS2340Servlet/itinerary?lodging_id=<%=i%>">Select</a></td>
                                    <td><a href='<%=lodgingResults.get(i).getURL()%>' target="_blank">See More</a></td>
                                </tr>
                        <% } %>
                            </tbody>
                    <% } else { %>
                                <tr>
                                    <td class="table-address" style="max-width: 250px;"
                                            ><%=selection.getFormattedAddress()%>
                                    </td>
                                    <td><%=selection.getPhoneNumber()%></td>
                                    <td><%=selection.getRating()%></td>
                                    <td><a href='<%=selection.getURL()%>' target="_blank">See More</a></td>
                                </tr>
                            </tbody>
                    <% } %>
                    </table>
                    <% if (selection == null && lodgingResults.size() > 0) { %>
                        <form action="/CS2340Servlet/itinerary" method="GET">
                            <div class="row">
                                <div style="float: left; padding-left: 50px">
                                    <input type="submit" class="form-control btn-primary" value="More Results"
                                        name="lodgingGetMoreResults" style="width: 150px;" />
                                </div>
                                <div style="float: right; padding-right: 40px; padding-top: 10px">
                                    <b><%=numberOfLodgingsFound%></b> Results
                                </div>
                            </div>
                        </form>
                    <% } else if (selection != null) {
                            final String checkIn = selection.getCheckIn();
                            final String checkOut = selection.getCheckOut();
                            if (checkIn != null && checkOut != null) { %>
                                <div class="alert alert-danger" style="display: block; margin-left: auto; margin-right: auto; width: 75%">
                                    <label>Check in:&nbsp;&nbsp;</label><%=checkIn%>
                                    <span style="margin-left: 10px; margin-right: 10px;"><b>|</b></span>
                                    <label>Check out:&nbsp;&nbsp;</label><%=checkOut%>
                                    <button id="updateLodgingDateTime" class="btn btn-danger" style="margin-left: 5px; margin-right: 5px">Update</button>
                                </div>
                                <div id="updateLodgingDateTimePanel" align="center" style="position: absolute; left: -5000px; visibility: hidden">
                                    <form action="/CS2340Servlet/itinerary" method="POST">
                                        <div class="popin" style="width: 90%">
                                            <h6>Edit your Lodging Time below then click 'Submit'. To cancel, click 'Cancel'.</h6>
                                            <div class="alert alert-danger" style="display: inline-block">
                                                <label >Check in:</label><br />
                                                <input type="datetime-local" name="lodgingCheckIn" />
                                            </div>
                                            <div class="alert alert-danger" style="display: inline-block;">
                                                <label>Check out:</label><br />
                                                <input type="datetime-local" name="lodgingCheckOut" />
                                                <input type="submit" name="lodgingDateTimeSubmit" style="margin-left: 12px; margin-right: 5px" />
                                            </div>
                                        </div>
                                    </form>
                                </div>
                        <%  } else { %>
                            <form action="/CS2340Servlet/itinerary" method="POST">
                                <div class="row">
                                    <div class="alert alert-danger" style="width: 90%; height: 80px">
                                        <div class="pull-left">
                                            <label style="margin-left: 20px">Check in:</label>
                                            <input type="datetime-local" name="lodgingCheckIn" style="margin-left: 20px;" />
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="alert alert-danger" style="width: 90%; height: 80px">
                                        <div class="pull-left">
                                            <label style="margin-left: 20px">Check out:</label>
                                            <input type="datetime-local" name="lodgingCheckOut" style="margin-left: 10px" />
                                            <input type="submit" value="Save" name="lodgingDateTimeSubmit" style="margin-left: 30px" />
                                        </div>
                                    </div>
                                </div>
                            </form>
                        <%  } %>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

    <script>
        // Lodging functionality
        var lodging_selection = '<%=session.getAttribute("lodgingSelection")%>';
        if (lodging_selection !== "null") {
            $('#create-lodging-pill').hide();
        }

        $(document).ready(function() {
            $('[rel=popover]').popover({trigger: 'click', placement: 'left'});

            // Controls update/cancel functionality for lodging time
            $("#updateLodgingDateTime").click(function() {
                var buttonValue = $(this).text();
                if (buttonValue === "Update") {
                    $(this).text("Cancel");
                    $("#updateLodgingDateTimePanel").css("visibility", "visible");
                    $("#updateLodgingDateTimePanel").css("position", "");
                    $("#updateLodgingDateTimePanel").css("left", "");
                } else if (buttonValue === "Cancel") {
                    $(this).text("Update");
                    $("#updateLodgingDateTimePanel").css("visibility", "hidden");
                    $("#updateLodgingDateTimePanel").css("position", "absolute");
                    $("#updateLodgingDateTimePanel").css("left", "-5000");
                }
            });
        });
    </script>