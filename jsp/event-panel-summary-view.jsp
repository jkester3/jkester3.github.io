<%@ page import="database.Place" %>
<%@ page import="database.City" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="width: 20%; text-align: center; margin: 0 auto">
    <h2 style="text-align: center; font-family: 'Audiowide', cursive; border: solid">
        Summary View
    </h2>
</div>
<div class="container" style="padding-top: 50px">
    <div class="col-md-12">
        <div class="well">
            <div id="event-carousel" class="carousel slide active" data-ride="carousel">
                <!-- Carousel items -->
                <div class="carousel-inner">
                    <div class="item active">
                        <div class="row" style="height: 250px !important;">
                            <div class="col-sm-3"><a href="#x"><img src="http://placehold.it/500x500" alt="Image" class="img-responsive"></a>
                            </div>
                            <div class="col-sm-3"><a href="#x"><img src="http://placehold.it/500x500" alt="Image" class="img-responsive"></a>
                            </div>
                            <div class="col-sm-3"><a href="#x"><img src="http://placehold.it/500x500" alt="Image" class="img-responsive"></a>
                            </div>
                            <div class="col-sm-3"><a href="#x"><img src="http://placehold.it/500x500" alt="Image" class="img-responsive"></a>
                            </div>
                        </div>
                    </div>
                    <%
                        for (int curEventID = 0; curEventID < numberOfEvents; curEventID++) {
                        Place event = events.get(curEventID);
                        String panelColor = (curEventID % 2 == 0) ? "alert-success" : "alert-info";
                    %>
                        <% if (numberOfEvents % 2 == 0 && curEventID % 2 == 0) { %>
                        <div class="item">
                            <div class="row">
                        <% } else if (numberOfEvents % 2 != 0 && curEventID % 2 == 0) { %>
                        <div class="item">
                            <div class="row">
                        <% } %>
                                <div class="col-md-6">
                                    <div class="panel-body <%=panelColor%>" style="height: 250px !important;">
                                        <h3 style="font-family: 'Monda'">
                                            <a href="<%=event.getURL()%>" target="_blank"><%=curEventID + 1%>. <%=event.getName()%>
                                            </a>
                                        </h3>
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Address</th>
                                                    <th>Phone Number</th>
                                                    <th>Rating</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td><div style="width: 200px;"><%=event.getFormattedAddress()%></div></td>
                                                    <td><%=event.getPhoneNumber()%></td>
                                                    <td><%=event.getRating()%></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <% String checkIn = event.getCheckIn();
                                            String checkOut = event.getCheckOut();
                                            if (checkIn != null && checkOut != null) { %>
                                        <div class="alert alert-<%=panelColor%>" style="overflow-x: auto">
                                            <label>Start:&nbsp;&nbsp;</label><%=checkIn%>
                                            <span style="margin-left: 5px; margin-right: 5px;"><b>|</b></span>
                                            <label>End:&nbsp;&nbsp;</label><%=checkOut%>
                                        </div>

                                        <%  } %>
                                    </div>

                                </div>
                        <% if (numberOfEvents % 2 == 0 && (curEventID % 2 != 0 )) { %>
                            </div>
                        </div>
                        <% } else if (numberOfEvents % 2 != 0 && (curEventID % 2 != 0 || curEventID == numberOfEvents - 1)) { %>
                            </div>
                        </div>
                        <% } %>
                    <% } %>
                </div>
                <a class="left carousel-control" href="#event-carousel" data-slide="prev">‹</a>
                <a class="right carousel-control" href="#event-carousel" data-slide="next">›</a>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $('#event-carousel').carousel({
            //options here
        });
    });
</script>
