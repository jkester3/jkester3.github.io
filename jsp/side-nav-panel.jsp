<div class="row-offcanvas row-offcanvas-left">
    <div id="sidebar" class="sidebar">
        <div class="col-md-12">
            <ul class="list-unstyled">
                <li class="nav-header"><a href="#"><h3>Toolbox</h3></a></li>
                <li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#userMenu">
                    <h4>User <i class="glyphicon glyphicon-chevron-down"></i></h4>
                </a>
                    <ul style="list-style: none;" class="collapse in" id="userMenu">
                        <li class="active"><a href="#"><i class="glyphicon glyphicon-home"></i> Home</a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-envelope"></i> Messages <span class="badge badge-info">4</span></a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-cog"></i> Settings</a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-comment"></i> Shoutbox</a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-user"></i> Staff List</a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-flag"></i> Transactions</a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-exclamation-sign"></i> Rules</a>
                        </li>
                        <li><a href="#"><i class="glyphicon glyphicon-off"></i> Logout</a>
                        </li>
                    </ul>
                </li>
                <li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#preferences-menuitem">
                    <h4>Preferences <i class="glyphicon glyphicon-chevron-right"></i></h4>
                </a>
                    <%  Preference preference = (Preference) session.getAttribute("activePreferences"); %>
                    <%  int maxDistance;
                        float minRating;
                        String priceCategory, attractionType, foodType;
                        maxDistance = 0;
                        minRating = 0;
                        priceCategory = attractionType = foodType = "";
                        if (preference != null) {
                            maxDistance = preference.getMaxDistance();
                            minRating = preference.getMinimumRating();
                            priceCategory = preference.getPriceCategory();
                            attractionType = preference.getPreferredAttractionType();
                            foodType = preference.getPreferredFoodType();
                        }
                    %>
                    <ul style="list-style-type: square;" class="collapse" id="preferences-menuitem">
                        <h4>Information &amp; Stats  <a href="#">edit</a></h4>
                        <li><b>Max Distance:</b> <%=maxDistance%></li>
                        <li><b>Rating:</b>  <%=minRating%></li>
                        <li><b>Price</b> : <%=priceCategory%></li>
                        <li><b>Attraction:</b>  <%=attractionType%></li>
                        <li><b>Food:</b>  <%=foodType%></li>
                    </ul>
                </li>
                <li class="nav-header">
                    <a href="#" data-toggle="collapse" data-target="#google-search-menuitem">
                        <h4>Keyword Search<i class="glyphicon glyphicon-chevron-right"></i></h4>
                    </a>
                    <ul style="list-style: none; padding-left: 0" class="collapse" id="google-search-menuitem">
                        <li>
                            <div id="google-textsearch">
                                <form class="form-inline" role="form" action="/CS2340Servlet/itinerary" method="POST">
                                    <div class="form-group">
                                        <input name="google-textsearch-query" type="text"
                                               class="form-control"
                                               placeholder="Keyword Search"
                                               style="width: 70%" />
                                        <input name="google-textsearch-submit"
                                               type="image"
                                               value="trialSubmit"
                                               src="../images/search.png"
                                               class="form-control" />
                                    </div>
                                </form>
                            </div>
                        </li>
                        <%  List<Place> places = (List<Place>) session.getAttribute("textSearchResults");
                            if (places != null) {
                                int i = 0;
                                for (Place place : places) {
                                    String placeName = place.getName();
                                    String address = place.getFormattedAddress();
                                    int priceLevel = place.getPriceLevel();
                                    double rating = place.getRating();
                                    boolean openNow = place.isOpenNow();
                                    String openOrClosed = (openNow) ? "Open" : "Closed";
                                    String color = (openNow) ? "rgb(0, 153, 0)" : "red";
                        %>
                        <li data-container="body"
                            data-toggle="popover"
                            data-placement="right"
                            data-content="Price level: <%=priceLevel%> | Rating: <%=rating%>">
                            <a class="popLink" href="javascript:void(0);" style="font-size: 10px;">
                                <b><%=++i%>.<%=placeName%></b> | <%=address%> | <span style="color: <%=color%>"><%=openOrClosed%></span>
                            </a>
                        </li>
                        <%      } %>
                        <%  }     %>
                    </ul>
                </li>
                <li class="nav-header" style="margin-top: 40px"><a href="#"><h3>Navigation</h3></a></li>
                <ul class="nav nav-list navbar-navigation">
                    <li><a href="#facebookLogin">Facebook Login<i class="glyphicon glyphicon-chevron-right"></i></a></li>
                    <li><a href="#itineraryOverview">Overview<i class="glyphicon glyphicon-chevron-right"></i></a></li>
                    <li><a href="#mapSection">Map<i class="glyphicon glyphicon-chevron-right"></i></a></li>
                    <li><a href="#lodging">Lodging<i class="glyphicon glyphicon-chevron-right"></i></a></li>
                    <li><a href="#eventsPlaces">Events & Places<i class="glyphicon glyphicon-chevron-right"></i></a></li>
                </ul>
            </ul>
        </div>
    </div>
</div>