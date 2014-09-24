<%@ page import="database.Place" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="database.City" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Java functions for this page -->
<%! private boolean apiErrorReturned(HttpSession session, int curEventID) {
        return session.getAttribute("apiSearchError" + curEventID) != null;
    }

    private String getErrorMessage(final HttpSession session, final int curEventID) {
        return session.getAttribute("apiSearchError" + curEventID).toString();
    }

    private String determineURLFromError(final String error) {
        String url;
        if (error.contains("Google"))
            url = "https://developers.google.com/places/documentation/search#PlaceSearchStatusCodes";
        else
            url = "http://www.yelp.com/developers/documentation/v2/errors";
        return url;
    }
%>

<div style="width: 20%; text-align: center; margin: 0 auto">
    <h2 style="text-align: center; font-family: 'Audiowide', cursive; border: solid">Grid View</h2>
</div>
<div class="container-fluid" style="padding-top: 50px">
    <%  boolean colorChange[] = {true, false, true, false};
        for (int curEventID = 0; curEventID < numberOfEvents; curEventID++) {
            final Place event = events.get(curEventID);
            if (curEventID % 4 == 0) {
                for (int i = 0; i < colorChange.length; i++) {
                    colorChange[i] = !colorChange[i];
                }
            }
            String imageBgColor = (colorChange[curEventID % colorChange.length]) ? "26a0e6" : "73a839";
    %>
    <div class="col-md-3" >
        <div class="well" style="height: 150px !important; color: black; text-align: left">
            <div class="media">
                <a class="pull-left" href="#" data-toggle="modal" data-target="#eventModal<%=curEventID%>">
                    <img class="image-responsive"
                         src="http://placehold.it/500/<%=imageBgColor%>/fff&amp;text=<%=curEventID+1%>"
                         width="50" height="30">
                </a>
                <%  if (event.getName() != null && !event.getName().isEmpty()) {
                    String address = event.getFormattedAddress() != null ? event.getFormattedAddress() : "N/A";
                    String phone = event.getPhoneNumber() != null ? event.getPhoneNumber() : "N/A";
                %>
                    <div class="media-body">
                        <h4 class="media-heading">
                            <a href="#" data-toggle="modal" data-target="#eventModal<%=curEventID%>">
                                <%=event.getName()%>
                            </a>
                        </h4>
                        <p>
                            <span style="overflow: hidden">
                                <span style="text-decoration: underline">Address:</span> <%=address%>
                            </span><br/>
                            <span style="overflow: hidden">
                                <span style="text-decoration: underline">Phone:</span> <%=phone%>
                            </span><br/>
                            <span style="overflow: hidden">
                                <a href='<%=event.getURL()%>'>Website</a>
                            </span>
                        </p>
                    </div>
                <%  } else { %>
                <div class="media-body" data-toggle="modal"
                     data-target="#eventModal<%=curEventID%>" style="cursor: pointer">
                    <h4 class="media-heading"><i>New Event no. <%=curEventID + 1%></i>
                        <span class="glyphicon glyphicon-exclamation-sign" style="float: right"></span>
                    </h4>
                    <p>
                        <span style="text-decoration: underline">Click here to define this event</span>
                        <br />
                        <br />
                        Note: This event is temporary and will not be saved after you log out
                    </p>
                </div>
                <%  } %>
            </div>
        </div>
    </div>
    <% } %>
</div>

