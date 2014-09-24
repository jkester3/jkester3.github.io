<%@ page import="java.util.List" %>
<%@ page import="database.Place" %>
<%@ page import="database.City" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%  final City eventsPanelCity = (City) session.getAttribute("activeCity");
    List<Place> events = (eventsPanelCity != null) ? eventsPanelCity.getEvents() : new ArrayList<Place>();
    int numberOfEvents = 0;
    if (events != null) {
        numberOfEvents = events.size();
    }
%>

<section id="eventsPlaces"></section>
<div class="row">
    <form action="/CS2340Servlet/itinerary?change_event_view" method="get" id="ajaxChangeViewForm">
        <div class="custom-centered-pills center-left">
            <h2>Views</h2>
            <ul class="nav nav-pills">
                <li class="alert-success"><a href="/CS2340Servlet/itinerary?change_event_view=grid">Grid</a></li>
                <li class="alert-info"><a href="/CS2340Servlet/itinerary?change_event_view=summary">Summary</a></li>
            </ul>
        </div>
    </form>
    <div class="custom-centered-pills center-right">
        <h2>Actions</h2>
        <ul class="nav nav-pills">
            <li class="dropdown alert-success" id="create-event-pill">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-plus-sign" style="position: relative; top: 2px"></span>
                    <b>Add Event</b>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu pull-left">
                    <li role ="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?create_event=1">
                            Create <b>ONE</b>
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?create_event=2">
                            Create <b>TWO</b>
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?create_event=3">
                            Create <b>THREE</b>
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?create_event=5">
                            Create <b>FIVE</b>
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?create_event=10">
                            Create <b>TEN</b>
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?create_event=20">
                            Create <b>TWENTY</b>
                        </a>
                    </li>
                </ul>
            </li>
            <% if (numberOfEvents > 0) { %>
            <li class="dropdown alert-info" id="create-event-pill">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-sort-by-attributes" style="position: relative; top: 2px"></span>
                    <b>Sort By</b>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu pull-left">
                    <li role ="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?sort=name">
                            Name
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?sort=start_time">
                            Start Time
                        </a>
                    </li>
                    <li role="presentation" class="dropdown-header">
                        <a role="menuitem" tabindex="-1" href="/CS2340Servlet/itinerary?sort=creation_date">
                            Creation Date
                        </a>
                    </li>
                </ul>
            </li>
            <% } %>
        </ul>
    </div>
</div>

<%  final String eventPanelView = (String) session.getAttribute("eventPanelView");
    if (eventPanelView == "Grid" || eventPanelView == null) { %>
    <%@ include file="event-panel-grid-view.jsp" %>
<%  } else if (eventPanelView == "Summary") { %>
    <%@ include file="event-panel-summary-view.jsp" %>
<%  } %>

<script>
    $(document).ready(function() {
        $('[rel=popover]').popover({trigger: 'click', placement: 'left'});

        jQuery(function($) {
            var panelList = $('#draggablePanelList');
            panelList.sortable({
                handle: '.panel-heading',
                cursor: 'move',
                start: function (event, ui) {
                    ui.item.addClass('being-dragged');
                },
                stop: function (event, ui) {
                    ui.item.removeClass('being-dragged');
                }
            });
        });
    });

    // Controls update/cancel functionality for event time
    function updateEventDateTime(eventID) {
        var $this = $("#updateEventDateTimeBtn" + eventID);
        var $panel = $("#updateEventDateTimePanel" + eventID);
        var buttonAction = $this.text();
        if (buttonAction === "Update") {
            $this.text("Cancel");
            $panel.css("visibility", "visible");
            $panel.css("position", "");
            $panel.css("left", "");
        } else if (buttonAction === "Cancel") {
            $this.text("Update");
            $panel.css("visibility", "hidden");
            $panel.css("position", "absolute");
            $panel.css("left", "-5000");
        }
    }
</script>
