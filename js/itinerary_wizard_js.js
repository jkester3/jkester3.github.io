function showPage1() {
    $("#form_page_1").show();
    $("#form_page_2").hide();
    $("#form_page_3").hide();
    $("#form_page_4").hide();
}

function showPage2() {
    // Check if page 1 fields are empty
    var check = checkIfPreviousPageHasEmptyFields(1);
    if (!check) return;
    $("#form_page_1").hide();
    $("#form_page_2").show();
    $("#form_page_3").hide();
    $("#form_page_4").hide();
}

function showPage3() {
    // Check if page 2 fields are empty
    var check = checkIfPreviousPageHasEmptyFields(2);
    if (!check) return;
    $("#form_page_1").hide();
    $("#form_page_2").hide();
    $("#form_page_3").show();
    $("#form_page_4").hide();
    initialize();
}

function showPage4() {
    // Check if page 3 fields are empty
    var check = checkIfPreviousPageHasEmptyFields(3);
    if (!check) return;
    $("#form_page_1").hide();
    $("#form_page_2").hide();
    $("#form_page_3").hide();
    $("#form_page_4").show();
}

function checkIfPreviousPageHasEmptyFields(pageNum) {
    var pageID = "form_page_" + pageNum;
    var elements = document.getElementById(pageID).getElementsByTagName("input");
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].value === "") {
            document.getElementById("createItineraryButton").click();
            return false;
        }
    }
    return true;
}

var geocoder;
var map, main_map;
var directionsDisplay;
var directionsService;
function initialize(centralLat, centralLng) {
    directionsDisplay = new google.maps.DirectionsRenderer();
    geocoder = new google.maps.Geocoder();
    centralLat = centralLat != null ? centralLat : 34.397;
    centralLng = centralLng != null ? centralLng : 150.644;
    var latlng = new google.maps.LatLng(centralLat, centralLng);
    var mapOptions = {
        zoom: 12,
        center: latlng
    };
    map = new google.maps.Map(document.getElementById("myMap"), mapOptions);
    main_map = new google.maps.Map(document.getElementById("main-map"),
        mapOptions);
    directionsDisplay.setMap(main_map);
    directionsDisplay.setPanel(document.getElementById('directionsPanel'));
}

function codeAddress(formAddress) {
    var address = formAddress.value;
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
            setFormattedAddress(results[0].formatted_address);
            setCoordinates(results[0].geometry.location);
        } else {
            alert("Address could not be found for the following reason: " + status);
        }
    });
}

function calcRoute() {
  var mainMainWrapper = $("#mainMapWrapper");
  mainMainWrapper.removeClass("col-md-12");
  mainMainWrapper.addClass("col-md-8");
  var directionsPanelWrapper = $("#directionsPanelWrapper");
  directionsPanelWrapper.hide();
  directionsPanelWrapper.removeClass("hideAway");
  directionsPanelWrapper.fadeIn("slow");
  directionsService = new google.maps.DirectionsService();
  var start = document.getElementById('start').value;
  var end = document.getElementById('end').value;
  var travelMode = document.getElementById('transitMode').value;
  var request = {
      origin:start,
      destination:end,
      travelMode: google.maps.TravelMode[travelMode]
  };
  directionsService.route(request, function(response, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(response);
    }
  });
}

function setFormattedAddress(formattedAddress) {
    $("#formattedAddress").text("Starting Address currently set to: " + formattedAddress);
    $("#cityAddress").val(formattedAddress);
}

function setCoordinates(coordinates) {
    $("#coordinates").text(coordinates);
    $("#coordinates-hidden").val(coordinates);
    $("#cityCoordinates").val(coordinates);
}