<div class="container-fluid">
<%  for (int curEventID = 0; curEventID < numberOfEvents; curEventID++) {
        String errorMessage = "";
        String eventPanelColor = (curEventID % 2 == 0) ? "success" : "primary";
        String alertColor = eventPanelColor == "primary" ? "info" : eventPanelColor;
        Place event = events.get(curEventID); %>

    <div id="eventModal<%=curEventID%>" class="modal fade" tabindex="-1" role="dialog"
         data-backdrop="true" aria-labelledby="eventModal<%=curEventID%>" aria-hidden="true">
        <div class="modal-dialog" style="width: 50%;">
            <div class="modal-content" style=" min-height: 400px; max-height: 800px; overflow: auto;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title" style="font-family: 'Lobster', cursive">
                        <%=!event.getName().isEmpty() ? event.getName() : "New Event no. " + (curEventID + 1)%>
                    </h2>
                </div>
                <div class="panel panel-<%=eventPanelColor%>" id="event-no-<%=curEventID%>"
                     style="width: 98%; margin-left: 10px; color: black; text-align: left">
                    <%  if (event.getName().isEmpty()) { %>
                    <div class="panel-body">
                        <form class="form-inline" role="form" action="/CS2340Servlet/itinerary?event_id=<%=curEventID%>" method="POST">
                            <div class="row">
                                <div class="form-group" style="padding-left: 20px">
                                    <input name="eventName<%=curEventID%>" type="text" class="form-control" placeholder="Place Name (optional)"/>
                                </div>
                                <div class="form-group" style="padding-left: 15px">
                                    <input name="eventType<%=curEventID%>" id="eventType<%=curEventID%>" type="text"
                                           class="form-control typeahead" placeholder="Place Type" required="required" />
                                </div>
                                <div class="form-group" style="padding-left: 15px">
                                    Radius (miles):  <input name="eventRadius<%=curEventID%>"
                                                            type="number" min="1" max="25" step="1" class="form-control" required="required"
                                                            value='<%=session.getAttribute("eventEndTime"+curEventID)%>'/>
                                </div><br />
                                <div class="form-group" style="float: left; padding-left: 20px; padding-top: 10px">
                                    <input name="getEventsWithGoogleButton" type="submit" class="form-control btn-primary" value="Google Search"
                                           onclick="changeValueToGoogleCode(document.getElementById('eventType' + '<%=curEventID%>'))" />
                                    <input name="getEventsWithYelpButton" type="submit" class="form-control btn-danger" value="Yelp Search"
                                           onclick="changeValueToGoogleCode(document.getElementById('eventType' + '<%=curEventID%>')); " />
                                </div>
                            </div><br />
                            <%  if (apiErrorReturned(session, curEventID)) { %>
                            <div class="alert alert-danger" role="alert" style="text-align: left">
                                <% errorMessage = getErrorMessage(session, curEventID); %>
                                <a href="<%=determineURLFromError(errorMessage)%>" target="_blank"
                                   class="alert-link"><%=errorMessage%></a>
                                <a data-toggle="collapse"
                                   data-parent="#accordion"
                                   href="#text-search-collapse<%=curEventID%>"
                                   id="parentCollapse">
                                    Recommendations
                                </a>
                            </div>
                            <% } %>
                            <%  List<Place> businesses = (List<Place>) session.getAttribute("businesses" + curEventID);
                                if (businesses != null) { %>
                            <div class="panel panel-<%=eventPanelColor%>">
                                <div class="panel-heading">
                                    <p>Search Parameters: <%=session.getAttribute("eventQueryString" + curEventID)%><br />
                                        The results of your search are listed below.</p>
                                </div>
                                <div class="panel-body">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <%  String apiIcon = (event.getAPI() != null) ? event.getAPI() : "google";
                                                String apiWidth = apiIcon.equals("google") ? "90px" : "50px";
                                                String apiHeight = apiIcon.equals("google") ? "25px" : "40px"; %>
                                            <img src="../images/<%=apiIcon%>.png" width="<%=apiWidth%>" height="<%=apiHeight%>" />&nbsp;&nbsp;Results
                                        </tr>
                                        <tr>
                                            <th>Name</th>
                                            <th>Address</th>
                                            <th>Rating</th>
                                            <th>Open</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%  for (int j = 0; j  < businesses.size(); j++) {
                                            String openOrClosedColor = (businesses.get(j).isOpenNow()) ? "green" : "red";
                                            String openOrClosed = (businesses.get(j).isOpenNow()) ? "Open" : "Closed";
                                            String businessURL = businesses.get(j).getURL();
                                            businessURL = (businessURL != null) ? businessURL :
                                                    "/CS2340Servlet/itinerary?detail_search&place_id=" + j + "&event_id=" + curEventID;%>
                                        <tr>
                                            <td style="max-width: 150px;">
                                                <a  rel="popover"
                                                    data-html="true"
                                                    data-content="<img src='<%=businesses.get(j).getRatingImage()%>'>
                                                                    <br /><%=businesses.get(j).getSnippet()%>"
                                                    data-title="<%=businesses.get(j).getName()%>">
                                                    <%=businesses.get(j).getName()%>
                                                </a>
                                            </td>
                                            <td style="max-width: 250px;"><%=businesses.get(j).getFormattedAddress()%></td>
                                            <td><%=businesses.get(j).getRating()%></td>
                                            <td style="color: <%=openOrClosedColor%>"><%=openOrClosed%></td>
                                            <td><a href="/CS2340Servlet/itinerary?select_business&place_id=<%=j%>&event_id=<%=curEventID%>">Select</a></td>
                                            <td><a href='<%=businessURL%>' target="_blank">See More</a></td>
                                        </tr>
                                        <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <% } %>
                        </form>
                        <!-- Form for Google Text Search, underneath the Recommendations label following Google error code -->
                        <form role="form" action="/CS2340Servlet/itinerary?event_id=<%=curEventID%>" method="POST">
                            <div id="text-search-collapse<%=curEventID%>" class="panel-collapse collapse">
                                <div class="alert alert-danger" role="alert" style="text-align: left">
                                    <ol>
                                        <%  final String recommendation = (errorMessage.contains("Google")) ? "Try a Yelp Search instead."
                                                : "Try a Google Search instead."; %>
                                        <li><%=recommendation%></li>
                                        <li>Try a Google Keyword Search instead:&nbsp;&nbsp;&nbsp;
                                            <input name="collapse-textsearch-query" type="text" placeholder="Keyword Search" />
                                            <input name="collapse-textsearch-submit" type="submit" />
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </form>
                    </div>
                    <% } else { %>
                    <div class="panel-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Address</th>
                                <th>Phone Number</th>
                                <th>Rating</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="table-address"><%=event.getFormattedAddress()%></td>
                                <td><%=event.getPhoneNumber()%></td>
                                <td><%=event.getRating()%></td>
                                <td><a href='<%=event.getURL()%>' target="_blank">See More</a></td>
                            </tr>
                            </tbody>
                        </table>
                        <% final String checkIn = event.getCheckIn();
                            final String checkOut = event.getCheckOut();
                            if (checkIn != null && checkOut != null) { %>
                        <div class="alert alert-<%=alertColor%>">
                            <label>Start:&nbsp;&nbsp;</label><%=checkIn%>
                            <span style="margin-left: 10px; margin-right: 10px;"><b>|</b></span>
                            <label>End:&nbsp;&nbsp;</label><%=checkOut%>
                            <button id="updateEventDateTimeBtn<%=curEventID%>"
                                    onclick="updateEventDateTime('<%=curEventID%>');"
                                    class="btn btn-<%=eventPanelColor%>"
                                    style="margin-left: 20px;">Update</button>
                        </div>
                        <div id="updateEventDateTimePanel<%=curEventID%>" align="center"
                             style="position: absolute; left: -5000px; visibility: hidden; margin-bottom: 10px">
                            <form action="/CS2340Servlet/itinerary?update_event_time&event_id=<%=curEventID%>" method="POST">
                                <div class="popin">
                                    <h6>Edit your Event Time below then click 'Submit'. To cancel, click 'Cancel'.</h6>
                                    <div class="alert alert-<%=alertColor%>" style="display: inline-block">
                                        <label >Start:</label><br />
                                        <input type="datetime-local" name="eventStartTime" />
                                    </div>
                                    <div class="alert alert-<%=alertColor%>" style="display: inline-block;">
                                        <label>End:</label><br />
                                        <input type="datetime-local" name="eventEndTime" />
                                        <input type="submit" name="eventDateTimeSubmit" style="margin-left: 12px; margin-right: 5px" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <%  } else { %>
                        <form action="/CS2340Servlet/itinerary?save_event_time&event_id=<%=curEventID%>" method="POST">
                            <div class="row" style="margin-left: 2px; margin-right: 2px">
                                <div class="alert alert-<%=alertColor%>">
                                    <label>Start:</label><input type="datetime-local" name="eventStartTime" style="margin-left: 20px" />
                                </div>
                            </div>
                            <div class="row" style="margin-left: 2px; margin-right: 2px">
                                <div class="alert alert-<%=alertColor%>">
                                    <label>End:&nbsp;&nbsp;</label><input type="datetime-local" name="eventEndTime" style="margin-left: 20px" />
                                    <input type="submit" value="Save" name="eventDateTimeSubmit" style="margin-left: 30px" />
                                </div>
                            </div>
                        </form>
                        <%  } %>
                        <div style="display: inline-block; margin-bottom: -20px; width: 100%">
                            <ol class="breadcrumb pull-left" style="background-color: white;">
                                <%  if (event.getAPI() != null) {
                                        if (event.getAPI().equals("google")) { %>
                                    <li class="api-breadcrumb">Google result</li>
                                    <%  } else if (event.getAPI().equals("yelp")) { %>
                                    <li class="api-breadcrumb">Yelp result&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                                    <%  } %>
                                <%  } %>
                            </ol>
                            <ol class="breadcrumb pull-right" style="background-color: white;">
                                <li><a href="<%=event.getURL()%>" target="_blank">Website</a></li>
                                <li><a href="#">Statistics</a></li>
                                <li><a href="<%=event.getCoordinates().format()%>">Find On Map</a></li>
                                <li><a href="#">Edit</a></li>
                                <li><a href="/CS2340Servlet/itinerary?delete_event&event_id=<%=curEventID%>">Delete</a></li>
                            </ol>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
    <% } %>
</div>

<script type="text/javascript">

        $(".draggable-modal").draggable({
            handle: ".modal-header"
        });

</script>
